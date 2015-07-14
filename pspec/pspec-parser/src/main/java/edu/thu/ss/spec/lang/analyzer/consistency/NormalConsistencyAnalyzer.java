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
		NormalConsistencySearcher searcher = new NormalConsistencySearcher(rules);
		searcher.search();
		return false;
	}

}
