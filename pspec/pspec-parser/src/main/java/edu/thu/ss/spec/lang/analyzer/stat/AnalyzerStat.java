package edu.thu.ss.spec.lang.analyzer.stat;

public abstract class AnalyzerStat {

	public long[] time;
	public int[] line;

	public AnalyzerStat(int n) {
		time = new long[n];
		line = new int[n];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Time: ");
		long totalTime = 0;
		for (long t : time) {
			sb.append(t);
			sb.append('\t');
			totalTime += t;
		}
		sb.append("\nAverage Time: ");
		sb.append(totalTime / time.length);
		sb.append(" ms");
		sb.append("\nLine: ");
		int totalLine = 0;
		for (int l : line) {
			sb.append(l);
			sb.append('\t');
			totalLine += l;
		}
		sb.append("\nAverage Line: ");
		sb.append(totalLine / line.length);

		return sb.toString();

	}
}
