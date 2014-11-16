package edu.thu.ss.spec.lang.pojo;

import java.util.Set;

import edu.thu.ss.spec.util.SetUtil;

public class Desensitization {
	protected Set<DataCategory> datas;
	protected Set<DesensitizeOperation> operations;
	protected int[] dataIndex;

	public Desensitization() {
	}

	public Desensitization(Set<DataCategory> datas, Set<DesensitizeOperation> operations, int[] dataRefs) {
		this.datas = datas;
		this.operations = operations;
		this.dataIndex = dataRefs;
	}

	public int[] getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(int[] dataIndex) {
		this.dataIndex = dataIndex;
	}

	public boolean isDefaultOperation() {
		return operations == null;
	}

	public Set<DesensitizeOperation> getOperations() {
		return operations;
	}

	public void setOperations(Set<DesensitizeOperation> operations) {
		this.operations = operations;
	}

	public void setDatas(Set<DataCategory> data) {
		this.datas = data;
	}

	public Set<DataCategory> getDatas() {
		return datas;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("data category ref: ");
		for (int i : dataIndex) {
			sb.append(i);
			sb.append(' ');
		}
		sb.append('\t');
		sb.append("operation: ");
		if (operations != null) {
			sb.append(SetUtil.format(operations, " "));
		} else {
			sb.append("default");
		}
		return sb.toString();
	}
}
