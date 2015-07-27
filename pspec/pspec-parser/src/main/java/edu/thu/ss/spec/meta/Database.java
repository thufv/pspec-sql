package edu.thu.ss.spec.meta;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.thu.ss.spec.lang.pojo.Writable;
import edu.thu.ss.spec.meta.xml.MetaParserConstant;

public class Database extends DBObject implements Writable {

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

	@Override
	public Element outputType(Document document, String name) {
		return null;
	}

	@Override
	public Element outputElement(Document document) {
		Element database = document.createElement(MetaParserConstant.Ele_Database);
		database.setAttribute(MetaParserConstant.Attr_Name, name);
		boolean isLabel = false;
		for (String tableName : tables.keySet()) {
			Element table = tables.get(tableName).outputElement(document);
			if (table != null) {
				isLabel = true;
				database.appendChild(table);
			}			
		}
		if (isLabel) {
			return database;
		} else {
			return null;
		}
		
	}

}
