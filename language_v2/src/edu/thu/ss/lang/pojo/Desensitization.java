package edu.thu.ss.lang.pojo;

import java.util.HashSet;
import java.util.Set;

import edu.thu.ss.lang.xml.XMLDataCategoryRef;

public class Desensitization {
	protected Set<DataCategory> datas;
	protected Set<DesensitizeOperation> operations;

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
