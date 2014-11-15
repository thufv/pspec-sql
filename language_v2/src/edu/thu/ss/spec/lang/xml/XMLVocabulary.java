package edu.thu.ss.spec.lang.xml;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import edu.thu.ss.spec.lang.pojo.DataCategoryContainer;
import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.UserCategoryContainer;

public class XMLVocabulary {

	protected Info info;

	protected String base;

	protected Map<String, UserCategoryContainer> userContainers = new HashMap<>();

	protected Map<String, DataCategoryContainer> dataContainers = new HashMap<>();

	protected Node rootNode;

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

	public void addUserCategories(String id, UserCategoryContainer categories) {
		userContainers.put(id, categories);
	}

	public void addDataCategories(String id, DataCategoryContainer categories) {
		dataContainers.put(id, categories);
	}

	public UserCategoryContainer getUserCategories(String id) {
		return userContainers.get(id);
	}

	public DataCategoryContainer getDataCategories(String id) {
		return dataContainers.get(id);
	}
}
