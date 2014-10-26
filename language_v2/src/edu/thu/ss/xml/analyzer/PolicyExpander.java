package edu.thu.ss.xml.analyzer;

import java.util.LinkedList;
import java.util.List;

import edu.thu.ss.xml.pojo.DataAssociation;
import edu.thu.ss.xml.pojo.DataCategoryRef;
import edu.thu.ss.xml.pojo.ExpandedRule;
import edu.thu.ss.xml.pojo.Policy;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.UserCategoryRef;

public class PolicyExpander extends BasePolicyAnalyzer {

	@Override
	public boolean analyze(Policy policy) {
		List<ExpandedRule> expandedRules = new LinkedList<>();
		List<Rule> rules = policy.getRules();
		for (Rule rule : rules) {
			for (UserCategoryRef user : rule.getUserRefs()) {
				for (DataCategoryRef data : rule.getDataRefs()) {
					ExpandedRule erule = expandRule(rule, user);
					erule.setData(data);
					expandedRules.add(erule);
				}
				for (DataAssociation association : rule.getAssociations()) {
					ExpandedRule erule = expandRule(rule, user);
					erule.setAssociation(association);
					expandedRules.add(erule);
				}
			}
		}
		policy.setExpandedRules(expandedRules);
		return false;

	}

	private ExpandedRule expandRule(Rule rule, UserCategoryRef user) {
		ExpandedRule erule = new ExpandedRule(rule);
		erule.setUser(user);
		return erule;
	}

}
