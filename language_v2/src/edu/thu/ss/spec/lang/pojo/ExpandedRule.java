package edu.thu.ss.spec.lang.pojo;

import java.util.Set;

public abstract class ExpandedRule {

	protected String ruleId;

	protected int num;

	protected Restriction[] restrictions;

	public ExpandedRule() {

	}

	protected Set<UserCategory> users;

	protected ExpandedRule(Rule rule, Set<UserCategory> users, int num) {
		this.ruleId = rule.getId();
		this.num = num;
		this.users = users;
	}

	public Set<UserCategory> getUsers() {
		return users;
	}

	public String getRuleId() {
		return ruleId + "#" + num;
	}

	public String getRawRuleId() {
		return ruleId;
	}

	public Restriction[] getRestrictions() {
		return restrictions;
	}

	public Restriction getRestriction() {
		return restrictions[0];
	}

	public abstract boolean isSingle();

	public abstract boolean isAssociation();
}
