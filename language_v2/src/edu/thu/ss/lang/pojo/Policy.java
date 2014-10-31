package edu.thu.ss.lang.pojo;

import java.util.List;

import edu.thu.ss.lang.xml.XMLRule;

public class Policy {
	protected Info info;

	protected String vocabularyLocation;

	protected String userRef;
	protected String dataRef;

	protected UserCategoryContainer users;

	protected DataCategoryContainer datas;

	protected List<XMLRule> rules;

	protected List<ExpandedRule> expandedRules;

	public void setExpandedRules(List<ExpandedRule> expandedRules) {
		this.expandedRules = expandedRules;
	}

	public List<ExpandedRule> getExpandedRules() {
		return expandedRules;
	}

	public String getVocabularyLocation() {
		return vocabularyLocation;
	}

	public void setVocabularyLocation(String vocabularyLocation) {
		this.vocabularyLocation = vocabularyLocation;
	}

	public String getUserRef() {
		return userRef;
	}

	public void setUserRef(String userRef) {
		this.userRef = userRef;
	}

	public String getDataRef() {
		return dataRef;
	}

	public void setDataRef(String dataRef) {
		this.dataRef = dataRef;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public List<XMLRule> getRules() {
		return rules;
	}

	public void setRules(List<XMLRule> rules) {
		this.rules = rules;
	}

	public UserCategoryContainer getUsers() {
		return users;
	}

	public void setUsers(UserCategoryContainer users) {
		this.users = users;
	}

	public DataCategoryContainer getDatas() {
		return datas;
	}

	public void setDatas(DataCategoryContainer datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Policy Info: \n");
		sb.append(info);

		sb.append("\n");

		sb.append("User Categories: ");
		sb.append(userRef);
		sb.append("\n");
		sb.append(users.toString());
		sb.append("\n");

		sb.append("Data Categories: ");
		sb.append(dataRef);
		sb.append("\n");
		sb.append(datas.toString());
		sb.append("\n");

		/*
		 * sb.append("Rules:\n");
		for (Rule rule : rules) {
			sb.append(rule);
			sb.append("\n");
		}*/
		sb.append("Expanded Rules:\n");
		for (ExpandedRule rule : expandedRules) {
			sb.append(rule);
			sb.append("\n");
		}

		return sb.toString();

	}
}
