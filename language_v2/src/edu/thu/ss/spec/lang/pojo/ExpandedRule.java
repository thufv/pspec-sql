package edu.thu.ss.spec.lang.pojo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import edu.thu.ss.spec.lang.xml.XMLDataAssociation;
import edu.thu.ss.spec.lang.xml.XMLDataCategoryRef;
import edu.thu.ss.spec.lang.xml.XMLRule;

public class ExpandedRule {

	protected String ruleId;

	protected Set<UserCategory> users;

	protected DataActionPair[] datas;

	protected Restriction[] restrictions;

	private ExpandedRule(XMLRule rule, Set<UserCategory> users) {
		this.ruleId = rule.getId();
		this.users = users;
	}

	public ExpandedRule(XMLRule rule, Set<UserCategory> users, Action action, Set<DataCategory> datas) {
		this(rule, users);

		this.datas = new DataActionPair[1];
		this.datas[0] = new DataActionPair(datas, action, Collections.min(datas).getLabel());
		this.restrictions = new Restriction[1];
		this.restrictions[0] = rule.getRestriction().toRestriction(this.datas);
		if (!this.restrictions[0].isForbid()) {
			Desensitization de = this.restrictions[0].getDesensitization();
			de.setDatas(datas);
			de.setDataIndex(new int[] { 0 });
		}

	}

	public ExpandedRule(XMLRule rule, Set<UserCategory> users, XMLDataAssociation association) {
		this(rule, users);
		this.datas = new DataActionPair[association.getDataRefs().size()];
		int i = 0;
		for (XMLDataCategoryRef ref : association.getDataRefs()) {
			this.datas[i] = new DataActionPair(ref.getMaterialized(), ref.getAction(), ref.getLabel());
			i++;
		}
		Arrays.sort(this.datas);

		this.restrictions = new Restriction[rule.getRestrictions().size()];
		for (i = 0; i < restrictions.length; i++) {
			this.restrictions[i] = rule.getRestrictions().get(i).toRestriction(this.datas);
		}
	}

	public boolean isSingle() {
		return datas.length == 1;
	}

	public boolean isAssociation() {
		return datas.length > 1;
	}

	public String getRuleId() {
		return ruleId;
	}

	public Set<UserCategory> getUsers() {
		return users;
	}

	public DataActionPair[] getDatas() {
		return datas;
	}

	public DataActionPair getData() {
		return datas[0];
	}

	public Restriction[] getRestrictions() {
		return restrictions;
	}

	public Restriction getRestriction() {
		return restrictions[0];
	}

	public int getDimension() {
		return datas.length;
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
		for (DataActionPair pair : datas) {
			sb.append("Action: ");
			sb.append(pair.action);
			sb.append("\n\t");
			sb.append("Datas: ");
			for (DataCategory data : pair.datas) {
				sb.append(data.getId());
				sb.append(' ');
			}
			sb.append("\n\t");
		}
		for (Restriction restriction : restrictions) {
			sb.append(restriction);
			sb.append("\n\t");
		}
		return sb.toString();

	}
}
