package edu.thu.ss.xml.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class Restriction extends DataCategoryGroup {
	public enum RestrictType {
		desensitize, aggregate
	}

	protected RestrictType type;

	public void parse(Node resNode) {
		NodeList list = resNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Desensitize.equals(name)) {
				this.type = RestrictType.desensitize;
				super.parse(node);
			} else if (ParserConstant.Ele_Policy_Rule_Desensitize.equals(name)) {
				this.type = RestrictType.aggregate;
				super.parse(node);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(type);
		sb.append(": ");
		sb.append(super.toString());
		return sb.toString();
	}
}
