package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

/**
 * class for rule, directly parsed from xml.
 * @author luochen
 *
 */
public class Rule extends DescribedObject {

	protected UserRef userRef = null;
	protected DataAssociation association = null;

	protected List<Restriction> restrictions = new ArrayList<>();

	protected Map<String, Object> storage = new HashMap<>();

	public Object get(String key) {
		return storage.get(key);
	}

	public void put(String key, Object value) {
		storage.put(key, value);
	}

	public int getDimension() {
		return association.getDimension();
	}

	public UserRef getUserRef() {
		return userRef;
	}

	public void setUserRef(UserRef userRef) {
		this.userRef = userRef;
	}

	public DataAssociation getDataAssociation() {
		return association;
	}

	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public List<Restriction> getRestrictions() {
		return restrictions;
	}

	public Restriction getRestriction() {
		return restrictions.get(0);
	}

	@Override
	public void parse(Node ruleNode) {
		super.parse(ruleNode);

		String name = ruleNode.getLocalName();

		NodeList list = ruleNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_UserRef.equals(name)) {
				userRef = new UserRef();
				userRef.parse(node);
			} else if (ParserConstant.Ele_Policy_Rule_DataAsscoation.equals(name)) {
				association = new DataAssociation();
				association.parse(node);
			} else if (ParserConstant.Ele_Policy_Rule_Restriction.equals(name)) {
				Restriction restriction = new Restriction();
				restriction.parse(node);
				this.restrictions.add(restriction);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rule: ");
		sb.append(id);
		sb.append("\n");

		sb.append('\t');
		sb.append(userRef);
		sb.append("\n");

		sb.append('\t');
		sb.append(association);
		sb.append("\n");

		for (Restriction res : restrictions) {
			sb.append('\t');
			sb.append(res);
		}

		return sb.toString();
	}

	public Integer getRestrictionIndex(Restriction res) {
		return restrictions.indexOf(res);
	}

	@Override
	public Element outputElement(Document document) {
		Element element = super.outputType(document, ParserConstant.Ele_Policy_Rule);
		element.setAttribute(ParserConstant.Attr_Id, id);

		Element refEle = userRef.outputElement(document);
		element.appendChild(refEle);

		Element assocEle = association.outputElement(document);
		element.appendChild(assocEle);

		if (restrictions.isEmpty()) {
			Element forbidEle = document.createElement(ParserConstant.Ele_Policy_Rule_Forbid);
			element.appendChild(forbidEle);
		} else {
			for (Restriction res : restrictions) {
				Element resEle = res.outputElement(document);
				element.appendChild(resEle);
			}
		}

		return element;

	}

	public void setDataAssociation(DataAssociation association) {
		this.association = association;

	}

	public boolean isForbid() {
		return restrictions.size() == 0;
	}
}
