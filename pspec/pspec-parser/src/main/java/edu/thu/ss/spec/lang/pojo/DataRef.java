package edu.thu.ss.spec.lang.pojo;

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
	 * all(default), projection and condition
	 */
	protected Action action = Action.All;

	protected boolean global = false;

	public DataRef() {
	}

	public DataRef(Action action, Set<DataCategory> datas) {
		this.action = action;
		this.materialized = datas;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public boolean isGlobal() {
		return global;
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

	/**
	 * for global {@link DataRef}, {@link #contains(DataCategory)} must be checked on data category hierarchy
	 */
	@Override
	public boolean contains(DataCategory t) {
		if (!global) {
			return super.contains(t);
		} else {
			if (!category.ancestorOf(t)) {
				return false;
			}
			for (DataCategory exclude : excludes) {
				if (exclude.ancestorOf(t)) {
					return false;
				}
			}
			return true;
		}
	}

	@Override
	public void parse(Node refNode) {
		super.parse(refNode);
		String globalValue = XMLUtil.getAttrValue(refNode, ParserConstant.Attr_Policy_Global);
		if (globalValue != null) {
			global = Boolean.valueOf(globalValue);
		}
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

		if (this.global) {
			element.setAttribute(ParserConstant.Attr_Policy_Global, String.valueOf(this.global));
		}
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
		sb.append(isGlobal() ? "global " : "local ");
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
		return ref;
	}

}
