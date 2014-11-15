package edu.thu.ss.spec.lang.analyzer;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataActionPair;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.InclusionUtil;
import edu.thu.ss.spec.util.SetUtil;
import edu.thu.ss.spec.util.SetUtil.SetRelation;

public class SimpleRedundancyAnalyzer extends BasePolicyAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(SimpleRedundancyAnalyzer.class);

	@Override
	public boolean analyze(Policy policy) {
		List<ExpandedRule> rules = policy.getExpandedRules();

		Iterator<ExpandedRule> it = rules.iterator();
		while (it.hasNext()) {
			ExpandedRule erule1 = it.next();
			boolean removable = checkRedundancy(erule1, rules);
			if (removable) {
				it.remove();
			}
		}

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
				logger
						.warn("The rule: {} is removed since it is covered by rule: {}.", target.getRuleId(), erule.getRuleId());
				return true;
			}
		}

		return false;
	}

	/**
	 * both rule1 and rule2 are single. Check rule1 implies rule2
	 * 
	 * @param rule1
	 * @param rule2
	 * @return
	 */
	private boolean checkSingle(ExpandedRule rule1, ExpandedRule rule2) {
		if (rule1.isAssociation()) {
			return false;
		}

		// pre-test, to filter out result early.
		DataActionPair pair1 = rule1.getDatas()[0];
		DataActionPair pair2 = rule2.getDatas()[0];

		Action action1 = pair1.getAction();
		Action action2 = pair2.getAction();
		if (!action1.ancestorOf(action2)) {
			return false;
		}
		//only 1 restriction each
		Restriction[] res1 = rule1.getRestrictions();
		Restriction[] res2 = rule2.getRestrictions();
		if (!InclusionUtil.singleStricterThan(res1[0], res2[0])) {
			return false;
		}
		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		SetRelation userRelation = SetUtil.relation(user1, user2);
		if (userRelation.equals(SetRelation.disjoint)) {
			return false;
		}

		Set<DataCategory> data1 = pair1.getDatas();
		Set<DataCategory> data2 = pair2.getDatas();
		SetRelation dataRelation = SetUtil.relation(data1, data2);
		if (dataRelation.equals(SetRelation.disjoint)) {
			return false;
		}

		if (userRelation.equals(SetRelation.contain) && dataRelation.equals(SetRelation.contain)) {
			return true;
		} else if (userRelation.equals(SetRelation.contain) && dataRelation.equals(SetRelation.intersect)) {
			data2.removeAll(data1);
		} else if (userRelation.equals(SetRelation.intersect) && dataRelation.equals(SetRelation.contain)) {
			user2.remove(user1);
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

		DataActionPair pair1 = rule1.getDatas()[0];

		Action action1 = pair1.getAction();
		Set<DataCategory> data1 = pair1.getDatas();

		DataActionPair[] pairs2 = rule2.getDatas();
		boolean match = false;
		for (DataActionPair pair2 : pairs2) {
			if (!InclusionUtil.includes(action1, pair2.getAction())) {
				continue;
			}
			if (SetUtil.contains(data1, pair2.getDatas())) {
				match = true;
				break;
			}
		}
		if (!match) {
			return false;
		}

		Restriction res1 = rule1.getRestrictions()[0];
		Restriction[] res2 = rule2.getRestrictions();

		return InclusionUtil.stricterThan(res1, res2);

	}

	/**
	 * both rule1 and rule2 are association. Check rule1 implies rule2
	 * 
	 * @param rule1
	 * @param rule2
	 * @return
	 */
	private boolean checkBothAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		DataActionPair[] pairs1 = rule1.getDatas();
		DataActionPair[] pairs2 = rule2.getDatas();
		if (pairs1.length > pairs2.length) {
			return false;
		}

		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		if (!SetUtil.contains(user1, user2)) {
			return false;
		}

		for (DataActionPair pair1 : pairs1) {
			boolean match = false;
			for (DataActionPair pair2 : pairs2) {
				if (!InclusionUtil.includes(pair1.getAction(), pair2.getAction())) {
					continue;
				}
				if (SetUtil.contains(pair1.getDatas(), pair2.getDatas())) {
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

		return InclusionUtil.stricterThan(res1, res2);

	}

}
