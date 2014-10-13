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

	protected Set<ReferringObject> userRefs;
	protected Set<ReferringObject> dataRefs;
	protected Set<Action> actions;

	protected List<UserCategory> users;
	protected List<DataCategory> datas;

	protected List<DataAssociation> associations;

	protected Restriction restriction;

	public Rule() {
		userRefs = new HashSet<>();
		dataRefs = new HashSet<>();
		actions = new HashSet<>();

		users = new ArrayList<>();
		datas = new ArrayList<>();

		associations = new ArrayList<>();

	}

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
}
