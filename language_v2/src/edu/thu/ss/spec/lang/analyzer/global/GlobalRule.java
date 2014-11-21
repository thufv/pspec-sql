package edu.thu.ss.spec.lang.analyzer.global;

import java.util.HashSet;
import java.util.List;

import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.SetUtil;

public class GlobalRule extends ExpandedRule {

	protected List<UserRef> userRefs;

	//if data ref is single, then it is merged
	protected DataRef dataRef;

	//used for association
	protected DataAssociation association;

	private GlobalRule(Rule rule, int num) {
		this.ruleId = rule.getId();
		this.num = num;
		this.userRefs = rule.getUserRefs();
		this.restrictions = rule.getRestrictions().toArray(new Restriction[rule.getRestrictions().size()]);

		this.users = new HashSet<>();
		for (UserRef ref : userRefs) {
			this.users.addAll(ref.getMaterialized());
		}
	}

	public GlobalRule(Rule rule, DataAssociation association, int num) {
		this(rule, num);
		this.association = association;
	}

	public GlobalRule(Rule rule, DataRef ref, int num) {
		this(rule, num);
		this.dataRef = ref;

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

	@Override
	public boolean isAssociation() {
		return association != null;
	}

	@Override
	public boolean isSingle() {
		return association == null;
	}

	public boolean isGlobal() {
		return dataRef != null;
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
