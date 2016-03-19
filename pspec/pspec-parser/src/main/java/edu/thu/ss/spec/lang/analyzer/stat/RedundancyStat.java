package edu.thu.ss.spec.lang.analyzer.stat;

public class RedundancyStat extends AnalyzerStat {

	public int rules;

	public RedundancyStat() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\nRedundant rules: ");

		sb.append(rules);

		return sb.toString();
	}

}
