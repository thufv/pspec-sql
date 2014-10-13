package edu.thu.ss.xml.pojo;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

public class Vocabulary {

	protected Info info;

	protected String base;

	protected Map<String, UserCategoryContainer> userContainers = new HashMap<>();

	protected Map<String, DataCategoryContainer> dataContainers = new HashMap<>();

	protected Document document;

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
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
}
