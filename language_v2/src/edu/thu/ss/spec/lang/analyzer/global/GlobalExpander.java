package edu.thu.ss.spec.lang.analyzer.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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

public class GlobalExpander extends BasePolicyAnalyzer {
	private Map<Action, Set<DataCategory>> actionMap = new HashMap<>();

	@Override
	public boolean analyze(Policy policy) {
		List<GlobalRule> expandedRules = new ArrayList<>();
		List<Rule> rules = policy.getRules();
		for (Rule rule : rules) {
			int index = 1;
			if (rule.isSingle()) {
				List<DataRef> refs = expandData(rule.getDataRefs());
				for (DataRef ref : refs) {
					GlobalRule globalRule = new GlobalRule(rule, ref, index++);
					expandedRules.add(globalRule);
				}
			} else {
				GlobalRule globalRule = new GlobalRule(rule, rule.getAssociation(), index++);
				expandedRules.add(globalRule);
			}
		}
		policy.setGlobalRules(expandedRules);
		return false;
	}

	private List<DataRef> expandData(List<DataRef> dataRefs) {
		List<DataRef> result = new LinkedList<DataRef>();
		for (DataRef ref : dataRefs) {
			if (ref.isGlobal()) {
				result.add(ref);
			} else {
				Set<DataCategory> set = actionMap.get(ref.getAction());
				if (set == null) {
					set = new HashSet<>();
					actionMap.put(ref.getAction(), set);
				}
				set.addAll(ref.getMaterialized());
			}
		}
		for (Entry<Action, Set<DataCategory>> e : actionMap.entrySet()) {
			DataRef ref = new DataRef();
			ref.setAction(e.getKey());
			ref.setGlobal(false);
			ref.materialize(e.getValue());
			result.add(ref);
		}
		actionMap.clear();
		return result;
	}

}
