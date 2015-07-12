package edu.thu.ss.spec.lang.analyzer.consistency;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.LocalExpander;
import edu.thu.ss.spec.lang.analyzer.stat.AnalyzerStat;
import edu.thu.ss.spec.lang.analyzer.stat.ConsistencyStat;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PolicyEvent;
import edu.thu.ss.spec.lang.pojo.Policy;

/**
 * consistency analyzer depends on {@link LocalExpander}
 * 
 * @author luochen
 * 
 */
public class ConsistencyAnalyzer extends BasePolicyAnalyzer {

	public ConsistencyAnalyzer(EventTable<PolicyEvent> table) {
		super(table);
	}

	public enum RuleRelation {
		disjoint, conflict, consistent, forbid
	}

	@Override
	public boolean analyze(Policy policy, AnalyzerStat stat, int n) {
		ConsistencySearcher searcher = new CachedConsistencySearcher(policy, (ConsistencyStat) stat, n);
		searcher.search();
		return false;
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
