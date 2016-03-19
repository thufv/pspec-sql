package edu.thu.ss.spec.lang.analyzer.stat;

public abstract class AnalyzerStat {

	public long time;

	public AnalyzerStat() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Time: ");
		sb.append(time);
		sb.append(" ms");

		return sb.toString();

	}
}
