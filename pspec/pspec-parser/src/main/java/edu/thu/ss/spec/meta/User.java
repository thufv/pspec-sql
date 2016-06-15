package edu.thu.ss.spec.meta;

import edu.thu.ss.spec.lang.pojo.UserCategory;

public class User{

  protected UserCategory userCategory;
	protected String name;

  public UserCategory getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(UserCategory userCategory) {
		this.userCategory = userCategory;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("user: ");
    sb.append(name);
    sb.append(" User Category: ");
    sb.append(userCategory.getId());
    return sb.toString();
  }
}
