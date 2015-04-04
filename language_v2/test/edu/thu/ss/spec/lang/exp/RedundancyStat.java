package edu.thu.ss.spec.lang.exp;

public class RedundancyStat extends ExperimentStat {

	public int rules[];

	public RedundancyStat(int n) {
		super(n);
		this.rules = new int[n];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\nRedundant rules: ");

		int totalRule = 0;
		for (int rule : rules) {
			sb.append(rule);
			sb.append('\t');
			totalRule += rule;
		}

		sb.append("\nAverage Redundant Rules: ");
		sb.append(totalRule / rules.length);

		return sb.toString();
	}

}
