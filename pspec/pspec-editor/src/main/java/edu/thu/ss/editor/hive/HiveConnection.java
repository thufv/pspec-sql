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

	public void resolve(XMLMetaRegistry registry) {
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
