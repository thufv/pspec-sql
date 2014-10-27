package edu.thu.ss.lang.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.lang.parser.ParserConstant;

public class UserCategoryContainer extends CategoryContainer<UserCategory> {

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
				set(user.id, user);
			}
		}
	}

}
