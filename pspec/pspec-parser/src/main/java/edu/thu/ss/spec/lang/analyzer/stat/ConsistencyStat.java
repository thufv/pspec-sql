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

		sb.append("\n###levels ");
		sb.append(levels);
		sb.append("\n###average_levels ");
		sb.append((double) levels / count);

		sb.append("\n###candidates ");
		sb.append(candidates);
		sb.append("\n###average_candidates ");
		sb.append((double) candidates / count);

		sb.append("\n###conflicts ");
		sb.append(conflicts);
		sb.append("\n###average_conflicts ");
		sb.append((double) conflicts / count);

		sb.append("\n###seeds ");
		sb.append(count);
		return sb.toString();

	}

}
