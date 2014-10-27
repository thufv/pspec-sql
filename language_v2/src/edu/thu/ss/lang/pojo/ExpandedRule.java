package edu.thu.ss.lang.pojo;

import java.util.List;
import java.util.Set;

public class ExpandedRule {

	protected String ruleId;

	protected Set<UserCategory> users;

	protected Set<DataCategory> datas;
	protected Action action;

	protected DataAssociation association;

	protected List<Restriction> restrictions;

	private ExpandedRule(Rule rule, Set<UserCategory> users) {
		this.ruleId = rule.getId();
		this.restrictions = rule.getRestrictions();
		this.users = users;
	}

	public ExpandedRule(Rule rule, Set<UserCategory> users, Action action, Set<DataCategory> datas) {
		this(rule, users);
		this.action = action;
		this.datas = datas;
	}

	public ExpandedRule(Rule rule, Set<UserCategory> users, DataAssociation association) {
		this(rule, users);
		this.association = association;
	}

	public boolean isSingle() {
		return datas != null;
	}

	public boolean isAssociation() {
		return association != null;
	}

	public String getRuleId() {
		return ruleId;
	}

	public DataAssociation getAssociation() {
		return association;
	}

	public Set<UserCategory> getUsers() {
		return users;
	}

	public Set<DataCategory> getDatas() {
		return datas;
	}

	public List<Restriction> getRestrictions() {
		return restrictions;
	}

	public List<Restriction> getRestriction() {
		return restrictions;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule: ");
		sb.append(ruleId);

		sb.append("\n\t");
		sb.append("Users: ");
		for (UserCategory user : users) {
			sb.append(user.getId());
			sb.append(' ');
		}
		sb.append("\n\t");
		if (datas != null) {
			sb.append("Action: ");
			sb.append(action.getId());
			sb.append("\n\t");
			sb.append("Datas: ");
			for (DataCategory data : datas) {
				sb.append(data.getId());
				sb.append(' ');
			}
			sb.append("\n\t");
		} else {
			sb.append(association);
		}
		for (Restriction restriction : restrictions) {
			sb.append(restriction);
			sb.append("\n\t");
		}
		return sb.toString();

	}
}
