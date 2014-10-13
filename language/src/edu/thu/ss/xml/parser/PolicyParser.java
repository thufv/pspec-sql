package edu.thu.ss.xml.parser;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.pojo.Info;
import edu.thu.ss.xml.pojo.Policy;
import edu.thu.ss.xml.pojo.Rule;

public class PolicyParser implements ParserConstant {

	private static Logger logger = LoggerFactory.getLogger(PolicyParser.class);

	protected void init() {

	}

	public Policy parse(String path) {
		Policy policy = new Policy();
		Document policyDoc = null;
		try {
			// load document
			policyDoc = XMLUtil.parse(path, Schema_Location);
		} catch (Exception e) {
			logger.error("Fail to load privacy policy at " + path, e);
			return null;
		}

		try {
			// parse document
			Node policyNode = policyDoc.getFirstChild();
			parseAttribute(policyNode, policy);
			NodeList list = policyNode.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				String name = node.getLocalName();
				if (Ele_Policy_Info.equals(name)) {
					parsePolicyInfo(node, policy);
				} else if (Ele_Policy_Vocabulary_Ref.equals(name)) {
					parseVocabularyRef(node, policy);
				} else if (Ele_Policy_Rules.equals(name)) {
					parseRules(node, policy);
				}
			}
		} catch (Exception e) {
			logger.error("Fail to parse privacy policy at " + path, e);
		}

		return policy;
	}

	private void parseAttribute(Node policyNode, Policy policy) {
		String defaultRuling = XMLUtil.getAttrValue(policyNode, Attr_Policy_Default_Ruling);
		policy.setDefaultRuling(defaultRuling);
	}

	private void parsePolicyInfo(Node infoNode, Policy policy) {
		Info info = new Info();
		info.parse(infoNode);
		policy.setInfo(info);
	}

	private void parseRules(Node rulesNode, Policy policy) {
		List<Rule> rules = new ArrayList<>();
		NodeList list = rulesNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Policy_Rule_Allow.equals(name) || Ele_Policy_Rule_Deny.equals(name)
					|| Ele_Policy_Rule_Restrict.equals(name)) {
				Rule rule = new Rule();
				rule.parse(node);
				rules.add(rule);
			}
		}
		policy.setRules(rules);
	}

	private void parseVocabularyRef(Node refNode, Policy policy) {
		String location = XMLUtil.getAttrValue(refNode, Attr_Policy_Vocabulary_location);
		String userRef = null;
		String dataRef = null;
		NodeList list = refNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Policy_Vocabulary_User.equals(name)) {
				userRef = XMLUtil.getAttrValue(node, Attr_Refid);
			} else if (Ele_Policy_Vocabulary_Data.equals(name)) {
				dataRef = XMLUtil.getAttrValue(refNode, Attr_Refid);
			}
		}
		policy.setVocabularyLocation(location);
		policy.setUserRef(userRef);
		policy.setDataRef(dataRef);

	}

}
