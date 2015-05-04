package edu.thu.ss.spec.lang.analyzer.stat;

public class ConsistencyStat extends AnalyzerStat {

	public int levels[];

	public int conflicts[];

	public ConsistencyStat(int n) {
		super(n);
		this.levels = new int[n];
		this.conflicts = new int[n];
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());

		sb.append("\nLevel: ");
		int totalLevel = 0;
		for (int l : levels) {
			sb.append(l);
			sb.append('\t');
			totalLevel += l;
		}
		sb.append("\nTotal Level: ");
		sb.append(totalLevel / levels.length);

		sb.append("\nConflicts: ");
		int totalConflicts = 0;
		for (int c : conflicts) {
			sb.append(c);
			sb.append('\t');
			totalConflicts += c;
		}
		sb.append("\nTotal Conflicts: ");
		sb.append(totalConflicts / conflicts.length);

		return sb.toString();

	}

}
