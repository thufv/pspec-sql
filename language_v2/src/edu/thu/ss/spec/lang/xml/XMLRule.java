package edu.thu.ss.spec.lang.xml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

public class XMLRule extends XMLDescribedObject {

	protected Set<XMLUserCategoryRef> userRefs = new HashSet<>();
	protected Set<XMLDataCategoryRef> dataRefs = new HashSet<>();

	protected Set<XMLDataAssociation> associations = new HashSet<>();

	protected List<XMLRestriction> restrictions = new ArrayList<>();

	public Set<XMLUserCategoryRef> getUserRefs() {
		return userRefs;
	}

	public Set<XMLDataCategoryRef> getDataRefs() {
		return dataRefs;
	}

	public Set<XMLDataAssociation> getAssociations() {
		return associations;
	}

	public List<XMLRestriction> getRestrictions() {
		return restrictions;
	}

	public XMLRestriction getRestriction() {
		return restrictions.get(0);
	}

	@Override
	public void parse(Node ruleNode) {
		super.parse(ruleNode);

		String name = ruleNode.getLocalName();

		NodeList list = ruleNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_UserRef.equals(name)) {
				XMLUserCategoryRef obj = new XMLUserCategoryRef();
				obj.parse(node);
				userRefs.add(obj);
			} else if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				XMLDataCategoryRef obj = new XMLDataCategoryRef();
				obj.parse(node);
				dataRefs.add(obj);
			} else if (ParserConstant.Ele_Policy_Rule_DataAsscoation.equals(name)) {
				XMLDataAssociation association = new XMLDataAssociation();
				association.parse(node);
				associations.add(association);
			} else if (ParserConstant.Ele_Policy_Rule_Restriction.equals(name)) {
				XMLRestriction restriction = new XMLRestriction();
				restriction.parse(node);
				this.restrictions.add(restriction);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule: ");
		sb.append(id);
		sb.append("\n");

		for (XMLUserCategoryRef user : userRefs) {
			sb.append('\t');
			sb.append(user);
			sb.append("\n");
		}

		for (XMLDataCategoryRef data : dataRefs) {
			sb.append('\t');
			sb.append(data);
			sb.append("\n");
		}

		for (XMLDataAssociation obj : associations) {
			sb.append('\t');
			sb.append(obj);
			sb.append("\n");
		}

		for (XMLRestriction res : restrictions) {
			sb.append('\t');
			sb.append(res);
		}
		return sb.toString();
	}

}
