package edu.thu.ss.spec.meta;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;

public class ConditionalColumn extends DBObject {
	Map<JoinCondition, DataCategory> dataCategories = new HashMap<>();

	Map<DataCategory, Map<String, DesensitizeOperation>> operations = new HashMap<>();

	public Map<JoinCondition, DataCategory> getDataCategories() {
		return dataCategories;
	}

	public DataCategory getDataCategory(JoinCondition join) {
		return dataCategories.get(join);
	}

	public DesensitizeOperation getOperation(DataCategory data, String udf) {
		Map<String, DesensitizeOperation> ops = operations.get(data);
		if (ops == null) {
			return null;
		}
		return ops.get(udf);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Column: ");
		sb.append(name);
		sb.append("\n");
		for (JoinCondition join : dataCategories.keySet()) {
			sb.append("Join: ");
			sb.append(join);
			sb.append("\n\t");
			sb.append("Data Category: ");
			DataCategory data = dataCategories.get(join);
			sb.append(data);
			sb.append("\n");
			Map<String, DesensitizeOperation> op = operations.get(data);
			if (op.size() > 0) {
				for (String udf : op.keySet()) {
					sb.append("\tUDF: ");
					sb.append(udf);
					sb.append(" Desensitize Operation: ");
					sb.append(op.get(udf).getName());
				}
				sb.append("\n");
			}

		}
		return sb.toString();

	}

	public Collection<JoinCondition> getConditions() {
		return dataCategories.keySet();
	}
}
