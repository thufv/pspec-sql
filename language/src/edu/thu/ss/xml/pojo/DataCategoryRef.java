package edu.thu.ss.xml.pojo;

import org.w3c.dom.Node;

import edu.thu.ss.xml.parser.ParserConstant;
import edu.thu.ss.xml.parser.XMLUtil;

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
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Data Category: ");
		sb.append(refid);
		sb.append('(');
		sb.append(action);
		sb.append(')');

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
