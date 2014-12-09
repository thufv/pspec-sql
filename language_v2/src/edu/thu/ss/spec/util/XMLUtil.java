package edu.thu.ss.spec.util;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
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

/**
 * a utility class for xml related functionalities
 * @author luochen
 *
 */
public class XMLUtil {

	private static Map<String, Schema> schemas = new HashMap<>();

	public static Document parseDocument(URI uri, String xsdPath) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = null;

		InputStream is = XMLUtil.class.getClassLoader().getResourceAsStream(uri.getPath());
		if (is != null) {
			//load from class path
			doc = builder.parse(is);
		} else {
			//load from file
			File docFile = new File(uri.getPath());
			if (docFile.exists()) {
				doc = builder.parse(docFile);
			} else {
				//load from absolute uri
				doc = builder.parse(uri.toString());
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

	public static String getLowerAttrValue(Node node, String attr) {
		String value = getAttrValue(node, attr);
		if (value != null) {
			return value.toLowerCase();
		} else {
			return null;
		}
	}

	public static URI toUri(String path) throws URISyntaxException {
		File file = new File(path);
		if (file.exists()) {
			path = file.getAbsolutePath();
		}
		return new URI(path).normalize();
	}
}
