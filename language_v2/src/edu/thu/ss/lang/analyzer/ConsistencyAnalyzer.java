package edu.thu.ss.lang.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.lang.pojo.Policy;

public class ConsistencyAnalyzer extends BasePolicyAnalyzer {

	private static Logger logger = LoggerFactory.getLogger(ConsistencyAnalyzer.class);

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
