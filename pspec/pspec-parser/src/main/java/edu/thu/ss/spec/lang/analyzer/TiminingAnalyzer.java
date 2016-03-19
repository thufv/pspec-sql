package edu.thu.ss.spec.lang.analyzer;

import edu.thu.ss.spec.lang.analyzer.stat.AnalyzerStat;
import edu.thu.ss.spec.lang.pojo.Policy;

public class TiminingAnalyzer<T extends AnalyzerStat> extends BasePolicyAnalyzer<T> {

	private IPolicyAnalyzer<T> analyzer;
	private T stat;

	public TiminingAnalyzer(IPolicyAnalyzer<T> analyzer, T stat) {
		super(null);
		this.analyzer = analyzer;
		this.stat = stat;
	}

	@Override
	public boolean analyze(Policy policy) throws Exception {
		long start = System.currentTimeMillis();
		try {
			return analyzer.analyze(policy, stat);
		} finally {
			long end = System.currentTimeMillis();
			long time = end - start;
			stat.time = time;
			System.out.println("Analyzing finish in " + time + " ms.");
		}
	}

}
