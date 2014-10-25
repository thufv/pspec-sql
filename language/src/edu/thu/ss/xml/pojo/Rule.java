package edu.thu.ss.xml.pojo;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class Rule extends DescribedObject {

	public enum Ruling {
		allow, deny, restrict
	};

	protected Ruling ruling;

	protected Set<UserCategoryRef> userRefs = new HashSet<>();
	protected Set<DataCategoryRef> dataRefs = new HashSet<>();

	protected Set<DataAssociation> associations = new HashSet<>();

	protected Restriction restriction;

	public Set<UserCategoryRef> getUserRefs() {
		return userRefs;
	}

	public Set<DataCategoryRef> getDataRefs() {
		return dataRefs;
	}

	public Set<DataAssociation> getAssociations() {
		return associations;
	}

	public Restriction getRestriction() {
		return restriction;
	}

	public Ruling getRuling() {
		return ruling;
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
				UserCategoryRef obj = new UserCategoryRef();
				obj.parse(node);
				userRefs.add(obj);
			} else if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				DataCategoryRef obj = new DataCategoryRef();
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule: ");
		sb.append(id);
		sb.append("\n");

		sb.append("\tRuling: ");
		sb.append(ruling);
		sb.append("\n");

		for (UserCategoryRef user : userRefs) {
			sb.append('\t');
			sb.append(user);
			sb.append("\n");
		}

		for (DataCategoryRef data : dataRefs) {
			sb.append('\t');
			sb.append(data);
			sb.append("\n");
		}

		for (DataAssociation obj : associations) {
			sb.append('\t');
			sb.append(obj);
			sb.append("\n");
		}

		if (restriction != null) {
			sb.append('\t');
			sb.append(restriction);
		}
		return sb.toString();
	}

}
