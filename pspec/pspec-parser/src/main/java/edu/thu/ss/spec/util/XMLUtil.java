package edu.thu.ss.spec.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import edu.thu.ss.spec.lang.parser.InvalidDocumentException;

/**
 * a utility class for xml related functionalities
 * @author luochen
 *
 */
public class XMLUtil {

	private static Map<String, Schema> schemas = new HashMap<>();

	public static Document newDocument() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.newDocument();
	}

	public static void writeDocument(Document document, String path) throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		DOMSource source = new DOMSource(document);
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		File file = new File(path);
		if (!file.exists()) {
			File parent = file.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
		}

		PrintWriter writer = new PrintWriter(new FileOutputStream(path));
		StreamResult result = new StreamResult(writer);
		transformer.transform(source, result);
	}

	public static Document parseDocument(URI uri, String xsdPath) throws InvalidDocumentException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = null;

			//load from file
			File docFile = new File(uri.getPath());
			if (docFile.exists()) {
				doc = builder.parse(docFile);
			} else {
				InputStream is = XMLUtil.class.getClassLoader().getResourceAsStream(uri.getPath());
				if (is != null) {
					//load from class path
					doc = builder.parse(is);
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
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new InvalidDocumentException(uri, e);
		}

	}

	public static Validator getValidator(String xsdPath) {
		Schema schema = schemas.get(xsdPath);
		if (schema != null) {
			return schema.newValidator();
		}
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		File schemaFile = new File(xsdPath);
		try {
			if (schemaFile.exists()) {
				schema = schemaFactory.newSchema(new File(xsdPath));
			} else {
				URL url = XMLUtil.class.getClassLoader().getResource(xsdPath);
				if (url != null) {
					schema = schemaFactory.newSchema(url);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (schema == null) {
			//fail to load schema xsd
			throw new RuntimeException("Fail to load xsd from path:" + xsdPath);
		}
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
			return "";
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

	public static URI toUri(String path) {
		if (path == null || path.isEmpty()) {
			return null;
		}

		File file = new File(path);
		if (file.exists()) {
			path = file.getAbsolutePath();
		}
		URI uri;
		try {
			uri = new URI(path.replace('\\', '/'));
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		return uri.normalize();
	}
}
