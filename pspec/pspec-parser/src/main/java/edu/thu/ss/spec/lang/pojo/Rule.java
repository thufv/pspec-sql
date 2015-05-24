package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

/**
 * class for rule, directly parsed from xml.
 * @author luochen
 *
 */
public class Rule extends DescribedObject {

	protected List<UserRef> userRefs = new ArrayList<>();
	protected List<DataRef> dataRefs = new ArrayList<>();
	protected DataAssociation association = null;

	protected List<Restriction> restrictions = new ArrayList<>();
	protected Condition condition = null;
	
	public List<UserRef> getUserRefs() {
		return userRefs;
	}

	public void setUserRefs(List<UserRef> userRefs) {
		this.userRefs = userRefs;
	}

	public void setDataRefs(List<DataRef> dataRefs) {
		this.dataRefs = dataRefs;
	}

	public void setAssociation(DataAssociation association) {
		this.association = association;
	}

	public List<DataRef> getDataRefs() {
		if (isSingle()) {
			return dataRefs;
		} else {
			return association.dataRefs;
		}
	}

	public List<DataRef> getRawDataRefs() {
		return dataRefs;
	}

	public boolean isSingle() {
		return association == null;
	}

	public boolean isFilter() {
		return condition != null;
	}
	
	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public List<Restriction> getRestrictions() {
		return restrictions;
	}

	public DataAssociation getAssociation() {
		return association;
	}

	public Restriction getRestriction() {
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
				UserRef obj = new UserRef();
				obj.parse(node);
				userRefs.add(obj);
			} else if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				DataRef obj = new DataRef();
				obj.parse(node);
				dataRefs.add(obj);
			} else if (ParserConstant.Ele_Policy_Rule_DataAsscoation.equals(name)) {
				association = new DataAssociation();
				association.parse(node);
			} else if (ParserConstant.Ele_Policy_Rule_Restriction.equals(name)) {
				Restriction restriction = new Restriction();
				restriction.parse(node);
				this.restrictions.add(restriction);
			} else if (ParserConstant.Ele_Policy_Rule_Filter.equals(name)) {
				Condition condition = new Condition();
				condition.parse(node);
				this.condition = condition;
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule: ");
		sb.append(id);
		sb.append("\n");

		for (UserRef user : userRefs) {
			sb.append('\t');
			sb.append(user);
			sb.append("\n");
		}

		for (DataRef data : dataRefs) {
			sb.append('\t');
			sb.append(data);
			sb.append("\n");
		}
		if (association != null) {
			sb.append('\t');
			sb.append(association);
			sb.append("\n");
		}

		for (Restriction res : restrictions) {
			sb.append('\t');
			sb.append(res);
		}
		
		if (isFilter()) {
			sb.append(condition);
		}
		return sb.toString();
	}

	public Integer getRestrictionIndex(Restriction res) {
		return restrictions.indexOf(res);
	}

}
