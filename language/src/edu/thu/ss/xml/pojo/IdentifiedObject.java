package edu.thu.ss.xml.pojo;

import org.w3c.dom.Node;

import edu.thu.ss.xml.parser.ParserConstant;
import edu.thu.ss.xml.parser.XMLUtil;

public class IdentifiedObject {

	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void parse(Node node) {

		this.id = XMLUtil.getAttrValue(node, ParserConstant.Attr_Id);
	}

}
