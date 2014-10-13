package edu.thu.ss.xml.pojo;

import java.util.List;

import edu.thu.ss.xml.pojo.Rule.Ruling;

public class Policy {
	protected Info info;

	protected String vocabularyLocation;

	protected String userRef;
	protected String dataRef;

	protected CategoryContainer<UserCategory> users;
	protected CategoryContainer<DataCategory> datas;

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

}
