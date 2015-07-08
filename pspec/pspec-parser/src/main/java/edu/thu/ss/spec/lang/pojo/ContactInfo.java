package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

public class ContactInfo implements Parsable {

	protected String name = "";
	protected String organization = "";
	protected String email = "";
	protected String address = "";
	protected String country = "";

	@Override
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

	public Element output(Document document, String name) {
		Element element = document.createElement(name);

		Element tmpEle = document.createElement(ParserConstant.Ele_Contact_Name);
		tmpEle.appendChild(document.createTextNode(name));
		element.appendChild(tmpEle);

		tmpEle = document.createElement(ParserConstant.Ele_Contact_Organization);
		tmpEle.appendChild(document.createTextNode(organization));
		element.appendChild(tmpEle);

		tmpEle = document.createElement(ParserConstant.Ele_Contact_Email);
		tmpEle.appendChild(document.createTextNode(email));
		element.appendChild(tmpEle);

		tmpEle = document.createElement(ParserConstant.Ele_Contact_Address);
		tmpEle.appendChild(document.createTextNode(address));
		element.appendChild(tmpEle);

		tmpEle = document.createElement(ParserConstant.Ele_Contact_Country);
		tmpEle.appendChild(document.createTextNode(country));
		element.appendChild(tmpEle);

		return element;
	}

	public String getName() {
		return name;
	}

	public String getOrganization() {
		return organization;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}

	public String getCountry() {
		return country;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCountry(String country) {
		this.country = country;
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
