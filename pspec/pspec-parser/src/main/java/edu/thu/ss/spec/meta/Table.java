package edu.thu.ss.spec.meta;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.thu.ss.spec.lang.pojo.Writable;
import edu.thu.ss.spec.meta.xml.MetaParserConstant;
import edu.thu.ss.spec.util.PSpecUtil;

public class Table extends DBObject implements Writable {
	private static Logger logger = LoggerFactory.getLogger(Table.class);

	Map<String, Column> columns = new LinkedHashMap<>();

	Map<String, ConditionalColumn> condColumns = new LinkedHashMap<>();

	Set<JoinCondition> conditions = null;

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

	public Set<JoinCondition> getAllConditions() {
		if (conditions == null) {
			conditions = new HashSet<>();
			for (ConditionalColumn col : condColumns.values()) {
				conditions.addAll(col.getConditions());
			}
		}
		return conditions;
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
		BaseType type = condColumn.getType(join);
		if (type != null) {
			logger.error(
					"Column: {} should not be mapped to multiple types under the same join condition: {}",
					column.name, join);
			return true;
		}
		condColumn.addType(join, column.type);
		return false;
	}

	public Map<String, Column> getColumns() {
		return columns;
	}

	public Map<String, ConditionalColumn> getCondColumns() {
		return condColumns;
	}

	public boolean overlap() {
		boolean error = false;
		for (String column : columns.keySet()) {
			if (condColumns.containsKey(column)) {
				logger.error(
						"Column: {} should not mapped to both data category and conditional data category.",
						column);
				error = true;
			}
		}

		return error;
	}

	public String toString(int l) {
		StringBuilder sb = new StringBuilder();
		sb.append(PSpecUtil.spaces(l));
		sb.append("Table: ");
		sb.append(name);
		sb.append("\n");
		for (Column column : columns.values()) {
			sb.append(column.toString(l + 1));
		}
		for (ConditionalColumn column : condColumns.values()) {
			sb.append(column.toString(l + 1));
		}
		return sb.toString();
	}

	@Override
	public Element outputType(Document document, String name) {
		return null;
	}

	@Override
	public Element outputElement(Document document) {
		Element table = document.createElement(MetaParserConstant.Ele_Table);
		table.setAttribute(MetaParserConstant.Attr_Name, name);
		boolean isLabel = false;
		for (String columnName : columns.keySet()) {
			Element column = columns.get(columnName).outputElement(document);
			if (column != null) {
				isLabel = true;
				table.appendChild(column);
			}
		}
		if (isLabel) {
			return table;
		} else {
			return null;
		}

	}

}
