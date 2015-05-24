package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.ArrayList;
import java.util.List;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;

public class NormalConsistencyAnalyzer extends ConsistencyAnalyzer {

	@Override
	public void analyze(List<ExpandedRule> rules) {
		NormalConsistencySearcher searcher = new NormalConsistencySearcher(rules);
		searcher.search();
	}

}
