package edu.thu.ss.spec.lang.parser;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.global.PolicyManager;
import edu.thu.ss.spec.lang.analyzer.PolicyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.global.GlobalExpander;
import edu.thu.ss.spec.lang.analyzer.global.GlobalRedundancyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.local.ConsistencyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.local.LocalExpander;
import edu.thu.ss.spec.lang.analyzer.local.LocalRedundancyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.rule.RuleConstraintAnalyzer;
import edu.thu.ss.spec.lang.analyzer.rule.RuleResolver;
import edu.thu.ss.spec.lang.analyzer.rule.RuleSimplifier;
import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.util.ParsingException;
import edu.thu.ss.spec.util.XMLUtil;

/**
 * main entrance for privacy language
 * @author luochen
 *
 */
public class PolicyParser implements ParserConstant {

	private static Logger logger = LoggerFactory.getLogger(PolicyParser.class);

	/**
	 * a list of {@link PolicyAnalyzer}, executed sequentially
	 */
	private List<PolicyAnalyzer> analyzers;

	protected void init(boolean global) {
		analyzers = new ArrayList<>();
		analyzers.add(new RuleResolver());

		analyzers.add(new RuleConstraintAnalyzer());

		analyzers.add(new RuleSimplifier());

		if (global) {
			//online
			analyzers.add(new GlobalExpander());
			analyzers.add(new GlobalRedundancyAnalyzer());
		} else {
			//offline
			analyzers.add(new LocalExpander());
			analyzers.add(new LocalRedundancyAnalyzer());
			analyzers.add(new ConsistencyAnalyzer());
		}

	}

	public Policy parse(String path) throws Exception {
		//online by default
		return parse(path, true);
	}

	/**
	 * parse a {@link Policy} from path
	 * @param path
	 * @param global
	 * @return {@link Policy}
	 * @throws Exception
	 */
	public Policy parse(String path, boolean global) throws Exception {
		URI uri = XMLUtil.toUri(path);
		Policy policy = PolicyManager.getPolicy(uri);
		if (policy != null) {
			logger.error("Policy: {} has already been parsed.", uri);
			return policy;
		}
		init(global);
		policy = new Policy();
		policy.setPath(uri);
		Document policyDoc = null;
		try {
			// load document
			policyDoc = XMLUtil.parseDocument(uri, Privacy_Schema_Location);
		} catch (Exception e) {
			throw new ParsingException("Fail to load privacy policy at " + path, e);
		}
		try {
			// parse document
			Node policyNode = policyDoc.getElementsByTagName(ParserConstant.Ele_Policy).item(0);
			NodeList list = policyNode.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				String name = node.getLocalName();
				if (Ele_Policy_Info.equals(name)) {
					Info info = new Info();
					info.parse(node);
					policy.setInfo(info);
				} else if (Ele_Policy_Vocabulary_Ref.equals(name)) {
					//parse referred vocabulary first
					parseVocabularyRef(node, policy);
				} else if (Ele_Policy_Rules.equals(name)) {
					parseRules(node, policy);
				}
			}
			//perform policy analysis
			analyzePolicy(policy);
		} catch (ParsingException e) {
			throw e;
		} catch (Exception e) {
			throw new ParsingException("Fail to parse privacy policy at " + path, e);
		} finally {
			cleanup();
		}

		//register parsed policy to PolicyManager
		PolicyManager.addPolicy(policy);
		return policy;
	}

	protected void cleanup() {
	}

	/**
	 * Invoking {@link VocabularyParser} to parse referred {@link Vocabulary}s
	 * @param refNode
	 * @param policy
	 * @throws Exception
	 */
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
		policy.setUserContainer(vocabulary.getUserContainer(userRef));
		policy.setDataContainer(vocabulary.getDataContainer(dataRef));

		policy.setUserContainers(vocabulary.getUserContainers());
		policy.setDataContainers(vocabulary.getDataContainers());

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

	private void analyzePolicy(Policy policy) throws ParsingException {
		for (PolicyAnalyzer analyzer : analyzers) {
			boolean error = analyzer.analyze(policy);
			if (error && analyzer.stopOnError()) {
				throw new ParsingException(analyzer.errorMsg());
			}
		}
	}

}
