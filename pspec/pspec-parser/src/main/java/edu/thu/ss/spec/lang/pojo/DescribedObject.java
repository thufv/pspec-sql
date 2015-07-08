package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

/**
 * class for described object (with descriptions)
 * @author luochen
 *
 */
public abstract class DescribedObject extends IdentifiedObject {
	protected String shortDescription="";
	protected String longDescription="";

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

	public Element outputType(Document document, String name) {
		Element element = super.outputType(document, name);
		if (this.shortDescription != null) {
			Element shortEle = document.createElement(ParserConstant.Ele_Short_Description);
			shortEle.appendChild(document.createTextNode(shortDescription));
			element.appendChild(shortEle);
		}
		if (this.longDescription != null) {
			Element longEle = document.createElement(ParserConstant.Ele_Long_Description);
			longEle.appendChild(document.createTextNode(longDescription));
			element.appendChild(longEle);
		}

		return element;
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
