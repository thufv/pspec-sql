package edu.thu.ss.spec.meta;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.DataCategory;

public class Table extends DBObject {
	private static Logger logger = LoggerFactory.getLogger(Table.class);

	Map<String, Column> columns = new HashMap<>();

	Map<String, ConditionalColumn> condColumns = new HashMap<>();

	public Column getColumn(String name) {
		return columns.get(name);
	}

	public ConditionalColumn getConditionalColumn(String name) {
		return condColumns.get(name);
	}

	public void addColumn(Column column) {
		if (column == null) {
			return;
		}
		columns.put(column.name, column);
	}

	public void addConditionalColumn(ConditionalColumn condColumn) {
		if (condColumn == null) {
			return;
		}
		condColumns.put(condColumn.name, condColumn);
	}

	public boolean addConditionalColumn(JoinCondition join, Column column) {
		ConditionalColumn condColumn = condColumns.get(column.name);
		if (condColumn == null) {
			condColumn = new ConditionalColumn();
			condColumn.name = column.name;
			condColumns.put(condColumn.name, condColumn);
		}
		DataCategory data = condColumn.getDataCategory(join);
		if (data != null && !data.equals(column.dataCategory)) {
			logger.error(
					"Column: {} should not be mapped to different data categories: {} and {}, under the same join condition: {}",
					column.name, data, column.dataCategory, join);
			return true;
		}
		condColumn.dataCategories.put(join, column.dataCategory);
		condColumn.operations.put(column.dataCategory, column.operations);
		return false;
	}

	public Map<String, Column> getColumns() {
		return columns;
	}

	public boolean overlap() {
		boolean error = false;
		for (String column : columns.keySet()) {
			if (condColumns.containsKey(column)) {
				logger.error("Column: {} should not mapped to both data category and conditional data category.", column);
				error = true;
			}
		}

		return error;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Table: ");
		sb.append(name);
		sb.append("\n");
		for (Column column : columns.values()) {
			sb.append(column);
		}
		for (ConditionalColumn column : condColumns.values()) {
			sb.append(column);
		}
		return sb.toString();
	}

}
