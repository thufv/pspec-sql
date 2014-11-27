package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

public class UserContainer extends CategoryContainer<UserCategory> {

	@Override
	public void parse(Node categoryNode) {
		super.parse(categoryNode);

		NodeList list = categoryNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Vocabulary_User_Category.equals(name)) {
				UserCategory user = new UserCategory();
				user.parse(node);
				user.setContainerId(this.id);
				set(user.getId(), user);
			}
		}
	}

	@Override
	public UserContainer getBaseContainer() {
		return (UserContainer) baseContainer;
	}

}