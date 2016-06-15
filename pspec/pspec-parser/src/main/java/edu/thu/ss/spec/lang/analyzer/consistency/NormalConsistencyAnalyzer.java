package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.List;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;

public class NormalConsistencyAnalyzer extends ConsistencyAnalyzer {

	public NormalConsistencyAnalyzer(EventTable table) {
		super(table);
	}

	@Override
	public boolean analyze(List<ExpandedRule> rules) {
		NormalConsistencySearcher searcher = new NormalConsistencySearcher(table);
		searcher.SetRules(rules);
		searcher.search();
		System.out.println("Find conflicts: " + searcher.conflicts);
		return false;
	}

}
