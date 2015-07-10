package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

/**
 * class for user ref
 * @author luochen
 *
 */
public class UserRef extends CategoryRef<UserCategory> {

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
	public Element outputElement(Document document) {
		Element element = super.outputType(document, ParserConstant.Ele_Policy_Rule_UserRef);

		if (this.excludes.size() > 0) {
			Element excludeEle = document.createElement(ParserConstant.Ele_Policy_Rule_Exclude);
			element.appendChild(excludeEle);

			for (UserCategory user : excludes) {
				Element userEle = document.createElement(ParserConstant.Ele_Policy_Rule_UserRef);
				userEle.setAttribute(ParserConstant.Attr_Refid, user.id);
				excludeEle.appendChild(userEle);
			}
		}
		return element;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("User Category: ");
		sb.append(refid);
		if (excludeRefs.size() > 0) {
			sb.append("\tExclude:");
			for (ObjectRef ref : excludeRefs) {
				sb.append(ref.getRefid());
				sb.append(' ');
			}
		}
		return sb.toString();
	}

	@Override
	public UserRef clone() {
		UserRef ref = new UserRef();
		ref.refid = refid;
		ref.resolved = resolved;
		ref.category = category;
		for (ObjectRef exclude : excludeRefs) {
			ref.excludeRefs.add(exclude);
		}
		return ref;
	}
}
