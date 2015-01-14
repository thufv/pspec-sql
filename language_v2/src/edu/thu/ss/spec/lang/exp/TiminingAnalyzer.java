package edu.thu.ss.spec.lang.exp;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.PolicyAnalyzer;
import edu.thu.ss.spec.lang.pojo.Policy;

public class TiminingAnalyzer extends BasePolicyAnalyzer {

	private PolicyAnalyzer analyzer;
	private ExperimentStat stat;
	private int n;

	public TiminingAnalyzer(PolicyAnalyzer analyzer, ExperimentStat stat, int n) {
		this.analyzer = analyzer;
		this.stat = stat;
		this.n = n;
	}

	@Override
	public boolean analyze(Policy policy) {

		long start = System.currentTimeMillis();
		try {
			return analyzer.analyze(policy, stat, n);
		} finally {
			long end = System.currentTimeMillis();
			long time = end - start;
			stat.time[n] = time;
			System.out.println("Analyzing finish in " + time + " ms.");
		}
	}

}
