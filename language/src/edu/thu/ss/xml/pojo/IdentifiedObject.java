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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentifiedObject other = (IdentifiedObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: ");
		sb.append(id);
		return sb.toString();
	}

}
