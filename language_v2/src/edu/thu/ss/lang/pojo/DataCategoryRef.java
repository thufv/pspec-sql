package edu.thu.ss.lang.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.lang.parser.ParserConstant;
import edu.thu.ss.lang.util.XMLUtil;

public class DataCategoryRef extends CategoryRef<DataCategory> {
	protected Action action = Action.root;

	public void setData(DataCategory data) {
		this.category = data;
	}

	public DataCategory getData() {
		return category;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public void parse(Node refNode) {
		super.parse(refNode);
		String actionValue = XMLUtil.getAttrValue(refNode, ParserConstant.Attr_Policy_Data_Action);
		if (actionValue != null) {
			this.action = Action.actions.get(actionValue);
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
				sb.append(ref.refid);
				sb.append(' ');
			}
		}
		return sb.toString();

	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
