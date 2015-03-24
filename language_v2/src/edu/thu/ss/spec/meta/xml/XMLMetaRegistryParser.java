package edu.thu.ss.spec.meta.xml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.global.MetaManager;
import edu.thu.ss.spec.global.PolicyManager;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.meta.ArrayType;
import edu.thu.ss.spec.meta.BaseType;
import edu.thu.ss.spec.meta.Column;
import edu.thu.ss.spec.meta.CompositeType;
import edu.thu.ss.spec.meta.Database;
import edu.thu.ss.spec.meta.JoinCondition;
import edu.thu.ss.spec.meta.MapType;
import edu.thu.ss.spec.meta.CompositeType.ExtractOperation;
import edu.thu.ss.spec.meta.MapType.EntryType;
import edu.thu.ss.spec.meta.MetaRegistry;
import edu.thu.ss.spec.meta.PrimitiveType;
import edu.thu.ss.spec.meta.StructType;
import edu.thu.ss.spec.meta.StructType.FieldType;
import edu.thu.ss.spec.meta.Table;
import edu.thu.ss.spec.util.ParsingException;
import edu.thu.ss.spec.util.XMLUtil;

public class XMLMetaRegistryParser implements MetaParserConstant {

  private static Logger logger = LoggerFactory.getLogger(XMLMetaRegistryParser.class);

  private boolean error = false;
  private XMLMetaRegistry registry = null;
  private Policy policy = null;
  private Map<String, DesensitizeOperation> udfs = null;

  public MetaRegistry parse(String path) throws ParsingException {
    init();
    Document policyDoc = null;
    try {
      // load document
      policyDoc = XMLUtil.parseDocument(XMLUtil.toUri(path), Meta_Schema_Location);
    } catch (Exception e) {
      throw new ParsingException("Fail to load meta file at :" + path, e);
    }
    try {
      // parse document
      Node rootNode = policyDoc.getElementsByTagName(Ele_Root).item(0);

      String policyPath = XMLUtil.getAttrValue(rootNode, Attr_Policy);
      policy = PolicyManager.getPolicy(XMLUtil.toUri(policyPath));
      if (policy == null) {
        throw new ParsingException("Policy: " + policyPath + " has not been loaded yet.");
      }
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
      throw new ParsingException("Error occured when parsing meta file at " + path
          + ", see error messages above.");
    }

    registry.setPolicy(policy);
    MetaRegistry result = new MetaRegistryProxy(registry);
    MetaManager.add(result);
    return result;
  }

  private void init() {
    registry = new XMLMetaRegistry();
    udfs = new HashMap<>();
  }

  private Database parseDatabase(Node dbNode) {
    Database database = new Database();
    String dbName = XMLUtil.getLowerAttrValue(dbNode, Attr_Name);
    database.setName(dbName);

    NodeList list = dbNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (Ele_Table.equals(name)) {
        Table table = parseTable(node);
        checkTable(database, table);
        database.addTable(table);
      }
    }
    return database;
  }

  private Table parseTable(Node tableNode) {
    Table table = new Table();
    String tableName = XMLUtil.getLowerAttrValue(tableNode, Attr_Name);
    table.setName(tableName);
    NodeList list = tableNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (Ele_Column.equals(name)) {
        Column column = parseColumn(node);
        table.addColumn(column);
      } else if (Ele_Condition.equals(name)) {
        parseCondition(node, table);
      }
    }

    error = error || table.overlap();
    return table;
  }

  private void parseCondition(Node condNode, Table table) {
    Set<JoinCondition> joins = new HashSet<>();
    List<Column> columns = new LinkedList<>();
    NodeList list = condNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (Ele_Column.equals(name)) {
        Column column = parseColumn(node);
        columns.add(column);
      } else if (Ele_Join.equals(name)) {
        JoinCondition join = parseJoin(node);
        joins.add(join);
      }
    }

    for (Column column : columns) {
      for (JoinCondition join : joins) {
        error = error || table.addConditionalColumn(join, column);
      }
    }

  }

  private JoinCondition parseJoin(Node joinNode) {
    JoinCondition join = new JoinCondition();
    String table = XMLUtil.getLowerAttrValue(joinNode, Attr_Join_Table);
    join.setJoinTable(table);

    NodeList list = joinNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (Ele_Join_Column.equals(name)) {
        String column = XMLUtil.getLowerAttrValue(node, Attr_Join_Column);
        String target = XMLUtil.getLowerAttrValue(node, Attr_Join_Target);
        join.addJoinColumn(column, target);
      }
    }

    return join;
  }

  private Column parseColumn(Node columnNode) {
    Column column = new Column();
    String columnName = XMLUtil.getLowerAttrValue(columnNode, Attr_Name);
    String joinable = XMLUtil.getAttrValue(columnNode, Attr_Joinable);
    String multiplicity = XMLUtil.getAttrValue(columnNode, Attr_Multiplicity);

    column.setName(columnName);
    if (joinable != null) {
      column.setJoinable(Boolean.valueOf(joinable));
    }
    if (multiplicity != null) {
      column.setMultiplicity(Integer.valueOf(multiplicity));
    }

    BaseType type = parseType(columnNode, columnName);
    column.setType(type);
    return column;

  }

  private BaseType parseType(Node columnNode, String columnName) {
    String dataCategoryId = XMLUtil.getLowerAttrValue(columnNode, Attr_Data_Category);
    if (dataCategoryId != null) {
      return parsePrimitiveType(columnNode, columnName);
    } else {
      return parseComplexType(columnNode, columnName);
    }
  }

  private PrimitiveType parsePrimitiveType(Node columnNode, String columnName) {
    PrimitiveType type = new PrimitiveType();
    String dataCategoryId = XMLUtil.getLowerAttrValue(columnNode, Attr_Data_Category);
    DataCategory dataCategory = policy.getDataCategory(dataCategoryId);
    if (dataCategory == null) {
      logger.error("Cannot locate data category: {} referred in column: {}.", dataCategoryId,
          columnName);
      error = true;
      return type;
    }
    type.setDataCategory(dataCategory);

    NodeList list = columnNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (Ele_Desensitize_Operation.equals(name)) {
        parseDesensitizeOperation(node, type);
      } else if (ComplexTypes.contains(name)) {
        logger.error(
            "Data category attribute and sub {} element cannot appear together in column: {}.",
            name, columnName);
        error = true;
      }
    }
    return type;
  }

  private BaseType parseComplexType(Node columnNode, String columnName) {
    BaseType type = null;
    NodeList list = columnNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (Ele_Struct.equals(name)) {
        type = parseStructType(node, columnName);
      } else if (Ele_Map.equals(name)) {
        type = parseMapType(node, columnName);
      } else if (Ele_Array.equals(name)) {
        type = parseArrayType(node, columnName);
      } else if (Ele_Composite.equals(name)) {
        type = parseCompositeType(node, columnName);
      }
    }
    if (type == null) {
      logger.error(
          "one of data-category attribute or struct/map/array element should exist in column: {} ",
          columnName);
      error = true;
    }
    return type;
  }

  private StructType parseStructType(Node structNode, String columnName) {
    StructType struct = new StructType();
    NodeList list = structNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (Ele_Struct_Field.equals(name)) {
        String fieldName = XMLUtil.getAttrValue(node, Attr_Struct_Field_Name);
        BaseType subType = parseType(node, columnName);
        FieldType field = new FieldType(fieldName, subType);
        struct.addField(field);
      }
    }
    return struct;
  }

  private MapType parseMapType(Node mapNode, String columnName) {
    MapType map = new MapType();
    NodeList list = mapNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (Ele_Map_Entry.equals(name)) {
        String key = XMLUtil.getAttrValue(node, Attr_Map_Entry_Key);
        BaseType valueType = parseType(node, columnName);
        EntryType entry = new EntryType(key, valueType);
        map.addEntry(entry);
      }
    }
    return map;
  }

  private ArrayType parseArrayType(Node arrayNode, String columnName) {
    ArrayType array = new ArrayType();
    NodeList list = arrayNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (Ele_Array_Item.equals(name)) {
        BaseType itemType = parseType(node, columnName);
        array.setItemType(itemType);
      }
    }
    return array;
  }

  private CompositeType parseCompositeType(Node compositeNode, String columnName) {
    CompositeType composite = new CompositeType();
    NodeList list = compositeNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      String name = node.getLocalName();
      if (Ele_Composite_Extract.equals(name)) {
        String extractName = XMLUtil.getAttrValue(node, Attr_Composite_Extract_Name);
        PrimitiveType extractType = parsePrimitiveType(node, columnName);
        ExtractOperation operation = new ExtractOperation(extractName, extractType);
        composite.addExtractOperation(operation);
      }
    }
    return composite;

  }

  private void parseDesensitizeOperation(Node deNode, PrimitiveType type) {
    String opName = XMLUtil.getAttrValue(deNode, Attr_Name);
    DataCategory data = type.getDataCategory();
    DesensitizeOperation op = data.getOperation(opName);
    if (op == null) {
      logger.error("Desensitize operation: {} is not supported by data category: {}", opName,
          data.getId());
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
        type.addDesensitizeOperation(udf, op);
      }
    }
  }

  private void checkTable(Database database, Table table) {
    if (MetaManager.isDefined(database.getName(), table.getName())) {
      logger.error(
          "Table: {} in database: {} has already been defined by other meta files, please fix.",
          table.getName(), database.getName());
      error = true;
    }
  }

  private void checkOperationMapping(String udf, DesensitizeOperation op) {
    DesensitizeOperation op2 = udfs.get(udf);
    if (op2 == null) {
      udfs.put(udf, op);
    } else if (!op.equals(op2)) {
      logger.error("UDF: {} should not be mapped to multiple desensitize operations: {} and {}.",
          udf, op.getName(), op2.getName());
      error = true;
    }
  }

}
