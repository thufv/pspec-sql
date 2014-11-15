package edu.thu.ss.spec.meta;

import java.util.HashMap;
import java.util.Map;

public class Table extends DBObject {

	Map<String, Column> columns = new HashMap<>();

	public Column getColumn(String name) {
		return columns.get(name);
	}

	public void addColumn(Column column) {
		if (column == null) {
			return;
		}
		columns.put(column.name, column);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Table: ");
		sb.append(name);
		sb.append("\n");
		for (Column col : columns.values()) {
			sb.append(col);
		}
		return sb.toString();

	}
}
