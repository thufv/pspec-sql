package edu.thu.ss.spec.lang.analyzer.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserRef;

public class LocalExpander extends BasePolicyAnalyzer {

	@Override
	public boolean analyze(Policy policy) {
		List<LocalRule> expandedRules = new ArrayList<>();
		List<Rule> rules = policy.getRules();
		for (Rule rule : rules) {
			int index = 1;
			Set<UserCategory> users = expandUser(rule.getUserRefs());
			Map<Action, Set<DataCategory>> datas = expandData(rule.getDataRefs());
			for (Entry<Action, Set<DataCategory>> e : datas.entrySet()) {
				LocalRule erule = new LocalRule(rule, users, e.getKey(), e.getValue(), index++);
				expandedRules.add(erule);
			}
			if (rule.getAssociation() != null) {
				LocalRule erule = new LocalRule(rule, users, rule.getAssociation(), index++);
				expandedRules.add(erule);
			}
		}
		policy.setLocalRules(expandedRules);
		return false;
	}

	private Set<UserCategory> expandUser(List<UserRef> userRefs) {
		Set<UserCategory> result = new HashSet<>();
		for (UserRef ref : userRefs) {
			result.addAll(ref.getMaterialized());
		}
		return result;
	}

	private Map<Action, Set<DataCategory>> expandData(List<DataRef> dataRefs) {
		Map<Action, Set<DataCategory>> result = new HashMap<>();
		for (DataRef ref : dataRefs) {
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
