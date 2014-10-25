package edu.thu.ss.xml.analyzer;

import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.xml.pojo.DataAssociation;
import edu.thu.ss.xml.pojo.DataCategoryRef;
import edu.thu.ss.xml.pojo.ExpandedRule;
import edu.thu.ss.xml.pojo.Policy;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.UserCategoryRef;

public class ExpandedRedundancyAnalyzer extends BasePolicyAnalyzer {

	private static Logger logger = LoggerFactory.getLogger(ExpandedRedundancyAnalyzer.class);

	private int curPrecedence = 0;
	private Set<UserCategoryRef> retainedUsers = new HashSet<>();
	private Set<DataCategoryRef> retainedDatas = new HashSet<>();
	private Set<DataAssociation> retainedAssociations = new HashSet<>();

	@Override
	public boolean analyze(Policy policy) {
		List<ExpandedRule> expandedRules = policy.getExpandedRules();
		assert (expandedRules != null);
		boolean[] redundant = new boolean[expandedRules.size()];

		int index = 0;
		for (ExpandedRule erule : expandedRules) {
			if (checkRedundancy(erule, expandedRules, redundant)) {
				redundant[index] = true;
			}
			index++;
		}

		ListIterator<ExpandedRule> it = expandedRules.listIterator();
		index = 0;
		while (it.hasNext()) {
			ExpandedRule erule = it.next();
			if (redundant[index]) {
				it.remove();
				logger.warn("({} ,{}) in rule: {} is ignored, since it is covered by previous rules.", erule.getUser(),
						erule.getData() != null ? erule.getData() : erule.getAssociation(), erule.getRuleId());

			}
			index++;
		}

		for (ExpandedRule erule : expandedRules) {
			collect(erule, policy);
		}

		printMsg(policy.getRules().size(), policy);

		return false;
	}

	private void collect(ExpandedRule erule, Policy policy) {
		if (erule.getPrecedence() > curPrecedence) {
			// the following rules are skipped
			printMsg(erule.getPrecedence(), policy);
			curPrecedence = erule.getPrecedence();
			retainedUsers.clear();
			retainedDatas.clear();
			retainedAssociations.clear();
		}
		retainedUsers.add(erule.getUser());
		if (erule.getData() != null) {
			retainedDatas.add(erule.getData());
		} else {
			retainedAssociations.add(erule.getAssociation());
		}
	}

	private void printMsg(int precedence, Policy policy) {
		List<Rule> rules = policy.getRules();
		for (int i = curPrecedence + 1; i < precedence; i++) {
			logger.error("The rule: {} is redundant, since its scope is totally covered by previous rules.",
					rules.get(i).getId());
		}
		Rule rule = policy.getRules().get(curPrecedence);
		if (retainedUsers.size() < rule.getUserRefs().size()) {
			logger.error("The following user category in rule: {} is redundant, consider revise your rules.", rules
					.get(curPrecedence).getId());
			setDiff(rule.getUserRefs(), retainedUsers);
		}
		if (retainedDatas.size() < rule.getDataRefs().size()) {
			logger.error("The following data category in rule: {} is redundant, consider revise your rules.", rules
					.get(curPrecedence).getId());
			setDiff(rule.getDataRefs(), retainedDatas);
		}
		if (retainedAssociations.size() < rule.getAssociations().size()) {
			logger.error("The following data association in rule: {} is redundant, consider revise your rules.", rules
					.get(curPrecedence).getId());
			setDiff(rule.getAssociations(), retainedAssociations);
		}

	}

	private <T> void setDiff(Set<T> set1, Set<T> set2) {
		for (T t : set1) {
			if (!set2.contains(t)) {
				logger.error("\t{}", t);
			}
		}
	}

	private boolean checkRedundancy(ExpandedRule erule, List<ExpandedRule> expandedRules, boolean[] redundant) {
		boolean covered = false;
		int i = 0;
		for (ExpandedRule rule : expandedRules) {
			if (rule.getPrecedence() >= erule.getPrecedence()) {
				break;
			}
			if (!redundant[i] && includes(rule, erule)) {
				covered = true;
				break;
			}
			i++;
		}
		return covered;
	}

	private boolean includes(ExpandedRule rule1, ExpandedRule rule2) {
		UserCategoryRef user1 = rule1.getUser();
		UserCategoryRef user2 = rule2.getUser();
		if (!InclusionUtil.includes(user1, user2)) {
			return false;
		}

		DataCategoryRef data1 = rule1.getData();
		DataCategoryRef data2 = rule2.getData();
		DataAssociation association1 = rule1.getAssociation();
		DataAssociation association2 = rule2.getAssociation();

		if (data1 != null && data2 != null) {
			return InclusionUtil.includes(data1, data2);
		} else if (data1 != null && association2 != null) {
			return InclusionUtil.includes(data1, association2, rule1.getRuling());
		} else if (association1 != null && data2 != null) {
			return InclusionUtil.includes(association1, data2, rule1.getRuling());
		} else if (association1 != null && association2 != null) {
			return InclusionUtil.includes(association1, association2, rule1.getRuling());
		}
		return false;
	}
}
