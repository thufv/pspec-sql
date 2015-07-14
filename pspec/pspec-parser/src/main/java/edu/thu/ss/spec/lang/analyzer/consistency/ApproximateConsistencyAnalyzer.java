package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.List;

import edu.thu.ss.spec.lang.analyzer.IPolicyAnalyzer;
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
		if (rules.get(0).isFilter()) {
			return false;
		}
		
		ApproximateConsistencySearcher searcher = new ApproximateConsistencySearcher(rules);
		searcher.search();
		return false;
	}

}
