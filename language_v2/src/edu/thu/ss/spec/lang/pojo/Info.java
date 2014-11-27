package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

public class Info extends DescribedObject {
	protected ContactInfo contact;
	protected String location;

	public ContactInfo getContact() {
		return contact;
	}

	public void setContact(ContactInfo contact) {
		this.contact = contact;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public void parse(Node infoNode) {
		super.parse(infoNode);

		NodeList list = infoNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Issuer.equals(name)) {
				contact = new ContactInfo();
				contact.parse(node);
			} else if (ParserConstant.Ele_Policy_Location.equals(name)) {
				this.location = node.getTextContent();
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\tContact: \n");
		sb.append(contact);
		sb.append("\n");
		if (location != null) {
			sb.append("location: ");
			sb.append("location");
		}
		return sb.toString();

	}

}