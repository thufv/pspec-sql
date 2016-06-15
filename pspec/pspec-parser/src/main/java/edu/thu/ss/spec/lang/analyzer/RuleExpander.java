package edu.thu.ss.spec.lang.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;

/**
 * expands rule in a local manner, global attribute is ignored
 * @author luochen
 *
 */
public class RuleExpander extends BasePolicyAnalyzer {

	public RuleExpander(EventTable table) {
		super(table);
	}

	@Override
	public boolean analyze(Policy policy) {
		List<ExpandedRule> expandedRules = new ArrayList<>();
		List<Rule> rules = policy.getRules();
		for (Rule rule : rules) {
			expand(rule, expandedRules);
		}
		Collections.sort(expandedRules);
		policy.setExpandedRules(expandedRules);
		return false;
	}

	public void expand(Rule rule, List<ExpandedRule> list) {
		if (rule.isSingle()) {
			for (DataRef ref : rule.getDataRefs()) {
				ExpandedRule erule = new ExpandedRule(rule, ref);
				list.add(erule);
			}
		} else {
			ExpandedRule erule = new ExpandedRule(rule, rule.getAssociation());
			list.add(erule);
		}

	}
}
