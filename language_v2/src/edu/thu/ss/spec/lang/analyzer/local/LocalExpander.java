package edu.thu.ss.spec.lang.analyzer.local;

import java.util.ArrayList;
import java.util.Collections;
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
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;

public class LocalExpander extends BasePolicyAnalyzer {

	@Override
	public boolean analyze(Policy policy) {
		List<ExpandedRule> expandedRules = new ArrayList<>();
		List<Rule> rules = policy.getRules();
		for (Rule rule : rules) {
			int index = 1;
			Map<Action, Set<DataCategory>> datas = expandData(rule.getDataRefs());
			for (Entry<Action, Set<DataCategory>> e : datas.entrySet()) {
				DataRef ref = new DataRef(e.getKey(), e.getValue());
				ExpandedRule erule = new ExpandedRule(rule, ref, index++);
				expandedRules.add(erule);
			}
			if (rule.getAssociation() != null) {
				ExpandedRule erule = new ExpandedRule(rule, rule.getAssociation(), index++);
				expandedRules.add(erule);
			}
		}
		Collections.sort(expandedRules);
		policy.setExpandedRules(expandedRules);
		return false;
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
