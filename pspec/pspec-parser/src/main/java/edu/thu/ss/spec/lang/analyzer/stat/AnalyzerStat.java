package edu.thu.ss.spec.lang.analyzer.stat;

public abstract class AnalyzerStat {

	public long time;

	public AnalyzerStat() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("###time ");
		sb.append(time);

		return sb.toString();

	}
}
