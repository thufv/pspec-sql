package edu.thu.ss.spec.lang.pojo;

import java.net.URI;
import java.util.List;
import java.util.Map;

import edu.thu.ss.spec.lang.analyzer.global.GlobalRule;
import edu.thu.ss.spec.lang.analyzer.local.LocalRule;

public class Policy {
	protected Info info;

	protected String vocabularyLocation;

	protected String userContainerRef;
	protected String dataContainerRef;

	protected UserContainer userContainer;

	protected DataContainer dataContainer;

	protected Map<String, UserContainer> userContainers;

	protected Map<String, DataContainer> dataContainers;

	protected List<Rule> rules;

	protected List<LocalRule> localRules;

	protected List<GlobalRule> globalRules;

	protected URI path;

	public void setPath(URI path) {
		this.path = path;
	}

	public URI getPath() {
		return path;
	}

	public void setLocalRules(List<LocalRule> localRules) {
		this.localRules = localRules;
	}

	public List<LocalRule> getLocalRules() {
		if (localRules == null) {
			throw new RuntimeException("Rules in policy: " + info.getId() + " not expanded yet.");
		}
		return localRules;
	}

	public void setGlobalRules(List<GlobalRule> globalRules) {
		this.globalRules = globalRules;
	}

	public List<GlobalRule> getGlobalRules() {
		if (globalRules == null) {
			throw new RuntimeException("Rules in policy: " + info.getId() + " not expanded yet.");
		}
		return globalRules;
	}

	public String getVocabularyLocation() {
		return vocabularyLocation;
	}

	public void setVocabularyLocation(String vocabularyLocation) {
		this.vocabularyLocation = vocabularyLocation;
	}

	public String getUserRef() {
		return userContainerRef;
	}

	public void setUserRef(String userRef) {
		this.userContainerRef = userRef;
	}

	public String getDataRef() {
		return dataContainerRef;
	}

	public void setDataRef(String dataRef) {
		this.dataContainerRef = dataRef;
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

	public void setDataContainers(Map<String, DataContainer> dataContainers) {
		this.dataContainers = dataContainers;
	}

	public void setUserContainers(Map<String, UserContainer> userContainers) {
		this.userContainers = userContainers;
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
		sb.append(userContainerRef);
		sb.append("\n");
		sb.append(userContainer.toString());
		sb.append("\n");

		sb.append("Data Categories: ");
		sb.append(dataContainerRef);
		sb.append("\n");
		sb.append(dataContainer.toString());
		sb.append("\n");

		sb.append("Expanded Rules:\n");
		if (globalRules != null) {
			for (ExpandedRule rule : globalRules) {
				sb.append(rule);
				sb.append("\n");
			}
		} else if (localRules != null) {
			for (ExpandedRule rule : localRules) {
				sb.append(rule);
				sb.append("\n");
			}
		}

		return sb.toString();

	}

	public DataCategory getDataCategory(String id) {
		return dataContainer.get(id);
	}
}
