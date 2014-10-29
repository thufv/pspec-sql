package edu.thu.ss.lang.pojo;

import java.util.Set;

public class Desensitization {
	protected Set<DataCategory> datas;
	protected Set<DesensitizeOperation> operations;

	public Desensitization() {
	}

	public Desensitization(Set<DataCategory> datas, Set<DesensitizeOperation> operations) {
		this.datas = datas;
		this.operations = operations;
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
		sb.append("data category: ");
		for (DataCategory data : datas) {
			sb.append(data.getId());
			sb.append(' ');
		}
		sb.append('\t');
		if (operations != null) {
			sb.append("operation: ");
			for (DesensitizeOperation op : operations) {
				sb.append(op.udf);
				sb.append(' ');
			}
		}
		return sb.toString();
	}

}
