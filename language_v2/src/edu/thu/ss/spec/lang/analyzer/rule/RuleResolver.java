package edu.thu.ss.spec.lang.analyzer.rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.HierarchicalObject;
import edu.thu.ss.spec.lang.pojo.ObjectRef;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.UserRef;

/**
 * Resolve reference elements into concrete elements.
 * Also materialize user and data references.
 * 
 * @author luochen
 * 
 */
public class RuleResolver extends BaseRuleAnalyzer {

	private static Logger logger = LoggerFactory.getLogger(RuleResolver.class);

	private Map<UserCategory, Set<UserCategory>> userCache = new HashMap<>();
	private Map<DataCategory, Set<DataCategory>> dataCache = new HashMap<>();

	@Override
	public boolean analyzeRule(Rule rule, UserContainer users, DataContainer datas) {
		boolean error = false;
		error = error || resolveUsers(rule.getUserRefs(), users);
		error = error || resolveDatas(rule.getDataRefs(), datas);

		if (rule.getAssociation() != null) {
			error = error || resolveDatas(rule.getAssociation().getDataRefs(), datas);
		}

		for (Restriction restriction : rule.getRestrictions()) {
			if (restriction.isForbid()) {
				continue;
			}
			for (Desensitization de : restriction.getDesensitizations()) {
				error = error || resolveDesensitization(de, rule);
			}
		}
		return error;
	}

	@Override
	public boolean stopOnError() {
		return true;
	}

	@Override
	public String errorMsg() {
		return "Error detected when resolving category references in rules, see error messages above.";
	}

	private boolean resolveUsers(List<UserRef> refs, UserContainer users) {
		boolean error = false;
		UserCategory user = null;
		for (UserRef ref : refs) {
			user = resolveUser(ref, users, ruleId);
			if (user != null) {
				ref.setUser(user);
			} else {
				error = true;
			}
			for (ObjectRef excludeRef : ref.getExcludeRefs()) {
				user = resolveUser(excludeRef, users, ruleId);
				if (user != null && !checkExclusion(ref.getUser(), user)) {
					ref.getExcludes().add(user);
				} else {
					error = true;
				}
			}

			ref.materialize(policy.getUserContainer(), userCache);
		}
		return error;
	}

	@SuppressWarnings("unchecked")
	private <T extends HierarchicalObject<T>> boolean checkExclusion(HierarchicalObject<T> category,
			HierarchicalObject<T> exclude) {
		if (category == null) {
			return true;
		}
		if (!category.ancestorOf((T) exclude) || category.equals(exclude)) {
			logger.error("Excluded category: {} must be a sub-category of referenced category: {}", exclude.getId(),
					category.getId());
			return true;
		}
		return false;
	}

	private UserCategory resolveUser(ObjectRef ref, UserContainer container, String ruleId) {
		UserCategory user = container.get(ref.getRefid());
		if (user == null) {
			logger.error("Fail to locate user category: " + ref.getRefid() + ", referenced in rule: " + ruleId);
		}
		return user;
	}

	private boolean resolveDatas(Collection<DataRef> refs, DataContainer datas) {
		boolean error = false;
		for (DataRef ref : refs) {
			DataCategory data = resolveData(ref, datas);
			if (data != null) {
				ref.setData(data);
			} else {
				error = true;
			}
			for (ObjectRef excludeRef : ref.getExcludeRefs()) {
				data = resolveData(excludeRef, datas);
				if (data != null && !checkExclusion(ref.getData(), data)) {
					ref.exclude(data);
				} else {
					error = true;
				}
			}
			if (!error) {
				ref.materialize(policy.getDataContainer(), dataCache);
			}
		}
		return error;
	}

	private DataCategory resolveData(ObjectRef ref, DataContainer container) {
		DataCategory data = container.get(ref.getRefid());
		if (data == null) {
			logger.error("Fail to locate data category: " + ref.getRefid() + ", referenced in rule: " + ruleId);
		}
		return data;
	}

	private boolean resolveDesensitization(Desensitization de, Rule rule) {
		if (rule.getAssociation() != null && rule.getDataRefs().size() > 0) {
			logger
					.error(
							"Data-category-ref and data-association should not appear together when restriction contains desensitize element in rule: {}",
							ruleId);
			return true;
		}
		if (rule.getDataRefs().size() > 0) {
			if (de.getDataRefIds().size() > 0) {
				logger
						.error("No data-category-ref element should appear in desensitize element when only data category is referenced in rule: "
								+ ruleId);
				return true;
			}
			de.setDataIndex(0);
			return false;
		} else if (rule.getAssociation() != null) {
			if (de.getDataRefIds().size() == 0) {
				logger
						.error("Restricted data category must be specified explicitly when data association is referenced by rule: "
								+ ruleId);
				return true;
			}
		}
		boolean error = false;
		DataAssociation association = rule.getAssociation();
		int[] index = new int[de.getDataRefIds().size()];
		int i = 0;
		for (String refid : de.getDataRefIds()) {
			DataRef data = null;
			data = association.get(refid);
			if (data == null) {
				logger.error("Restricted data category: {} must be contained in referenced data association in rule: {}",
						refid, ruleId);
				error = true;
			}
			if (!error) {
				de.getDataRefs().add(data);
				index[i++] = association.getIndex(refid);
			}
		}
		if (!error) {
			de.setDataIndex(index);
			if (rule.getAssociation() != null) {
				de.materialize();
			}
		}

		return error;
	}
}
