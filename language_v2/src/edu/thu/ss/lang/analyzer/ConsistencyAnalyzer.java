package edu.thu.ss.lang.analyzer;

import java.util.List;
import java.util.Map;

import edu.thu.ss.lang.pojo.ExpandedRule;
import edu.thu.ss.lang.pojo.Policy;

public class ConsistencyAnalyzer extends BasePolicyAnalyzer {

	@Override
	public boolean analyze(Policy policy) {

		PolicyConsistencySearcher searcher = new PolicyConsistencySearcher(policy);
		searcher.search();
		return false;
	}

	private class PolicyConsistencySearcher extends LevelwiseSearcher<ExpandedRule> {
		private Policy policy;

		public PolicyConsistencySearcher(Policy policy) {
			this.policy = policy;
		}

		@Override
		protected void initLevel(List<SearchKey> currentLevel, Map<SearchKey, ExpandedRule> currentIndex) {
			List<ExpandedRule> rules = policy.getExpandedRules();
			int i = 0;
			for (ExpandedRule rule : rules) {
				SearchKey key = new SearchKey(i);
				currentLevel.add(key);
				currentIndex.put(key, rule);
				i++;
			}
		}

		@Override
		protected ExpandedRule process(SearchKey key, Map<SearchKey, ExpandedRule> currentIndex) {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
