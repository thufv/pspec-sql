package edu.thu.ss.editor.hive;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.hive.HiveContext;

import edu.thu.ss.spec.meta.Column;
import edu.thu.ss.spec.meta.Database;
import edu.thu.ss.spec.meta.Table;
import edu.thu.ss.spec.meta.xml.XMLMetaRegistry;

public class HiveConnection {

	private HiveContext context;

	//default constructor for local test
	public HiveConnection() {
		SparkConf conf = new SparkConf().setMaster("local[1]").setAppName("localnode");
		context = new HiveContext(new SparkContext(conf));
	}

	public HiveConnection(String host, String port, String username, String password) {
		SparkConf conf = new SparkConf().setMaster("local[1]").setAppName("localnode");
		conf.set("spark.driver.host", host);
		conf.set("spark.driver.port", port);
		//TODO modify hive-site.xml?
		context = new HiveContext(new SparkContext(conf));
	}

	public DataFrame sql(String sqlText) {
		return context.sql(sqlText);
	}

	private String[] getDatabases() {
		DataFrame df = context.sql("show databases");
		Row[] rows = (Row[]) df.collect();
		String[] databases = new String[rows.length];
		for (int i = 0; i < rows.length; i++) {
			databases[i] = rows[i].getString(0);
		}
		return databases;
	}
	
	private String[] getTables(String database) {
		context.sql("use " + database);
		String[] tables = context.tableNames(database);
		return tables;
	}
	
	private String[] getColumns(String database, String table) {
		context.sql("use " + database);
		DataFrame df = context.table(table);
		String[] columns = df.columns();
		return columns;
	}
	
	private boolean contains(String[] strs, String str) {
		for (String item : strs) {
			if (item.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean match(XMLMetaRegistry registry) {
		String[] databases = getDatabases();
		for (String database : registry.getDatabases().keySet()) {
			if (!contains(databases, database)) {
				return false;
			}
			if (!matchDatabase(registry, database)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean matchDatabase(XMLMetaRegistry registry, String databaseName) {
		Database database = registry.getDatabase(databaseName);
		String[] tables = getTables(databaseName);
		for (String table : database.getTables().keySet()) {
			if (!contains(tables, table)) {
				return false;
			}
			if (!matchTable(registry, databaseName, table)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean matchTable(XMLMetaRegistry registry, String databaseName, String tableName) {
		Table table = registry.getDatabase(databaseName).getTable(tableName);
		String[] columns = getColumns(databaseName, tableName);
		for (String column : table.getColumns().keySet()) {
			if (!contains(columns, column)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean resolve(XMLMetaRegistry registry) {
		if (!match(registry)) {
			return false;
		}
		DataFrame df = context.sql("show databases");
		Row[] rows = (Row[]) df.collect();
		for (Row row : rows) {
			String databaseName = row.getString(0);
			Database database;
			if (registry.getDatabases().containsKey(databaseName)) {
				database = registry.getDatabases().get(databaseName);
			} else {
				database = new Database();
				database.setName(databaseName);
				registry.addDatabase(database);
			}
			resolveDatabase(registry, database);
		}
		return true;
	}

	private void resolveDatabase(XMLMetaRegistry registry, Database database) {
		context.sql("use " + database.getName());
		String[] tableNames = context.tableNames(database.getName());
		for (String tableName : tableNames) {
			Table table;
			if (database.getTables().containsKey(tableName)) {
				table = database.getTable(tableName);
			} else {
				table = new Table();
				table.setName(tableName);
				database.addTable(table);
			}
			resolveTable(registry, table);
		}
	}

	private void resolveTable(XMLMetaRegistry registry, Table table) {
		DataFrame df = context.table(table.getName());
		String[] columns = df.columns();
		for (String column : columns) {
			Column col;
			if (table.getColumns().containsKey(column)) {
				col = table.getColumn(column);
			} else {
				col = new Column();
				col.setName(column);
				table.addColumn(col);
			}

		}
	}
}
