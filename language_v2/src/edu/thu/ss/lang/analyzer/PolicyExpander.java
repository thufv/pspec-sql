package edu.thu.ss.lang.analyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.thu.ss.lang.pojo.Action;
import edu.thu.ss.lang.pojo.DataAssociation;
import edu.thu.ss.lang.pojo.DataCategory;
import edu.thu.ss.lang.pojo.DataCategoryRef;
import edu.thu.ss.lang.pojo.ExpandedRule;
import edu.thu.ss.lang.pojo.Policy;
import edu.thu.ss.lang.pojo.Rule;
import edu.thu.ss.lang.pojo.UserCategory;
import edu.thu.ss.lang.pojo.UserCategoryRef;

public class PolicyExpander extends BasePolicyAnalyzer {

	@Override
	public boolean analyze(Policy policy) {
		List<ExpandedRule> expandedRules = new LinkedList<>();
		List<Rule> rules = policy.getRules();
		for (Rule rule : rules) {
			Set<UserCategory> users = expandUser(rule.getUserRefs());
			Map<Action, Set<DataCategory>> datas = expandData(rule.getDataRefs());
			for (Entry<Action, Set<DataCategory>> e : datas.entrySet()) {
				ExpandedRule erule = new ExpandedRule(rule, users, e.getKey(), e.getValue());
				expandedRules.add(erule);
			}

			for (DataAssociation association : rule.getAssociations()) {
				ExpandedRule erule = new ExpandedRule(rule, users, association);
				expandedRules.add(erule);
			}
		}
		policy.setExpandedRules(expandedRules);
		return false;

	}

	private Set<UserCategory> expandUser(Set<UserCategoryRef> userRefs) {
		Set<UserCategory> result = new HashSet<>();
		for (UserCategoryRef ref : userRefs) {
			result.addAll(ref.getMaterialized());
		}
		return result;
	}

	private Map<Action, Set<DataCategory>> expandData(Set<DataCategoryRef> dataRefs) {
		Map<Action, Set<DataCategory>> result = new HashMap<>();
		for (DataCategoryRef ref : dataRefs) {
			Set<DataCategory> set = result.get(ref.getAction());
			if (set == null) {
				set = new HashSet<>();
				result.put(ref.getAction(), set);
			}
			set.addAll(ref.getMaterialized());
		}
		return result;
	}

}
