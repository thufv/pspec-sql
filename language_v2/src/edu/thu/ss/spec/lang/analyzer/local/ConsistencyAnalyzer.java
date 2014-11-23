package edu.thu.ss.spec.lang.analyzer.local;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.pojo.Policy;

public class ConsistencyAnalyzer extends BasePolicyAnalyzer {

	public enum RuleRelation {
		disjoint, conflict, consistent, forbid
	}

	@Override
	public boolean analyze(Policy policy) {

		ConsistencySearcher searcher = new CachedConsistencySearcher(policy);
		searcher.search();
		return false;
	}

}
