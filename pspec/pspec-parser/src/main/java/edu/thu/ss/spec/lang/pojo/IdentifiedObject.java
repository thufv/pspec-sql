package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

/**
 * class for identified object
 * @author luochen
 *
 */
public abstract class IdentifiedObject implements Parsable, Writable {

	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void parse(Node node) {
		this.id = XMLUtil.getAttrValue(node, ParserConstant.Attr_Id);
	}

	public Element outputType(Document document, String name) {
		Element element = document.createElement(name);
		element.setAttribute(ParserConstant.Attr_Id, id);
		return element;
	}

	@Override
	public Element outputElement(Document document) {
		throw new UnsupportedOperationException();
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
		return id;
	}

}