package edu.thu.ss.spec.lang.analyzer.stat;

public class ConsistencyStat extends AnalyzerStat {

	public int count;

	public int levels;

	public int conflicts;

	public int candidates;

	public ConsistencyStat() {
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());

		sb.append("\nLevels: ");
		sb.append(levels);
		sb.append("\nAverage Levels:");
		sb.append((double) levels / count);

		sb.append("\nCandidates: ");
		sb.append(candidates);
		sb.append("\nAverage Candidates:");
		sb.append((double) candidates / count);

		sb.append("\nConflicts: ");
		sb.append(conflicts);
		sb.append("\nAverage Conflicts:");
		sb.append((double) conflicts / count);

		return sb.toString();

	}

}
