package edu.thu.ss.xml.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class DataCategory extends HierarchicalObject {

	protected DesensitizeOperation op;

	@Override
	public void parse(Node dataNode) {
		super.parse(dataNode);

		NodeList list = dataNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Vocabulary_Desensitize_Op.equals(name)) {
				op = new DesensitizeOperation();
				op.parse(node);
			}
		}
	}

}
