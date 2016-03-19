package edu.thu.ss.spec.lang.pojo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * class for policy
 * @author luochen
 *
 */
public class Policy {
	protected Info info = new Info();

	protected URI vocabularyLocation;

	protected Vocabulary vocabulary;

	/**
	 * {@link UserContainer} of id {@link #userContainerRef}
	 */
	protected UserContainer userContainer = new UserContainer();

	/**
	 * {@link DataContainer} of id {@link #dataContainerRef}
	 */
	protected DataContainer dataContainer = new DataContainer();

	protected List<Rule> rules = new ArrayList<>();

	protected URI path;

	public Policy(String id) {
		info = new Info();
		info.id = id;
	}

	public Policy() {
	}

	public void setPath(URI path) {
		this.path = path;
	}

	public URI getPath() {
		return path;
	}

	public URI getVocabularyLocation() {
		return vocabularyLocation;
	}

	public void setVocabularyLocation(URI vocabularyLocation) {
		this.vocabularyLocation = vocabularyLocation;
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

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	public UserContainer getUserContainer() {
		return userContainer;
	}

	public DataContainer getDataContainer() {
		return dataContainer;
	}

	public void setUserContainer(UserContainer userContainer) {
		this.userContainer = userContainer;
	}

	public void setDataContainer(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Policy other = (Policy) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Policy Info: \n");
		sb.append(info);

		sb.append("\n");

		sb.append("User Categories: ");
		sb.append(userContainer.toString());
		sb.append("\n");

		sb.append("Data Categories: ");
		sb.append(dataContainer.toString());
		sb.append("\n");

		sb.append("Rules:\n");
		for (Rule rule : rules) {
			sb.append(rule);
			sb.append("\n");
		}

		return sb.toString();

	}

	public DataCategory getDataCategory(String id) {
		return dataContainer.get(id);
	}

	public UserCategory getUserCategory(String id) {
		return userContainer.get(id);
	}

	public void setVocabulary(Vocabulary vocabulary) {
		this.vocabularyLocation = vocabulary.path;
		this.vocabulary = vocabulary;
		this.userContainer = vocabulary.getUserContainer();
		this.dataContainer = vocabulary.getDataContainer();
	}

	public Rule getRule(String id) {
		for (Rule rule : rules) {
			if (rule.getId().equals(id)) {
				return rule;
			}
		}
		return null;
	}
}
