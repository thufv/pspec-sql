package edu.thu.ss.spec.lang.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;

/**
 * expands rule in a local manner, global attribute is ignored
 * @author luochen
 *
 */
public class LocalExpander extends BasePolicyAnalyzer {

	public LocalExpander(EventTable table) {
		super(table);
	}

	@Override
	public boolean analyze(Policy policy) {
		List<ExpandedRule> expandedRules = new ArrayList<>();
		List<Rule> rules = policy.getRules();
		for (Rule rule : rules) {
			int index = 1;
			Map<Action, List<DataRef>> datas = expandData(rule.getDataRefs());
			for (Entry<Action, List<DataRef>> e : datas.entrySet()) {
				List<DataRef> list = e.getValue();

				Set<DataCategory> set = new HashSet<>();
				for (DataRef ref : list) {
					set.addAll(ref.getMaterialized());
				}
				DataRef ref = new DataRef(e.getKey(), set);
				ExpandedRule erule = new ExpandedRule(rule, ref, index++);
				erule.setRawDataRefs(list);
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

	private Map<Action, List<DataRef>> expandData(List<DataRef> dataRefs) {
		Map<Action, List<DataRef>> result = new HashMap<>();
		for (DataRef ref : dataRefs) {
			List<DataRef> refs = result.get(ref.getAction());
			if (refs == null) {
				refs = new ArrayList<>();
				result.put(ref.getAction(), refs);
			}
			refs.add(ref);
		}
		return result;
	}

}
