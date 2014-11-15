package edu.thu.ss.spec.lang.pojo;

import java.util.Set;

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
			for (DesensitizeOperation op : operations) {
				sb.append(op.name);
				sb.append(' ');
			}
		} else {
			sb.append("default");
		}
		return sb.toString();
	}
}
