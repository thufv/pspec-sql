package edu.thu.ss.spec.lang.analyzer.rule;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.HierarchicalObject;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.xml.XMLDataAssociation;
import edu.thu.ss.spec.lang.xml.XMLDataCategoryRef;
import edu.thu.ss.spec.lang.xml.XMLDesensitization;
import edu.thu.ss.spec.lang.xml.XMLObjectRef;
import edu.thu.ss.spec.lang.xml.XMLRestriction;
import edu.thu.ss.spec.lang.xml.XMLRule;
import edu.thu.ss.spec.lang.xml.XMLUserCategoryRef;

/**
 * Resolve reference elements into concrete elements.
 * Also materialize user and data references.
 * 
 * @author luochen
 * 
 */
public class RuleResolver extends BaseRuleAnalyzer {

	private static Logger logger = LoggerFactory.getLogger(RuleResolver.class);

	@Override
	public boolean analyzeRule(XMLRule rule, UserContainer users, DataContainer datas) {
		boolean error = false;
		error = error || resolveUsers(rule.getUserRefs(), users);
		error = error || resolveDatas(rule.getDataRefs(), datas);
		for (XMLDataAssociation association : rule.getAssociations()) {
			error = error || resolveDatas(association.getDataRefs(), datas);
		}
		for (XMLRestriction restriction : rule.getRestrictions()) {
			if (restriction.isForbid()) {
				continue;
			}
			for (XMLDesensitization de : restriction.getDesensitizations()) {
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

	private boolean resolveUsers(Set<XMLUserCategoryRef> refs, UserContainer users) {
		boolean error = false;
		UserCategory user = null;
		for (XMLUserCategoryRef ref : refs) {
			user = resolveUser(ref, users, ruleId);
			if (user != null) {
				ref.setUser(user);
			} else {
				error = true;
			}
			for (XMLObjectRef excludeRef : ref.getExcludeRefs()) {
				user = resolveUser(excludeRef, users, ruleId);
				if (user != null && !checkExclusion(ref.getUser(), user)) {
					ref.getExcludes().add(user);
				} else {
					error = true;
				}
			}

			ref.materialize();
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

	private UserCategory resolveUser(XMLObjectRef ref, UserContainer container, String ruleId) {
		UserCategory user = container.get(ref.getRefid());
		if (user == null) {
			logger.error("Fail to locate user category: " + ref.getRefid() + ", referenced in rule: " + ruleId);
		}
		return user;
	}

	private boolean resolveDatas(Set<XMLDataCategoryRef> refs, DataContainer datas) {
		boolean error = false;
		for (XMLDataCategoryRef ref : refs) {
			DataCategory data = resolveData(ref, datas);
			if (data != null) {
				ref.setData(data);
			} else {
				error = true;
			}
			for (XMLObjectRef excludeRef : ref.getExcludeRefs()) {
				data = resolveData(excludeRef, datas);
				if (data != null && !checkExclusion(ref.getData(), data)) {
					ref.getExcludes().add(data);
				} else {
					error = true;
				}
			}
			if (!error) {
				ref.materialize();
			}
		}
		return error;
	}

	private DataCategory resolveData(XMLObjectRef ref, DataContainer container) {
		DataCategory data = container.get(ref.getRefid());
		if (data == null) {
			logger.error("Fail to locate data category: " + ref.getRefid() + ", referenced in rule: " + ruleId);
		}
		return data;
	}

	private boolean resolveDesensitization(XMLDesensitization de, XMLRule rule) {
		if (rule.getAssociations().size() > 0 && rule.getDataRefs().size() > 0) {
			logger
					.error(
							"Data-category-ref and data-association should not appear together when restriction contains desensitize element in rule: {}",
							ruleId);
			return true;
		}
		if (rule.getDataRefs().size() > 0) {
			if (de.getObjRefs().size() > 0) {
				logger
						.error("No data-category-ref element should appear in desensitize element when only data category is referenced in rule: "
								+ ruleId);
				return true;
			}
			return false;
		} else if (rule.getAssociations().size() > 0) {
			if (de.getObjRefs().size() == 0) {
				logger
						.error("Restricted data category must be specified explicitly when data association is referenced by rule: "
								+ ruleId);
				return true;
			}
		}
		boolean error = false;
		for (XMLObjectRef ref : de.getObjRefs()) {
			XMLDataCategoryRef data = null;
			for (XMLDataAssociation association : rule.getAssociations()) {
				data = association.get(ref.getRefid());
				if (data == null) {
					logger.error("Restricted data category: {} must be contained in referenced data association in rule: {}",
							ref.getRefid(), ruleId);
					error = true;
				}
			}
			if (!error) {
				de.getDataRefs().add(data);
			}
		}
		if (!error) {
			de.materialize();
		}

		return error;
	}
}
