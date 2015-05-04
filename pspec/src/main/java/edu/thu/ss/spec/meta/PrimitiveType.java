package edu.thu.ss.spec.meta;

import java.util.HashMap;
import java.util.Map;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;

public class PrimitiveType extends BaseType {
	protected DataCategory dataCategory;
	protected Map<String, DesensitizeOperation> operations = new HashMap<>();

	public DataCategory getDataCategory() {
		return dataCategory;
	}

	public void setDataCategory(DataCategory category) {
		this.dataCategory = category;
	}

	public DesensitizeOperation getDesensitizeOperation(String udf) {
		return operations.get(udf.toLowerCase());
	}

	public void addDesensitizeOperation(String udf, DesensitizeOperation op) {
		operations.put(udf.toLowerCase(), op);
	}

	@Override
	public PrimitiveType[] toPrimitives() {
		if (primitives == null) {
			primitives = new PrimitiveType[] { this };
		}
		return primitives;
	}

	@Override
	public BaseType[] toSubTypes() {
		if (subTypesArray == null) {
			subTypesArray = toPrimitives();
		}
		return subTypesArray;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCategory == null) ? 0 : dataCategory.hashCode());
		result = prime * result + ((operations == null) ? 0 : operations.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrimitiveType other = (PrimitiveType) obj;
		if (dataCategory == null) {
			if (other.dataCategory != null)
				return false;
		} else if (!dataCategory.equals(other.dataCategory))
			return false;
		if (operations == null) {
			if (other.operations != null)
				return false;
		} else if (!operations.equals(other.operations))
			return false;
		return true;
	}

	@Override
	public String toString(int l) {
		StringBuilder sb = new StringBuilder();
		sb.append(" Data Category: ");
		sb.append(dataCategory.getId());
		//		sb.append("\n");
		//		for (String udf : operations.keySet()) {
		//			sb.append("\tUDF: ");
		//			sb.append(udf);
		//			sb.append("\tDesensitize Operation: ");
		//			sb.append(operations.get(udf).getName());
		//			sb.append("\n");
		//		}
		return sb.toString();
	}

}
