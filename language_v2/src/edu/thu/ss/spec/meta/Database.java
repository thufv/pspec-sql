package edu.thu.ss.spec.meta;

import java.util.LinkedHashMap;
import java.util.Map;

public class Database extends DBObject {

	Map<String, Table> tables = new LinkedHashMap<>();

	public Table getTable(String name) {
		return tables.get(name);
	}

	public void addTable(Table table) {
		if (table == null) {
			return;
		}
		tables.put(table.name, table);
	}

	public Map<String, Table> getTables() {
		return tables;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Database: ");
		sb.append(name);
		sb.append("\n");
		for (Table table : tables.values()) {
			sb.append(table.toString(1));
			sb.append('\n');
		}
		return sb.toString();

	}

}
