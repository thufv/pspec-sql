package edu.thu.ss.editor.model;

import java.util.List;

import edu.thu.ss.editor.hive.HiveConnection;
import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.spec.meta.Column;
import edu.thu.ss.spec.meta.Database;
import edu.thu.ss.spec.meta.Table;
import edu.thu.ss.spec.meta.xml.XMLMetaRegistry;

public class MetadataModel extends BaseModel {

	private XMLMetaRegistry registry;

	private HiveConnection connection;

	public MetadataModel(XMLMetaRegistry registry, String path) {
		super(path);
		this.registry = registry;
		// TODO Auto-generated constructor stub
	}

	public MetadataModel(String path) {
		super(path);
	}

	public String getPath() {
		return path;
	}

	public void init(XMLMetaRegistry registry) {
		this.registry = registry;
	}
	
	public void connect(String host, String port, String username, String password) {
		connection = new HiveConnection(host, port, username, password);
		connection.resolve(registry);
	}
	
	public XMLMetaRegistry getRegistry() {
		// TODO Auto-generated method stub
		return registry;
	}

	public boolean addColumnExtraction(String databaseName, String tableName, String columnName,
			String extractionName, String label) {
		Column column = getColumn(databaseName, tableName, columnName);
		if (column == null) {
			return false;
		}
		column.addExtraction(extractionName, label);
		return true;
	}
	
	private Column getColumn(String databaseName, String tableName, String columnName) {
		Database database = registry.getDatabases().get(databaseName);
		if (database == null) {
			return null;
		}
		Table table = database.getTable(tableName);
		if (table == null) {
			return null;
		}
		Column column = table.getColumn(columnName);
		if (column == null) {
			return null;
		}
		return column;
	}
}
