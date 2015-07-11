package edu.thu.ss.editor.model;

public abstract class BaseModel {

	protected String path;

	public BaseModel(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
