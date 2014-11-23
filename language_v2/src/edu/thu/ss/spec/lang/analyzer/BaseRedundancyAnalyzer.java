package edu.thu.ss.spec.lang.analyzer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.InclusionUtil;
import edu.thu.ss.spec.util.SetUtil;
import edu.thu.ss.spec.util.SetUtil.SetRelation;

public abstract class BaseRedundancyAnalyzer extends BasePolicyAnalyzer {

	protected static class SimplificationLog {
		public ExpandedRule rule;
		public List<UserRef> userRefs;
		public DataRef dataRef;

		public SimplificationLog(ExpandedRule rule, List<UserRef> userRefs, DataRef dataRef) {
			this.rule = rule;
			this.userRefs = userRefs;
			this.dataRef = dataRef;
		}

	}

	protected static Logger logger = LoggerFactory.getLogger(BaseRedundancyAnalyzer.class);

	protected List<SimplificationLog> logs = new LinkedList<>();

	/**
	 * initialized by sub classes
	 */
	protected InclusionUtil instance = null;

	public boolean analyze(Policy policy) {
		List<ExpandedRule> rules = policy.getExpandedRules();

		Iterator<ExpandedRule> it = rules.iterator();
		while (it.hasNext()) {
			ExpandedRule erule = it.next();
			boolean removable = checkRedundancy(erule, rules);
			if (removable) {
				it.remove();
			}
		}
		commit();
		return false;
	}

	private boolean checkRedundancy(ExpandedRule target, List<ExpandedRule> rules) {
		for (ExpandedRule erule : rules) {
			if (target == erule) {
				continue;
			}
			boolean redundant = false;
			if (target.isSingle()) {
				redundant = checkSingle(erule, target);
			} else {
				redundant = checkAssociation(erule, target);
			}
			if (redundant) {
				logger.warn("The rule: {} is redundant since it is covered by rule: {}, consider revise your policy.",
						target.getRuleId(), erule.getRuleId());
				return true;
			}
		}

		return false;
	}

	/**
	 * both rule1 and rule2 are single. Check rule1 implies rule2, i.e., whether rule2 is redundant
	 * 
	 * @param rule1
	 * @param rule2
	 * @return
	 */
	private boolean checkSingle(ExpandedRule rule1, ExpandedRule rule2) {
		if (rule1.isAssociation()) {
			return false;
		}
		if (rule2.isGlobal() && !rule1.isGlobal()) {
			//global rule cannot be covered by local rules.
			return false;
		}
		// pre-test, to filter out result early.
		DataRef ref1 = rule1.getDataRef();
		DataRef ref2 = rule2.getDataRef();

		Action action1 = ref1.getAction();
		Action action2 = ref2.getAction();
		if (!instance.includes(action1, action2)) {
			return false;
		}
		//only 1 restriction each
		Restriction[] res1 = rule1.getRestrictions();
		Restriction[] res2 = rule2.getRestrictions();
		if (!instance.singleStricterThan(res1[0], res2[0])) {
			return false;
		}
		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		SetRelation userRelation = SetUtil.relation(user1, user2);
		if (userRelation.equals(SetRelation.disjoint)) {
			return false;
		}

		Set<DataCategory> data1 = ref1.getMaterialized();
		Set<DataCategory> data2 = ref2.getMaterialized();
		SetRelation dataRelation = SetUtil.relation(data1, data2);
		if (dataRelation.equals(SetRelation.disjoint)) {
			return false;
		}

		if (userRelation.equals(SetRelation.contain) && dataRelation.equals(SetRelation.contain)) {
			return true;
		} else if (userRelation.equals(SetRelation.contain) && dataRelation.equals(SetRelation.intersect)) {
			logs.add(new SimplificationLog(rule2, null, ref1));
		} else if (userRelation.equals(SetRelation.intersect) && dataRelation.equals(SetRelation.contain)) {
			logs.add(new SimplificationLog(rule2, rule1.getUserRefs(), null));
		}
		return false;
	}

	/**
	 * rule2 is association, dispatch to
	 * {@link #checkSingleAssociation(ExpandedRule, ExpandedRule)} and
	 * {@link #checkBothAssociation(ExpandedRule, ExpandedRule)} only perform
	 * remove operation, not perform simplification operation.
	 * 
	 * @param rule1
	 * @param rule2
	 * @return
	 */
	private boolean checkAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		if (rule1.isSingle()) {
			return checkSingleAssociation(rule1, rule2);
		} else {
			return checkBothAssociation(rule1, rule2);
		}

	}

	/**
	 * rule1 is single, while rule2 is association. Check rule1 implies rule2
	 * 
	 * @param rule1
	 * @param rule2
	 * @return
	 */
	private boolean checkSingleAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();

		if (!SetUtil.contains(user1, user2)) {
			return false;
		}

		DataRef ref1 = rule1.getDataRef();
		Action action1 = ref1.getAction();
		Set<DataCategory> data1 = ref1.getMaterialized();

		DataAssociation assoc2 = rule2.getAssociation();

		boolean match = false;
		for (DataRef ref2 : assoc2.getDataRefs()) {
			if (ref2.isGlobal() && !ref1.isGlobal()) {
				continue;
			}
			if (!instance.includes(action1, ref2.getAction())) {
				continue;
			}
			if (SetUtil.contains(data1, ref2.getMaterialized())) {
				match = true;
				break;
			}
		}
		if (!match) {
			return false;
		}

		Restriction[] res1 = rule1.getRestrictions();
		Restriction[] res2 = rule2.getRestrictions();

		return instance.stricterThan(res1, res2);

	}

	/**
	 * both rule1 and rule2 are association. Check rule1 implies rule2
	 * 
	 * @param rule1
	 * @param rule2
	 * @return
	 */
	private boolean checkBothAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		DataAssociation assoc1 = rule1.getAssociation();
		DataAssociation assoc2 = rule2.getAssociation();

		if (assoc1.getDimension() > assoc2.getDimension()) {
			return false;
		}

		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		if (!SetUtil.contains(user1, user2)) {
			return false;
		}

		for (DataRef ref1 : assoc1.getDataRefs()) {
			boolean match = false;
			for (DataRef ref2 : assoc2.getDataRefs()) {
				if (instance.includes(ref1, ref2)) {
					match = true;
					break;
				}
			}
			if (!match) {
				return false;
			}
		}

		Restriction[] res1 = rule1.getRestrictions();
		Restriction[] res2 = rule2.getRestrictions();

		return instance.stricterThan(res1, res2);

	}

	protected abstract void commit();

}
