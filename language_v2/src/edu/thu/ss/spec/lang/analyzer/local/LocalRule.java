package edu.thu.ss.spec.lang.analyzer.local;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.SetUtil;

public class LocalRule extends ExpandedRule {
	public static class DataActionPair implements Comparable<DataActionPair> {
		protected Set<DataCategory> datas;
		protected Action action;

		public DataActionPair(Set<DataCategory> data, Action action) {
			super();
			this.datas = data;
			this.action = action;
		}

		public void setAction(Action action) {
			this.action = action;
		}

		public Action getAction() {
			return action;
		}

		public Set<DataCategory> getDatas() {
			return datas;
		}

		@Override
		public int compareTo(DataActionPair o) {
			return Integer.compare(datas.size(), o.datas.size());
		}
	}

	protected DataActionPair[] datas;

	public LocalRule(Rule rule, Set<UserCategory> users, Action action, Set<DataCategory> datas, int num) {
		super(rule, users, num);

		this.datas = new DataActionPair[1];
		this.datas[0] = new DataActionPair(datas, action);
		this.restrictions = rule.getRestrictions().toArray(new Restriction[1]);

		if (!this.restrictions[0].isForbid()) {
			Desensitization de = this.restrictions[0].getDesensitization();
			de.materialize(datas);
		}

	}

	public LocalRule(Rule rule, Set<UserCategory> users, DataAssociation association, int num) {
		super(rule, users, num);
		this.datas = new DataActionPair[association.getDataRefs().size()];
		int i = 0;
		for (DataRef ref : association.getDataRefs()) {
			this.datas[i] = new DataActionPair(ref.getMaterialized(), ref.getAction());
			i++;
		}
		Arrays.sort(this.datas);
		List<Restriction> list = rule.getRestrictions();
		this.restrictions = list.toArray(new Restriction[list.size()]);
	}

	public boolean isSingle() {
		return datas.length == 1;
	}

	public boolean isAssociation() {
		return datas.length > 1;
	}

	public DataActionPair[] getDatas() {
		return datas;
	}

	public DataActionPair getData() {
		return datas[0];
	}

	public int getDimension() {
		return datas.length;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule: ");
		sb.append(getRuleId());

		sb.append("\n\t");
		sb.append("Users: ");
		sb.append(SetUtil.format(users, ","));
		sb.append("\n\t");
		for (DataActionPair pair : datas) {
			sb.append("Action: ");
			sb.append(pair.action);
			sb.append("\n\t");
			sb.append("Datas: ");
			sb.append(SetUtil.format(pair.datas, ","));
			sb.append("\n\t");
		}
		for (Restriction restriction : restrictions) {
			sb.append(restriction);
			sb.append("\n\t");
		}
		return sb.toString();

	}

}
