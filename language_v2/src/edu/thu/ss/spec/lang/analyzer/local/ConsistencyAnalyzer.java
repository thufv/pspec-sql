package edu.thu.ss.spec.lang.analyzer.local;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.pojo.Policy;

/**
 * consistency analyzer
 * depends on {@link LocalExpander}
 * @author luochen
 *
 */
public class ConsistencyAnalyzer extends BasePolicyAnalyzer {

	public enum RuleRelation {
		disjoint, conflict, consistent, forbid
	}

	/**
	 * support {@link ConsistencySearcher} and {@link CachedConsistencySearcher}
	 */
	@Override
	public boolean analyze(Policy policy) {

		ConsistencySearcher searcher = new CachedConsistencySearcher(policy);
		searcher.search();
		return false;
	}

}
