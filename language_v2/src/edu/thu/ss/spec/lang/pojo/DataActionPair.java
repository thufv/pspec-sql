package edu.thu.ss.spec.lang.pojo;

import java.util.Set;

public class DataActionPair implements Comparable<DataActionPair> {
	protected Set<DataCategory> datas;
	protected Action action;
	protected int label;

	public DataActionPair(Set<DataCategory> data, Action action, int label) {
		super();
		this.datas = data;
		this.action = action;
		this.label = label;
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

	public int getLabel() {
		return label;
	}

	@Override
	public int compareTo(DataActionPair o) {
		return Integer.compare(label, o.label);
	}
}
