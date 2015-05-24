package edu.thu.ss.spec.lang.analyzer;

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
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.FineBudget;
import edu.thu.ss.spec.lang.pojo.GlobalBudget;
import edu.thu.ss.spec.lang.pojo.HierarchicalObject;
import edu.thu.ss.spec.lang.pojo.ObjectRef;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.PrivacyBudget;
import edu.thu.ss.spec.lang.pojo.PrivacyParams;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.UserRef;

/**
 * Resolve reference elements into concrete elements in policy.
 * Also materialize {@link DataRef} and {@link UserRef}
 * 
 * @author luochen
 * 
 */
public class PolicyResolver extends BasePolicyAnalyzer {

	private abstract class ResolveListener {
		public void noUser(String refid) {
		}

		public void noData(String refid) {
		}
	}

	private static Logger logger = LoggerFactory.getLogger(PolicyResolver.class);

	/**
	 * caches descendants for {@link UserCategory}
	 */
	private Map<UserCategory, Set<UserCategory>> userCache = new HashMap<>();

	/**
	 * caches descendants for {@link DataCategory}
	 */
	private Map<DataCategory, Set<DataCategory>> dataCache = new HashMap<>();

	private boolean error = false;

	public boolean analyze(Policy policy) {
		error = false;
		List<Rule> rules = policy.getRules();
		for (Rule rule : rules) {
			resolveRule(rule, policy.getUserContainer(), policy.getDataContainer());
		}
		PrivacyParams params = policy.getPrivacyParams();
		if (params == null) {
			return false;
		}
		PrivacyBudget<?> budget = params.getPrivacyBudget();
		if (budget.isGlobal()) {
			resolveGlobalBudget((GlobalBudget) budget, policy.getUserContainer(),
					policy.getDataContainer());
		} else {
			resolveFineBudget((FineBudget) budget, policy.getUserContainer(), policy.getDataContainer());
		}

		return error;
	}

	private void resolveGlobalBudget(GlobalBudget budget, UserContainer users, DataContainer datas) {
		ResolveListener listener = new ResolveListener() {
			@Override
			public void noUser(String refid) {
				logger.error("fail to locate user category: {} in budget allocation.", refid);
			}
		};
		for (PrivacyBudget.BudgetAllocation alloc : budget.getAllocations()) {
			GlobalBudget.BudgetAllocation galloc = (GlobalBudget.BudgetAllocation) alloc;
			resolveUserRef(galloc.userRef, users, listener);
		}

	}

	private void resolveFineBudget(FineBudget budget, UserContainer users, DataContainer datas) {
		ResolveListener listener = new ResolveListener() {
			@Override
			public void noData(String refid) {
				logger.error("fail to locate data category: {} in budget allocation.", refid);
			}

			@Override
			public void noUser(String refid) {
				logger.error("fail to locate user category: {} in budget allocation.", refid);
			}
		};
		for (PrivacyBudget.BudgetAllocation alloc : budget.getAllocations()) {
			FineBudget.BudgetAllocation falloc = (FineBudget.BudgetAllocation) alloc;
			resolveUserRef(falloc.userRef, users, listener);
			resolveDataRef(falloc.dataRef, datas, listener);
		}

	}

	private void resolveRule(Rule rule, UserContainer users, DataContainer datas) {
		if (rule.getAssociation() != null && rule.getDataRefs().size() > 0) {
			error = true;
			logger
					.error(
							"Data-category-ref and data-association should not appear together when restriction contains desensitize element in rule: {}",
							rule.getId());
			return;
		}

		resolveUsers(rule.getUserRefs(), users, rule);
		resolveDatas(rule.getDataRefs(), datas, rule);

		if (rule.getAssociation() != null) {
			resolveDatas(rule.getAssociation().getDataRefs(), datas, rule);
		}

		for (Restriction restriction : rule.getRestrictions()) {
			if (restriction.isForbid() || restriction.isFilter()) {
				continue;
			}
			resolveRestriction(restriction, rule);

		}
	}

	@Override
	public boolean stopOnError() {
		return true;
	}

	@Override
	public String errorMsg() {
		return "Error detected when resolving category references in rules, see error messages above.";
	}

	private void resolveUsers(List<UserRef> refs, UserContainer users, final Rule rule) {
		ResolveListener listener = new ResolveListener() {
			@Override
			public void noUser(String refid) {
				logger.error("Fail to locate user category: " + refid + ", referenced in rule: "
						+ rule.getId());
			}
		};

		for (UserRef ref : refs) {
			resolveUserRef(ref, users, listener);
		}
	}

	private void resolveDatas(List<DataRef> refs, DataContainer datas, final Rule rule) {
		ResolveListener listener = new ResolveListener() {
			@Override
			public void noData(String refid) {
				logger.error("Fail to locate data category: " + refid + ", referenced in rule: "
						+ rule.getId());
			}
		};

		for (DataRef ref : refs) {
			resolveDataRef(ref, datas, listener);
		}
	}

	private void resolveUserRef(UserRef ref, UserContainer users, ResolveListener listener) {
		if (ref.isResolved()) {
			return;
		}
		UserCategory user = resolveUser(ref, users, listener);
		if (user != null) {
			ref.setUser(user);
		} else {
			error = true;
		}
		for (ObjectRef excludeRef : ref.getExcludeRefs()) {
			user = resolveUser(excludeRef, users, listener);
			if (user != null) {
				checkExclusion(ref.getUser(), user);
				if (!error) {
					ref.getExcludes().add(user);
				}
			} else {
				error = true;
			}
		}
		if (!error) {
			ref.materialize(users, userCache);
		}

	}

	private void resolveDataRef(DataRef ref, DataContainer datas, ResolveListener listener) {
		if (ref.isResolved()) {
			return;
		}
		DataCategory data = resolveData(ref, datas, listener);
		if (data != null) {
			ref.setData(data);
		} else {
			error = true;
		}
		for (ObjectRef excludeRef : ref.getExcludeRefs()) {
			data = resolveData(excludeRef, datas, listener);
			if (data != null) {
				checkExclusion(ref.getData(), data);
				if (!error) {
					ref.exclude(data);
				}
			} else {
				error = true;
			}
		}
		if (!error) {
			ref.materialize(datas, dataCache);
		}
	}

	private UserCategory resolveUser(ObjectRef ref, UserContainer container, ResolveListener listener) {
		UserCategory user = container.get(ref.getRefid());
		if (user == null && listener != null) {
			listener.noUser(ref.getRefid());
		}
		return user;
	}

	private DataCategory resolveData(ObjectRef ref, DataContainer container, ResolveListener listener) {
		DataCategory data = container.get(ref.getRefid());
		if (data == null) {
			if (listener != null) {
				listener.noData(ref.getRefid());
			}
		}
		return data;
	}

	/**
	 * checks whether excluded category is descendant of referred category 
	 * @param category
	 * @param exclude
	 * @return error
	 */
	@SuppressWarnings("unchecked")
	private <T extends HierarchicalObject<T>> void checkExclusion(HierarchicalObject<T> category,
			HierarchicalObject<T> exclude) {
		if (category == null) {
			error = true;
			return;
		}
		if (!category.ancestorOf((T) exclude) || category.equals(exclude)) {
			logger.error("Excluded category: {} must be a sub-category of referenced category: {}",
					exclude.getId(), category.getId());
			error = true;
		}
	}

	/**
	 * resolve {@link DesensitizeOperation} in {@link Desensitization}
	 * also performs constraint checks.
	 * @param de
	 * @param rule
	 * @return error
	 */
	private void resolveRestriction(Restriction res, Rule rule) {
		if (rule.getAssociation() == null) {
			resolveSingleRestriction(res, rule);
		} else {
			resolveAssociateRestriction(res, rule);
		}
	}

	private void resolveSingleRestriction(Restriction res, Rule rule) {
		if (res.getList().size() > 1) {
			logger.error("Only 1 desensitization is allowed for single rule: {}", rule.getId());
			error = true;
			return;
		}
		Desensitization de = res.getList().get(0);
		if (de.getDataRefId() != null) {
			logger
					.error("No data-category-ref element should appear in desensitize element when only data category is referenced in rule: "
							+ rule.getId());
			error = true;
			return;
		}
		Desensitization[] des = new Desensitization[] { de };
		res.setDesensitizations(des);
	}

	private void resolveAssociateRestriction(Restriction res, Rule rule) {
		Desensitization[] des = new Desensitization[rule.getAssociation().getDimension()];
		DataAssociation association = rule.getAssociation();
		for (Desensitization de : res.getList()) {
			if (de.getDataRefId() == null) {
				logger
						.error("Restricted data category must be specified explicitly when data association is referenced by rule: "
								+ rule.getId());
				error = true;
				return;
			}
			String refid = de.getDataRefId();
			DataRef ref = association.get(refid);
			if (ref == null) {
				logger
						.error(
								"Restricted data category: {} must be contained in referenced data association in rule: {}",
								refid, rule.getId());
				error = true;
				continue;
			}
			de.setDataRef(ref);
			de.materialize();
			int index = association.getIndex(refid);
			des[index] = de;
		}

		res.setDesensitizations(des);

	}

}
