package edu.thu.ss.lang.analyzer;

import edu.thu.ss.lang.pojo.Policy;

class CachedConsistencySearcher extends ConsistencySearcher {

	public CachedConsistencySearcher(Policy policy) {
		super(policy);
	}

	@Override
	protected void beginLevel(int level) {
	}

	@Override
	protected void endLevel(int level) {
	};

	@Override
	protected boolean process(SearchKey key) {
		return false;
	}
}
