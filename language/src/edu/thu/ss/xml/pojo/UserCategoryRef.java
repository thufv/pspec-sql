package edu.thu.ss.xml.pojo;

public class UserCategoryRef extends ObjectRef {
	protected UserCategory user;

	public void setUser(UserCategory user) {
		this.user = user;
	}

	public UserCategory getUser() {
		return user;
	}
}
