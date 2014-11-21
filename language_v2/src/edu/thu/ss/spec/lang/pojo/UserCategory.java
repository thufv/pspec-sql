package edu.thu.ss.spec.lang.pojo;

public class UserCategory extends Category<UserCategory> {

	public UserCategory() {
	}

	public UserCategory(String id, String containerId) {
		this.id = id;
		this.containerId = containerId;
	}

	@Override
	public String toString() {
		return containerId + ":" + id;
	}
}
