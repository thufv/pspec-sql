package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

public class Info extends DescribedObject {
	protected ContactInfo contact = new ContactInfo();
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
	public Element outputType(Document document, String name) {
		Element element = super.outputType(document, name);

		if (this.contact != null) {
			Element tmp = contact.output(document, ParserConstant.Ele_Policy_Issuer);
			element.appendChild(tmp);
		}
		if (this.location != null) {
			Element tmp = contact.output(document, ParserConstant.Ele_Policy_Location);
			element.appendChild(tmp);
		}
		return element;
	}

	@Override
	public Element outputElement(Document document) {
		return null;
	}

	@Override
	public void parse(Node infoNode) {
		super.parse(infoNode);
		NodeList list = infoNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Issuer.equals(name)) {
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
