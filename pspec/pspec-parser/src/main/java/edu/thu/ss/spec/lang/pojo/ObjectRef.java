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

	protected boolean error = false;

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

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
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

	public ObjectRef clone() {
		ObjectRef ref = new ObjectRef();
		ref.refid = this.refid;
		ref.resolved = this.resolved;
		ref.error = this.error;
		return ref;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((refid == null) ? 0 : refid.hashCode());
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
		ObjectRef other = (ObjectRef) obj;
		if (refid == null) {
			if (other.refid != null)
				return false;
		} else if (!refid.equals(other.refid))
			return false;
		return true;
	}

}
