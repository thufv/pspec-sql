package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

public class DescribedObject extends IdentifiedObject {
	protected String shortDescription;
	protected String longDescription;

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	@Override
	public void parse(Node objNode) {
		super.parse(objNode);

		NodeList list = objNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Short_Description.equals(name)) {
				this.shortDescription = node.getTextContent();
			} else if (ParserConstant.Ele_Long_Description.equals(name)) {
				this.longDescription = node.getTextContent();
			}

		}
	}

}
