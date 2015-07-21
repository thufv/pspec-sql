package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.List;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;

public class ApproximateConsistencyAnalyzer extends ConsistencyAnalyzer{

	public enum RuleRelation {
		disjoint, conflict, consistent, forbid
	}
	
	public ApproximateConsistencyAnalyzer(EventTable table) {
		super(table);
	}

	@Override
	public boolean analyze(List<ExpandedRule> rules) {
		ApproximateConsistencySearcher searcher = new ApproximateConsistencySearcher(table);
		searcher.setRules(rules);
		searcher.search();
		return false;
	}

}
