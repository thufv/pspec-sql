package edu.thu.ss.spec.meta.xml;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.meta.Column;
import edu.thu.ss.spec.meta.Database;
import edu.thu.ss.spec.meta.Table;
import edu.thu.ss.spec.util.ParsingException;
import edu.thu.ss.spec.util.XMLUtil;

public class XMLMetaRegistryParser implements MetaParserConstant {

	private static Logger logger = LoggerFactory.getLogger(XMLMetaRegistryParser.class);

	private boolean error = false;
	private XMLMetaRegistry registry = null;
	private Policy policy = null;
	private Map<String, DesensitizeOperation> udfs = null;

	public XMLMetaRegistryParser(Policy policy) {
		this.policy = policy;
	}

	public XMLMetaRegistry parse(String path) throws ParsingException {
		init();
		Document policyDoc = null;
		try {
			// load document
			policyDoc = XMLUtil.parseDocument(path, Meta_Schema_Location);
		} catch (Exception e) {
			throw new ParsingException("Fail to load meta file at :" + path, e);
		}
		try {
			// parse document
			Node rootNode = policyDoc.getElementsByTagName(Ele_Root).item(0);
			
			String policyPath = XMLUtil.getAttrValue(rootNode, Attr_Policy);

			NodeList dbList = policyDoc.getElementsByTagName(Ele_Database);
			for (int i = 0; i < dbList.getLength(); i++) {
				Node node = dbList.item(i);
				Database database = parseDatabase(node);
				registry.addDatabase(database);
			}
		} catch (Exception e) {
			throw new ParsingException("Fail to parse meta file at " + path, e);
		}
		if (error) {
			throw new ParsingException("Error occured when parsing meta file at {}, see error messages above.");
		} else {
			return registry;
		}

	}

	private void init() {
		registry = new XMLMetaRegistry();
		udfs = new HashMap<>();
	}

	private Database parseDatabase(Node dbNode) {
		Database database = new Database();
		String dbName = XMLUtil.getAttrValue(dbNode, Attr_Name);
		database.setName(dbName);

		NodeList list = dbNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Table.equals(name)) {
				Table table = parseTable(node);
				database.addTable(table);
			}
		}
		return database;
	}

	private Table parseTable(Node tableNode) {
		Table table = new Table();
		String tableName = XMLUtil.getAttrValue(tableNode, Attr_Name);
		table.setName(tableName);
		NodeList list = tableNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Column.equals(name)) {
				Column column = parseColumn(node);
				table.addColumn(column);
			}
		}
		return table;
	}

	private Column parseColumn(Node columnNode) {
		Column column = new Column();
		String columnName = XMLUtil.getAttrValue(columnNode, Attr_Name);
		column.setName(columnName);

		String dataCategoryId = XMLUtil.getAttrValue(columnNode, Attr_Data_Category);
		DataCategory dataCategory = policy.getDatas().get(dataCategoryId);
		if (dataCategory == null) {
			logger.error("Cannot locate data category: {}.", dataCategoryId);
			error = true;
			return column;
		}
		column.setCategory(dataCategory);

		NodeList list = columnNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Desensitize_Operation.equals(name)) {
				parseDesensitizeOperation(node, column);
			}
		}
		return column;
	}

	private void parseDesensitizeOperation(Node deNode, Column column) {
		String opName = XMLUtil.getAttrValue(deNode, Attr_Name);
		DataCategory data = column.getCategory();
		DesensitizeOperation op = data.getOperation(opName);
		if (op == null) {
			logger.error("Desensitize operation: {} is not supported by data category: {}", opName, data.getId());
			error = true;
			return;
		}
		NodeList list = deNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Desensitize_UDF.equals(name)) {
				String udf = node.getTextContent();
				checkOperationMapping(udf, op);
				column.addDesensitizeOperation(udf, op);
			}
		}
	}

	private void checkOperationMapping(String udf, DesensitizeOperation op) {
		DesensitizeOperation op2 = udfs.get(udf);
		if (op2 == null) {
			udfs.put(udf, op);
		} else if (!op.equals(op2)) {
			logger.error("UDF: {} should not be mapped to multiple desensitize operations: {} and {}.", udf, op.getName(),
					op2.getName());
			error = true;
		}
	}

}
