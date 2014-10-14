package edu.thu.ss.xml.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class Rule extends DescribedObject {

	public enum Ruling {
		allow, deny, restrict
	};

	protected Ruling ruling;

	protected Set<ReferringObject> userRefs = new HashSet<>();
	protected Set<ReferringObject> dataRefs = new HashSet<>();
	protected Set<Action> actions = new HashSet<>();

	protected List<UserCategory> users = new ArrayList<>();
	protected List<DataCategory> datas = new ArrayList<>();

	protected List<DataAssociation> associations = new ArrayList<>();

	protected Restriction restriction;

	@Override
	public void parse(Node ruleNode) {
		super.parse(ruleNode);

		String name = ruleNode.getLocalName();
		this.ruling = Ruling.valueOf(name);

		NodeList list = ruleNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_UserRef.equals(name)) {
				ReferringObject obj = new ReferringObject();
				obj.parse(node);
				userRefs.add(obj);
			} else if (ParserConstant.Ele_Policy_Rule_Action.equals(name)) {
				parseActions(node);
			} else if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				ReferringObject obj = new ReferringObject();
				obj.parse(node);
				dataRefs.add(obj);
			} else if (ParserConstant.Ele_Policy_Rule_DataAsscoation.equals(name)) {
				DataAssociation association = new DataAssociation();
				association.parse(node);
				associations.add(association);
			} else if (ParserConstant.Ele_Policy_Rule_Restriction.equals(name)) {
				this.restriction = new Restriction();
				restriction.parse(node);
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

	public Set<ReferringObject> getUserRefs() {
		return userRefs;
	}

	public Set<ReferringObject> getDataRefs() {
		return dataRefs;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public List<UserCategory> getUsers() {
		return users;
	}

	public List<DataCategory> getDatas() {
		return datas;
	}

	public List<DataAssociation> getAssociations() {
		return associations;
	}

	public Restriction getRestriction() {
		return restriction;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule: ");
		sb.append(id);
		sb.append("\n");

		sb.append("\tRuling: ");
		sb.append(ruling);
		sb.append("\n");

		sb.append("\tUser Categories: ");
		for (UserCategory user : users) {
			sb.append(user.id);
			sb.append(" ");
		}
		sb.append("\n");

		sb.append("\tActions: ");
		for (Action action : actions) {
			sb.append(action);
			sb.append(" ");
		}
		sb.append("\n");

		if (dataRefs.size() > 0) {
			sb.append("\tData Categories: ");
			for (DataCategory data : datas) {
				sb.append(data.id);
				sb.append(" ");
			}
			sb.append("\n");
		}
		if (associations.size() > 0) {
			sb.append("\tData Associations: ");
			for (DataAssociation obj : associations) {
				sb.append(obj);
				sb.append(" ");
			}
			sb.append("\n");
		}

		if (restriction != null) {
			sb.append('\t');
			sb.append(restriction);
		}
		return sb.toString();
	}

}
