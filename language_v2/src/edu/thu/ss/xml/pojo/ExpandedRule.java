package edu.thu.ss.xml.pojo;

import java.util.List;

public class ExpandedRule {

	protected String ruleId;

	protected UserCategoryRef user;

	// only one of the two can be set.
	protected DataCategoryRef data;
	protected DataAssociation association;

	protected List<Restriction> restrictions;

	public ExpandedRule(Rule rule) {
		this.ruleId = rule.getId();
		this.restrictions = rule.getRestrictions();

	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public UserCategoryRef getUser() {
		return user;
	}

	public void setUser(UserCategoryRef user) {
		this.user = user;
	}

	public DataCategoryRef getData() {
		return data;
	}

	public void setData(DataCategoryRef data) {
		this.data = data;
	}

	public DataAssociation getAssociation() {
		return association;
	}

	public void setAssociation(DataAssociation association) {
		this.association = association;
	}

	public List<Restriction> getRestriction() {
		return restrictions;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule: ");
		sb.append(ruleId);

		sb.append("\n\t");
		sb.append(user);
		sb.append("\n\t");
		if (data != null) {
			sb.append(data);
		}
		if (association != null) {
			sb.append(association);
		}
		for (Restriction restriction : restrictions) {
			sb.append("\n\t");
			sb.append(restriction);
		}
		return sb.toString();

	}
}
