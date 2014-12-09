package edu.thu.ss.spec.lang.pojo;

/**
 * class for user category
 * @author luochen
 *
 */
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
