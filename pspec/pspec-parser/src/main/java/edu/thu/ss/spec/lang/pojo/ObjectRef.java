package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

/**
 * class for object ref
 * @author luochen
 *
 */
public class ObjectRef implements Parsable, Writable {
	protected String refid = "";

	protected boolean resolved = false;

	public ObjectRef() {

	}

	public ObjectRef(String refid) {
		this.refid = refid;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	@Override
	public void parse(Node node) {
		this.refid = XMLUtil.getAttrValue(node, ParserConstant.Ele_Policy_Refid);
	}

	public String getRefid() {
		return refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}

	@Override
	public Element outputType(Document document, String name) {
		Element element = document.createElement(name);
		element.setAttribute(ParserConstant.Attr_Refid, refid);
		return element;
	}

	@Override
	public Element outputElement(Document document) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return refid;
	}

}
