package edu.thu.ss.xml.pojo;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataCategoryRef extends ObjectRef {
	protected DataCategory data;
	protected Set<Action> actions = new HashSet<>();

	public void setData(DataCategory data) {
		this.data = data;
	}

	public DataCategory getData() {
		return data;
	}

	public Set<Action> getActions() {
		return actions;
	}

	@Override
	public void parse(Node refNode) {
		super.parse(refNode);

		NodeList list = refNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			Action action = Action.actions.get(name);
			if (action != null) {
				actions.add(action);
			}
		}
	}

	public void parseActions(Node actionNode) {
		NodeList list = actionNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			Action action = Action.actions.get(name);
			if (action != null) {
				actions.add(action);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(refid);
		if (actions.size() > 0) {
			sb.append('(');
			int count = 0;
			for (Action action : actions) {
				sb.append(action);
				if (count++ < actions.size() - 1) {
					sb.append(", ");
				}
			}
			sb.append(')');
		}

		return sb.toString();

	}

}
