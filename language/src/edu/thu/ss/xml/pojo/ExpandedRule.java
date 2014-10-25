package edu.thu.ss.xml.pojo;

import edu.thu.ss.xml.pojo.Rule.Ruling;

public class ExpandedRule {

	protected Ruling ruling;
	protected String ruleId;

	protected UserCategoryRef user;

	// only one of the two can be set.
	protected DataCategoryRef data;
	protected DataAssociation association;

	protected Restriction restriction;

	protected int precedence;

	public ExpandedRule(Rule rule) {
		this.ruleId = rule.getId();
		this.ruling = rule.getRuling();
		this.restriction = rule.getRestriction();

	}

	public Ruling getRuling() {
		return ruling;
	}

	public void setRuling(Ruling ruling) {
		this.ruling = ruling;
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

	public Restriction getRestriction() {
		return restriction;
	}

	public void setRestriction(Restriction restriction) {
		this.restriction = restriction;
	}

	public int getPrecedence() {
		return precedence;
	}

	public void setPrecedence(int precedence) {
		this.precedence = precedence;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule: ");
		sb.append(ruleId);
		sb.append("\n");

		sb.append("\tRuling: ");
		sb.append(ruling);
		sb.append("\n\t");
		sb.append("Precedence: ");
		sb.append(precedence);
		sb.append("\n\t");
		sb.append(user);
		sb.append("\n\t");
		if (data != null) {
			sb.append(data);
		}
		if (association != null) {
			sb.append(association);
		}
		if (restriction != null) {
			sb.append("\n\t");
			sb.append(restriction);
		}
		return sb.toString();

	}
}
