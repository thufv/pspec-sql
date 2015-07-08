package edu.thu.ss.spec.lang.pojo;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

/**
 * class for user container
 * @author luochen
 *
 */
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
				user.setContainer(this);
				add(user);
			}
		}
	}

	@Override
	public Element outputElement(Document document) {
		Element element = super.outputType(document,
				ParserConstant.Ele_Vocabulary_User_Category_Container);

		for (UserCategory user : categories.values()) {
			Element userEle = user.outputElement(document);
			element.appendChild(userEle);
		}
		return element;
	}

	@Override
	public UserContainer getBaseContainer() {
		return (UserContainer) baseContainer;
	}

	public List<UserCategory> getRoot() {
		return root;
	}

}
