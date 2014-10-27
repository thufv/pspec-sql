package edu.thu.ss.lang.analyzer;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.lang.pojo.Action;
import edu.thu.ss.lang.pojo.DataCategory;
import edu.thu.ss.lang.pojo.ExpandedRule;
import edu.thu.ss.lang.pojo.Policy;
import edu.thu.ss.lang.pojo.Restriction;
import edu.thu.ss.lang.pojo.UserCategory;
import edu.thu.ss.lang.util.InclusionUtil;
import edu.thu.ss.lang.util.SetUtil;
import edu.thu.ss.lang.util.SetUtil.SetRelation;

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
				//logger.warn("The #{} restriction is removed from rule: {} since it is redundant.", i, ruleId);
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
				return true;
			}
		}

		return false;
	}

	//check whether rule1 contains rule2
	private boolean checkSingle(ExpandedRule rule1, ExpandedRule rule2) {
		if (rule1.isAssociation()) {
			return false;
		}

		// pre-test, to filter out result early.
		Action action1 = rule1.getAction();
		Action action2 = rule2.getAction();
		if (!action1.ancestorOf(action2)) {
			return false;
		}
		//only 1 restriction each
		Restriction res1 = rule1.getRestriction().get(0);
		Restriction res2 = rule2.getRestriction().get(0);
		if (!InclusionUtil.singleStricterThan(res1, res2)) {
			return false;
		}
		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		SetRelation userRelation = SetUtil.relation(user1, user2);
		if (userRelation.equals(SetRelation.disjoint)) {
			return false;
		}

		Set<DataCategory> data1 = rule1.getDatas();
		Set<DataCategory> data2 = rule2.getDatas();
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

	private boolean checkAssociation(ExpandedRule erule, ExpandedRule target) {
		// TODO Auto-generated method stub
		return false;
	}
}
