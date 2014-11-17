package edu.thu.ss.spec.meta.xml;

import java.util.HashMap;
import java.util.Map;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.meta.Column;
import edu.thu.ss.spec.meta.ConditionalColumn;
import edu.thu.ss.spec.meta.Database;
import edu.thu.ss.spec.meta.JoinCondition;
import edu.thu.ss.spec.meta.MetaRegistry;
import edu.thu.ss.spec.meta.Table;

public class XMLMetaRegistry implements MetaRegistry {

	private Map<String, Database> databases = new HashMap<>();

	public void addDatabase(Database database) {
		this.databases.put(database.getName(), database);
	}

	@Override
	public Map<String, Database> getDatabases() {
		return databases;
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
			return column.getDataCategory();
		}
	}

	private Table lookupTable(String databaseName, String tableName) {
		Database database = databases.get(databaseName);
		if (database == null) {
			return null;
		}
		return database.getTable(tableName);
	}

	@Override
	public DesensitizeOperation lookup(DataCategory data, String udf, String databaseName, String tableName,
			String columnName) {
		Column column = lookupColumn(databaseName, tableName, columnName);
		DesensitizeOperation op = null;
		if (column != null) {
			if (!column.getDataCategory().equals(data)) {
				throw new RuntimeException("Target data category: " + data + " is inconsistent with original data category: "
						+ column.getDataCategory() + " for column: " + column);
			}
			op = column.getDesensitizeOperation(udf);
		} else {
			ConditionalColumn condColumn = lookupConditionalColumn(databaseName, tableName, columnName);
			if (condColumn != null) {
				op = condColumn.getOperation(data, udf);
			}
		}
		if (op != null) {
			return op;
		} else {
			return data.getOperation(udf);
		}
	}

	@Override
	public Map<JoinCondition, DataCategory> conditionalLookup(String databaseName, String tableName, String columnName) {
		ConditionalColumn column = lookupConditionalColumn(databaseName, tableName, columnName);
		if (column == null) {
			return null;
		}
		return column.getDataCategories();
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

	private Column lookupColumn(String databaseName, String tableName, String columnName) {
		Table table = lookupTable(databaseName, tableName);
		if (table == null) {
			return null;
		}
		return table.getColumn(columnName);
	}

	private ConditionalColumn lookupConditionalColumn(String databaseName, String tableName, String columnName) {
		Table table = lookupTable(databaseName, tableName);
		if (table == null) {
			return null;
		}
		return table.getConditionalColumn(columnName);
	}

}
