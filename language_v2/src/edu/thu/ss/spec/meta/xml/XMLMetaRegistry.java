package edu.thu.ss.spec.meta.xml;

import java.util.HashMap;
import java.util.Map;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.meta.Column;
import edu.thu.ss.spec.meta.Database;
import edu.thu.ss.spec.meta.MetaRegistry;
import edu.thu.ss.spec.meta.Table;

public class XMLMetaRegistry implements MetaRegistry {

	private Map<String, Database> databases = new HashMap<>();

	public void addDatabase(Database database) {
		this.databases.put(database.getName(), database);
	}

	@Override
	public UserCategory currentUser() {
		return null;
	}

	@Override
	public DataCategory lookup(String databaseName, String tableName, String columnName) {
		Column column = lookupColumn(databaseName, tableName, columnName);
		if (column == null) {
			return null;
		} else {
			return column.getCategory();
		}
	}

	private Column lookupColumn(String databaseName, String tableName, String columnName) {
		Database database = databases.get(databaseName);
		if (database == null) {
			return null;
		}
		Table table = database.getTable(tableName);
		if (table == null) {
			return null;
		}
		return table.getColumn(columnName);
	}

	@Override
	public DesensitizeOperation lookup(String udf, DataCategory data, String databaseName, String tableName,
			String columnName) {
		Column column = lookupColumn(databaseName, tableName, columnName);
		if (column != null) {
			DesensitizeOperation op = column.getDesensitizeOperation(udf);
			if (op != null) {
				return op;
			}
		}
		return data.getOperation(udf);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String db : databases.keySet()) {
			sb.append(databases.get(db));
			sb.append('\n');
		}
		return sb.toString();
	}
}
