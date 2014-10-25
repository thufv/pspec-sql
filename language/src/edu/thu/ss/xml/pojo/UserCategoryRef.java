package edu.thu.ss.xml.pojo;

public class UserCategoryRef extends CategoryRef<UserCategory> {

	public void setUser(UserCategory user) {
		this.category = user;
	}

	public UserCategory getUser() {
		return category;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("User Category: ");
		sb.append(refid);
		return sb.toString();
	}
}
