package edu.thu.ss.xml.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class ContactInfo {

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

}
