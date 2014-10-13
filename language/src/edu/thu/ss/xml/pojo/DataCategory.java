package edu.thu.ss.xml.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class DataCategory extends HierarchicalObject {

	protected String udf;
	protected String clazz;

	@Override
	public void parse(Node dataNode) {

		NodeList list = dataNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Vocabulary_Desensitize_Op.equals(name)) {
				parseDesensitizeOp(node);
			}
		}

	}

	private void parseDesensitizeOp(Node opNode) {
		NodeList list = opNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Vocabulary_Desensitize_UDF.equals(name)) {
				this.udf = node.getTextContent();
			} else if (ParserConstant.Ele_Vocabulary_Desensitize_Class
					.equals(name)) {
				this.clazz = node.getTextContent();
			}

		}

	}
}
