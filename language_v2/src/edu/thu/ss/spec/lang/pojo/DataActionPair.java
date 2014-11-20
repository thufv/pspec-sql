package edu.thu.ss.spec.lang.pojo;

import java.util.Set;

public class DataActionPair implements Comparable<DataActionPair> {
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
