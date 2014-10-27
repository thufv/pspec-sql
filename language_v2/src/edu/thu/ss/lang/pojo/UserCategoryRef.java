package edu.thu.ss.lang.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.lang.parser.ParserConstant;

public class UserCategoryRef extends CategoryRef<UserCategory> {

	public void setUser(UserCategory user) {
		this.category = user;
	}

	public UserCategory getUser() {
		return category;
	}

	@Override
	protected void parseExclude(Node excludeNode) {
		NodeList list = excludeNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_UserRef.equals(name)) {
				ObjectRef ref = new ObjectRef();
				ref.parse(node);
				excludeRefs.add(ref);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("User Category: ");
		sb.append(refid);
		if (excludeRefs.size() > 0) {
			sb.append("\tExclude:");
			for (ObjectRef ref : excludeRefs) {
				sb.append(ref.refid);
				sb.append(' ');
			}
		}
		return sb.toString();
	}
}
