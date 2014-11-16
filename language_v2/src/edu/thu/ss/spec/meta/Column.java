package edu.thu.ss.spec.meta;

import java.util.HashMap;
import java.util.Map;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;

public class Column extends DBObject {
	DataCategory dataCategory;
	Map<String, DesensitizeOperation> operations = new HashMap<>();

	public DataCategory getDataCategory() {
		return dataCategory;
	}

	public void setDataCategory(DataCategory category) {
		this.dataCategory = category;
	}

	public DesensitizeOperation getDesensitizeOperation(String udf) {
		return operations.get(udf);
	}

	public void addDesensitizeOperation(String udf, DesensitizeOperation op) {
		operations.put(udf, op);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Column: ");
		sb.append(name);

		sb.append("\tData Category: ");
		sb.append(dataCategory.getId());
		sb.append("\n");
		for (String udf : operations.keySet()) {
			sb.append("\tUDF: ");
			sb.append(udf);
			sb.append("\tDesensitize Operation: ");
			sb.append(operations.get(udf).getName());
			sb.append("\n");
		}
		return sb.toString();
	}
}
