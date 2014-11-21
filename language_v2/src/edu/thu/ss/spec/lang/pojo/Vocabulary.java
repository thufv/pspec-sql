package edu.thu.ss.spec.lang.pojo;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

public class Vocabulary {

	protected Info info;

	protected String base;

	protected Map<String, UserContainer> userContainers = new HashMap<>();

	protected Map<String, DataContainer> dataContainers = new HashMap<>();

	protected Node rootNode;

	protected URI path;

	protected boolean resolved = false;

	public URI getPath() {
		return path;
	}

	public void setUserContainers(Map<String, UserContainer> userContainers) {
		this.userContainers = userContainers;
	}

	public void setDataContainers(Map<String, DataContainer> dataContainers) {
		this.dataContainers = dataContainers;
	}

	public void setPath(URI path) {
		this.path = path;
	}

	public boolean isResolved() {
		return resolved;
	}

	public Map<String, UserContainer> getUserContainers() {
		return userContainers;
	}

	public Map<String, DataContainer> getDataContainers() {
		return dataContainers;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public void setRootNode(Node rootNode) {
		this.rootNode = rootNode;
	}

	public Node getRootNode() {
		return rootNode;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public UserContainer getUserContainer(String id) {
		return userContainers.get(id);
	}

	public DataContainer getDataContainer(String id) {
		return dataContainers.get(id);
	}
}
