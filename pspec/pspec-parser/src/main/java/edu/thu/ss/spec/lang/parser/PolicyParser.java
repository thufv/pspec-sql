package edu.thu.ss.spec.lang.parser;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.analyzer.GlobalExpander;
import edu.thu.ss.spec.lang.analyzer.IPolicyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.LocalExpander;
import edu.thu.ss.spec.lang.analyzer.budget.BudgetResolver;
import edu.thu.ss.spec.lang.analyzer.budget.FineBudgetAllocator;
import edu.thu.ss.spec.lang.analyzer.budget.GlobalBudgetAllocator;
import edu.thu.ss.spec.lang.analyzer.rule.RuleResolver;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.PrivacyParams;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.manager.PolicyManager;
import edu.thu.ss.spec.util.XMLUtil;

/**
 * main entrance for privacy language
 * @author luochen
 *
 */
public class PolicyParser extends BaseParser implements ParserConstant {

	private static Logger logger = LoggerFactory.getLogger(PolicyParser.class);

	private Policy policy;

	/**
	 * a list of {@link IPolicyAnalyzer}, executed sequentially
	 */
	private List<IPolicyAnalyzer> analyzers;

	protected void init(boolean global) {
		analyzers = new ArrayList<>();
		analyzers.add(new RuleResolver(table));
		analyzers.add(new BudgetResolver(table));
		analyzers.add(new GlobalBudgetAllocator());
		analyzers.add(new FineBudgetAllocator());
		if (global) {
			analyzers.add(new GlobalExpander(table));
		} else {
			analyzers.add(new LocalExpander(table));
		}
	}

	/**
	 * parse a {@link Policy} from path
	 * @param path
	 * @param global
	 * @return {@link Policy}
	 * @throws Exception
	 */
	public Policy parse(String path) throws InvalidPolicyException, InvalidVocabularyException {
		uri = XMLUtil.toUri(path);
		policy = PolicyManager.getPolicy(uri);
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
			throw new InvalidPolicyException(uri, e);
		}
		// parse document
		Node policyNode = policyDoc.getElementsByTagName(ParserConstant.Ele_Policy).item(0);
		if (policyNode == null) {
			throw new InvalidPolicyException(uri, null);
		}

		NodeList list = policyNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Policy_Info.equals(name)) {
				policy.getInfo().parse(node);
			} else if (Ele_Policy_Vocabulary_Ref.equals(name)) {
				//parse referred vocabulary first
				parseVocabularyRef(node, policy, forceRegister);
			} else if (Ele_Policy_Privacy_Params.equals(name)) {
				parsePrivacyBudget(node, policy);
			} else if (Ele_Policy_Rules.equals(name)) {
				parseRules(node, policy);
			}
		}
		//perform policy analysis
		error = analyzePolicy(policy);

		//register parsed policy to PolicyManager
		if (forceRegister || !error) {
			PolicyManager.addPolicy(policy);
		}
		return policy;
	}

	public void addAnalyzer(IPolicyAnalyzer analyzer) {
		analyzers.add(analyzer);
	}

	/**
	 * Invoking {@link VocabularyParser} to parse referred {@link Vocabulary}s
	 * @param refNode
	 * @param policy
	 * @throws Exception
	 */
	private void parseVocabularyRef(Node refNode, Policy policy, boolean forceRegister)
			throws InvalidVocabularyException {
		String location = XMLUtil.getAttrValue(refNode, Attr_Policy_Vocabulary_location);
		policy.setVocabularyLocation(XMLUtil.toUri(location));
		VocabularyParser vocabParser = new VocabularyParser();
		Vocabulary vocabulary = vocabParser.parse(location);
		policy.setVocabulary(vocabulary);

	}

	private void parsePrivacyBudget(Node budgetNode, Policy policy) {
		PrivacyParams budget = new PrivacyParams();
		budget.parse(budgetNode);
		policy.setPrivacyBudget(budget);
	}

	private void parseRules(Node rulesNode, Policy policy) {
		List<Rule> rules = policy.getRules();
		NodeList list = rulesNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Policy_Rule.equals(name)) {
				Rule rule = new Rule();
				rule.parse(node);
				rules.add(rule);
				table.onParseRule(rule);
			}
		}
	}

	private boolean analyzePolicy(Policy policy) {
		boolean error = false;
		for (IPolicyAnalyzer analyzer : analyzers) {
			if (analyzer.analyze(policy)) {
				error = true;
				if (analyzer.stopOnError()) {
					return error;
				}
			}
		}
		return error;
	}

}
