package edu.thu.ss.spec.lang.pojo;

public class UserCategory extends BaseCategory<UserCategory> {

	public UserCategory() {
	}

	public UserCategory(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return containerId + ":" + id;
	}
}
