package edu.thu.ss.xml.parser;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.pojo.DataCategoryContainer;
import edu.thu.ss.xml.pojo.Info;
import edu.thu.ss.xml.pojo.Policy;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.UserCategoryContainer;
import edu.thu.ss.xml.pojo.Vocabulary;

public class PolicyParser implements ParserConstant {

	private static Logger logger = LoggerFactory.getLogger(PolicyParser.class);

	private RuleAnalyzer analyzer;
	private VocabularyParser vocabParser;

	protected void init() {
		analyzer = new RuleAnalyzer();
		vocabParser = new VocabularyParser();
	}

	protected void cleanup() {

	}

	public Policy parse(String path) throws ParsingException {

		init();
		Policy policy = new Policy();
		Document policyDoc = null;
		try {
			// load document
			policyDoc = XMLUtil.parseDocument(path, Schema_Location);
		} catch (Exception e) {
			throw new ParsingException("Fail to load privacy policy at " + path, e);
		}
		try {
			// parse document
			Node policyNode = policyDoc.getElementsByTagName(ParserConstant.Ele_Policy).item(0);
			parseAttribute(policyNode, policy);
			NodeList list = policyNode.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				String name = node.getLocalName();
				if (Ele_Policy_Info.equals(name)) {
					Info info = new Info();
					info.parse(node);
					policy.setInfo(info);
				} else if (Ele_Policy_Vocabulary_Ref.equals(name)) {
					parseVocabularyRef(node, policy);
				} else if (Ele_Policy_Rules.equals(name)) {
					parseRules(node, policy);
				}
			}
			analyzeReferences(policy);

		} catch (ParsingException e) {
			throw e;
		} catch (Exception e) {
			throw new ParsingException("Fail to parse privacy policy at " + path, e);
		} finally {
			cleanup();
		}

		return policy;
	}

	private void parseAttribute(Node policyNode, Policy policy) {
		String defaultRuling = XMLUtil.getAttrValue(policyNode, Attr_Policy_Default_Ruling);
		policy.setDefaultRuling(defaultRuling);
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

	private void parseVocabularyRef(Node refNode, Policy policy) throws Exception {
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
				dataRef = XMLUtil.getAttrValue(node, Attr_Refid);
			}
		}
		policy.setVocabularyLocation(location);
		policy.setUserRef(userRef);
		policy.setDataRef(dataRef);

		Vocabulary vocabulary = vocabParser.parse(location, userRef, dataRef);
		policy.setUsers(vocabulary.getUserCategories(userRef));
		policy.setDatas(vocabulary.getDataCategories(dataRef));

	}

	private void analyzeReferences(Policy policy) throws ParsingException {
		List<Rule> rules = policy.getRules();
		UserCategoryContainer userContainer = policy.getUsers();
		DataCategoryContainer dataContainer = policy.getDatas();
		boolean error = false;
		for (Rule rule : rules) {
			error = error || analyzer.subsitute(rule, userContainer, dataContainer);
		}
		if (error) {
			throw new ParsingException(
					"Error detected when analyzing category references in rule, see error messages above.");
		}

		for (Rule rule : rules) {
			error = error || analyzer.consistencyCheck(rule);
		}
		if (error) {
			throw new ParsingException(
					"Error detected when checking consistency of restricted data categories, see error messages above");
		}
		for (Rule rule : rules) {
			analyzer.simplify(rule);
		}
	}

}
