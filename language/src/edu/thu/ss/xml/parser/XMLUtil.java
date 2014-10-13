package edu.thu.ss.xml.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XMLUtil {

	private static Map<String, Schema> schemas = new HashMap<>();

	public static Document parse(String path, String xsdPath) throws Exception {

		Validator validator = getValidator(xsdPath);

		File docFile = new File(path);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(docFile);

		validator.validate(new DOMSource(doc));
		return doc;
	}

	public static Validator getValidator(String xsdPath) throws Exception {
		Schema schema = schemas.get(xsdPath);
		if (schema != null) {
			return schema.newValidator();
		}
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		schema = schemaFactory.newSchema(new File(xsdPath));
		schemas.put(xsdPath, schema);
		return schema.newValidator();
	}

	public static String getAttrValue(Node node, String attr) {
		NamedNodeMap attrs = node.getAttributes();
		Node baseAttr = attrs.getNamedItem(attr);
		if (baseAttr == null) {
			return null;
		}
		return baseAttr.getNodeValue();
	}

}
