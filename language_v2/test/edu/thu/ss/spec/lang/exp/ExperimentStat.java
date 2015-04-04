package edu.thu.ss.spec.lang.exp;

public abstract class ExperimentStat {

	long[] time;
	int[] line;

	public ExperimentStat(int n) {
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
