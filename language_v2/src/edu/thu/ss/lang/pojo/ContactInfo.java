package edu.thu.ss.lang.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.lang.parser.ParserConstant;

public class ContactInfo implements Parsable {

	protected String name;
	protected String email;
	protected String address;
	protected String organization;
	protected String country;

	public void parse(Node contactNode) {
		NodeList list = contactNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Contact_Name.equals(name)) {
				this.name = node.getTextContent();
			} else if (ParserConstant.Ele_Contact_Email.equals(name)) {
				this.email = node.getTextContent();
			} else if (ParserConstant.Ele_Contact_Address.equals(name)) {
				this.address = node.getTextContent();
			} else if (ParserConstant.Ele_Contact_Organization.equals(name)) {
				this.organization = node.getTextContent();
			} else if (ParserConstant.Ele_Contact_Country.equals(name)) {
				this.country = node.getTextContent();
			}
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\tname: ");
		sb.append(name);
		sb.append("\n");
		sb.append("\temail: ");
		sb.append(email);
		sb.append("\n");

		sb.append("\taddress: ");
		sb.append(address);
		sb.append("\n");

		sb.append("\torganization: ");
		sb.append(organization);
		sb.append("\n");

		sb.append("\tcountry: ");
		sb.append(country);
		sb.append("\n");

		return sb.toString();
	}

}
