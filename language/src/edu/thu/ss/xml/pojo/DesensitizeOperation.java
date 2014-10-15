package edu.thu.ss.xml.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class DesensitizeOperation {
	protected String clazz;
	protected String udf;

	public void parse(Node opNode) {
		NodeList list = opNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Vocabulary_Desensitize_UDF.equals(name)) {
				this.udf = node.getTextContent();
			} else if (ParserConstant.Ele_Vocabulary_Desensitize_Class.equals(name)) {
				this.clazz = node.getTextContent();
			}

		}
	}
}
