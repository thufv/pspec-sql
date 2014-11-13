package edu.thu.ss.lang.util;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
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

	public static Document parseDocument(String path, String xsdPath) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = null;
		//	URI uri = validateURI(path);

		InputStream is = XMLUtil.class.getClassLoader().getResourceAsStream(path);
		if (is != null) {
			//load from class path
			doc = builder.parse(path);
		} else {
			File docFile = new File(path);
			if (docFile.exists()) {
				doc = builder.parse(docFile);
			} else {
				doc = builder.parse(path);
			}
		}
		Validator validator = getValidator(xsdPath);
		if (validator != null) {
			validator.validate(new DOMSource(doc));
		}
		return doc;
	}

	public static Validator getValidator(String xsdPath) throws Exception {
		if (xsdPath == null) {
			return null;
		}
		Schema schema = schemas.get(xsdPath);
		if (schema != null) {
			return schema.newValidator();
		}
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		schema = schemaFactory.newSchema(new File(xsdPath));
		schemas.put(xsdPath, schema);
		return schema.newValidator();
	}

	public static URI validateURI(String path) {
		try {
			URI uri = URI.create(path);
			return uri;
		} catch (Exception e) {
			return null;
		}
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
