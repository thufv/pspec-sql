package edu.thu.ss.spec.lang.analyzer;

import edu.thu.ss.spec.lang.analyzer.stat.AnalyzerStat;
import edu.thu.ss.spec.lang.pojo.Policy;

public class TiminingAnalyzer extends BasePolicyAnalyzer {

	private IPolicyAnalyzer analyzer;
	private AnalyzerStat stat;
	private int n;

	public TiminingAnalyzer(IPolicyAnalyzer analyzer, AnalyzerStat stat, int n) {
		super(null);
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
