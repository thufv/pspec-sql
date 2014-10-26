package edu.thu.ss.xml.parser;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.analyzer.PolicyAnalyzer;
import edu.thu.ss.xml.analyzer.PolicyExpander;
import edu.thu.ss.xml.analyzer.RuleResolver;
import edu.thu.ss.xml.analyzer.RuleSimplifier;
import edu.thu.ss.xml.analyzer.SyntacticConsistencyChecker;
import edu.thu.ss.xml.pojo.Info;
import edu.thu.ss.xml.pojo.Policy;
import edu.thu.ss.xml.pojo.Rule;
import edu.thu.ss.xml.pojo.Vocabulary;

public class PolicyParser implements ParserConstant {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(PolicyParser.class);

	private List<PolicyAnalyzer> analyzers;

	protected void init() {
		analyzers = new ArrayList<>();
		analyzers.add(new RuleResolver());
		analyzers.add(new SyntacticConsistencyChecker());
		analyzers.add(new RuleSimplifier());
		// analyzers.add(new RedundancyAnalyzer());
		analyzers.add(new PolicyExpander());
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

			analyzePolicy(policy);
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

	}

	private void parseRules(Node rulesNode, Policy policy) {
		List<Rule> rules = new ArrayList<>();
		NodeList list = rulesNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Policy_Rule.equals(name)) {
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
		VocabularyParser vocabParser = new VocabularyParser();
		Vocabulary vocabulary = vocabParser.parse(location, userRef, dataRef);
		policy.setUsers(vocabulary.getUserCategories(userRef));
		policy.setDatas(vocabulary.getDataCategories(dataRef));

	}

	private void analyzePolicy(Policy policy) throws ParsingException {
		for (PolicyAnalyzer analyzer : analyzers) {
			boolean error = analyzer.analyze(policy);
			if (error && analyzer.stopOnError()) {
				throw new ParsingException(analyzer.errorMsg());
			}
		}

	}

}
