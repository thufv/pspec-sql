package edu.thu.ss.spec.meta.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.parser.WritingException;
import edu.thu.ss.spec.util.XMLUtil;

public class XMLMetaRegistryWriter implements MetaParserConstant {
	
	public void output(XMLMetaRegistry registry, String path) throws WritingException {
		Document document = null;
		try {
			document = XMLUtil.newDocument();
		} catch (Exception e) {
			throw new WritingException("Fail to create XML document.", e);
		}
		
		Element root = document.createElement(Ele_Root);
		document.appendChild(root);
		
		root.setAttribute(ParserConstant.Attr_XMLNs, ParserConstant.XMLNs);
		root.setAttribute(MetaParserConstant.Attr_Policy, registry.getPolicy().getPath().toString());
		
		for (String databaseName : registry.getDatabases().keySet()) {
			Element database = registry.getDatabases().get(databaseName).outputElement(document);
			if (database != null) {
				root.appendChild(database);
			}
		}
		
		try {
			XMLUtil.writeDocument(document, path);
		} catch (Exception e) {
			throw new WritingException("Fail to output policy to " + path, e);
		}
	}
}
