package edu.thu.ss.xml.pojo;

import java.util.List;

import edu.thu.ss.xml.pojo.Rule.Ruling;

public class Policy {
	protected Info info;

	protected String vocabularyLocation;

	protected String userRef;
	protected String dataRef;

	protected UserCategoryContainer users;

	protected DataCategoryContainer datas;

	protected List<Rule> rules;

	protected Ruling defaultRuling;

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

	public Ruling getDefaultRuling() {
		return defaultRuling;
	}

	public void setDefaultRuling(String defaultRuling) {
		this.defaultRuling = Ruling.valueOf(defaultRuling);
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
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

		sb.append("Default Ruling: ");
		sb.append(defaultRuling);
		sb.append("\n");

		sb.append("Rules:\n");
		for (Rule rule : rules) {
			sb.append(rule);
			sb.append("\n");
		}
		return sb.toString();

	}
}
