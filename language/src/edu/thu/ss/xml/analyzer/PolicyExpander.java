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
		int precedence = 0;
		for (Rule rule : rules) {
			for (UserCategoryRef user : rule.getUserRefs()) {
				for (DataCategoryRef data : rule.getDataRefs()) {
					ExpandedRule erule = expandRule(rule, user, data, precedence);
					expandedRules.add(erule);
				}
				for (DataAssociation association : rule.getAssociations()) {
					ExpandedRule erule = expandRule(rule, user, association, precedence);
					expandedRules.add(erule);
				}

				precedence++;
			}
		}
		policy.setExpandedRules(expandedRules);
		return false;

	}

	private ExpandedRule expandRule(Rule rule, UserCategoryRef user, DataCategoryRef data, int precedence) {
		ExpandedRule erule = new ExpandedRule(rule);
		erule.setUser(user);
		erule.setData(data);
		erule.setPrecedence(precedence);
		return erule;
	}

	private ExpandedRule expandRule(Rule rule, UserCategoryRef user, DataAssociation data, int precedence) {
		ExpandedRule erule = new ExpandedRule(rule);
		erule.setUser(user);
		erule.setAssociation(data);
		erule.setPrecedence(precedence);
		return erule;
	}

}
