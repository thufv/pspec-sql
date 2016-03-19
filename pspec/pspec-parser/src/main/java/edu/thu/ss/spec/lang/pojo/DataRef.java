package edu.thu.ss.spec.lang.pojo;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

/**
 * class for data ref
 * @author luochen
 *
 */
public class DataRef extends CategoryRef<DataCategory> {
	/**
	 * all(default), output and condition
	 */
	protected Action action = Action.All;

	public DataRef() {
	}

	public boolean subsumes(DataRef another) {
		return this.action.ancestorOf(another.action) && super.subsumes(another);

	}

	public int daNum() {
		int actionNum = action == Action.All ? 2 : 1;
		return actionNum * materialized.size();
	}

	public void setData(DataCategory data) {
		this.setCategory(data);
	}

	public DataCategory getData() {
		return category;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public void parse(Node refNode) {
		super.parse(refNode);

		String actionValue = XMLUtil.getAttrValue(refNode, ParserConstant.Attr_Policy_Action);
		if (!actionValue.isEmpty()) {
			this.action = Action.get(actionValue);
		}
	}

	@Override
	protected void parseExclude(Node excludeNode) {
		NodeList list = excludeNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				ObjectRef ref = new ObjectRef();
				ref.parse(node);
				excludeRefs.add(ref);
			}
		}
	}

	@Override
	public Element outputElement(Document document) {
		Element element = super.outputType(document, ParserConstant.Ele_Policy_Rule_DataRef);

		if (this.action != null) {
			element.setAttribute(ParserConstant.Attr_Policy_Action, this.action.id);
		}

		if (this.excludes.size() > 0) {
			Element excludeEle = document.createElement(ParserConstant.Ele_Policy_Rule_Exclude);
			element.appendChild(excludeEle);

			for (DataCategory data : excludes) {
				Element dataEle = document.createElement(ParserConstant.Ele_Policy_Rule_DataRef);
				dataEle.setAttribute(ParserConstant.Attr_Refid, data.id);
				excludeEle.appendChild(dataEle);
			}
		}
		return element;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Data Category: ");
		sb.append(refid);
		sb.append('(');
		sb.append(action);
		sb.append(')');
		if (excludeRefs.size() > 0) {
			sb.append("\tExclude:");
			for (ObjectRef ref : excludeRefs) {
				sb.append(ref.getRefid());
				sb.append(' ');
			}
		}
		return sb.toString();

	}

	@Override
	public DataRef clone() {
		DataRef ref = new DataRef();
		ref.refid = refid;
		ref.resolved = resolved;
		ref.category = category;
		ref.error = error;
		ref.action = action;
		for (ObjectRef exclude : excludeRefs) {
			ref.excludeRefs.add(exclude.clone());
		}
		ref.excludes.addAll(this.excludes);
		if (this.materialized != null) {
			ref.materialized = new HashSet<>(this.materialized);
		}

		return ref;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataRef other = (DataRef) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		return true;
	}
}
