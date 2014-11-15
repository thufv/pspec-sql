package edu.thu.ss.spec.meta;

import java.util.HashMap;
import java.util.Map;

public class Database extends DBObject {

	Map<String, Table> tables = new HashMap<>();

	public Table getTable(String name) {
		return tables.get(name);
	}

	public void addTable(Table table) {
		if (table == null) {
			return;
		}
		tables.put(table.name, table);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Database: ");
		sb.append(name);
		sb.append("\n");
		for (Table table : tables.values()) {
			sb.append(table);
			sb.append('\n');
		}
		return sb.toString();

	}

}
