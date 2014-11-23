package edu.thu.ss.spec.lang.pojo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.thu.ss.spec.util.SetUtil;

public class ExpandedRule implements Comparable<ExpandedRule> {

	protected String ruleId;

	protected Restriction[] restrictions;

	protected Set<UserCategory> users;

	protected int num;

	protected List<UserRef> userRefs;

	//if data ref is single, then it is merged
	protected DataRef dataRef;

	//used for association
	protected DataAssociation association;

	private ExpandedRule(Rule rule, int num) {
		this.ruleId = rule.getId();
		this.num = num;
		this.userRefs = rule.getUserRefs();
		this.users = new HashSet<>();
		for (UserRef ref : userRefs) {
			this.users.addAll(ref.getMaterialized());
		}
	}

	public ExpandedRule(Rule rule, DataAssociation association, int num) {
		this(rule, num);
		this.association = association;
		this.restrictions = rule.restrictions.toArray(new Restriction[0]);
	}

	public ExpandedRule(Rule rule, DataRef ref, int num) {
		this(rule, num);
		this.dataRef = ref;
		this.restrictions = new Restriction[1];
		this.restrictions[0] = rule.getRestriction().clone();
		if (!this.restrictions[0].isForbid()) {
			Desensitization de = this.restrictions[0].getDesensitization();
			de.getDataRefs().add(ref);
			de.materialize(ref.getMaterialized());
		}
	}

	@Override
	public int compareTo(ExpandedRule o) {
		return Integer.compare(this.getDimension(), o.getDimension());
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

	public List<UserRef> getUserRefs() {
		return userRefs;
	}

	public DataAssociation getAssociation() {
		return association;
	}

	public boolean contains(UserCategory user) {
		for (UserRef ref : userRefs) {
			if (ref.contains(user)) {
				return true;
			}
		}
		return false;
	}

	public DataRef getDataRef() {
		return dataRef;
	}

	public boolean isAssociation() {
		return association != null;
	}

	public boolean isSingle() {
		return association == null;
	}

	public int getDimension() {
		if (dataRef != null) {
			return 1;
		} else {
			return association.getDimension();
		}
	}

	public boolean isGlobal() {
		if (dataRef == null) {
			throw new UnsupportedOperationException("isGlobal() is only supported by single rule.");
		}
		return dataRef.isGlobal();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule: ");
		sb.append(getRuleId());

		sb.append("\n\t");
		sb.append("Users: ");
		sb.append(SetUtil.format(users, ","));
		sb.append("\n\t");

		if (dataRef != null) {
			sb.append("Datas: ");
			if (dataRef.isGlobal()) {
				sb.append("global\t");
				sb.append(dataRef.getCategory().getId());
			} else {
				sb.append("local\t");
				sb.append(SetUtil.format(dataRef.getMaterialized(), ","));
			}

		} else {
			sb.append(association);
		}
		sb.append("\n\t");
		for (Restriction restriction : restrictions) {
			sb.append(restriction);
			sb.append("\n\t");
		}
		return sb.toString();

	}
}
