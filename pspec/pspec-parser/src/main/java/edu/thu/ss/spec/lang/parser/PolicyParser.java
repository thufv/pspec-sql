package edu.thu.ss.spec.lang.parser;

import java.net.URI;
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
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PolicyEvent;
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
public class PolicyParser implements ParserConstant {

	private static Logger logger = LoggerFactory.getLogger(PolicyParser.class);

	/**
	 * a list of {@link IPolicyAnalyzer}, executed sequentially
	 */
	private List<IPolicyAnalyzer> analyzers;

	private EventTable<PolicyEvent> table = new EventTable<>();

	protected void init(boolean global, boolean analyze) {
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

	public Policy parse(String path) throws Exception {
		//online by default
		return parse(path, true, true);
	}

	public Policy parse(String path, boolean global) throws Exception {
		//online by default
		return parse(path, global, true);
	}

	/**
	 * parse a {@link Policy} from path
	 * @param path
	 * @param global
	 * @return {@link Policy}
	 * @throws Exception
	 */
	public Policy parse(String path, boolean global, boolean analyze) throws Exception {
		URI uri = XMLUtil.toUri(path);
		Policy policy = PolicyManager.getPolicy(uri);
		if (policy != null) {
			logger.error("Policy: {} has already been parsed.", uri);
			return policy;
		}
		init(global, analyze);
		policy = new Policy();
		policy.setPath(uri);
		Document policyDoc = null;
		try {
			// load document
			policyDoc = XMLUtil.parseDocument(uri, Privacy_Schema_Location);
		} catch (Exception e) {
			throw new ParseException("Fail to load privacy policy at " + path, e);
		}
		try {
			// parse document
			Node policyNode = policyDoc.getElementsByTagName(ParserConstant.Ele_Policy).item(0);
			NodeList list = policyNode.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				String name = node.getLocalName();
				if (Ele_Policy_Info.equals(name)) {
					policy.getInfo().parse(node);
				} else if (Ele_Policy_Vocabulary_Ref.equals(name)) {
					//parse referred vocabulary first
					parseVocabularyRef(node, policy);
				} else if (Ele_Policy_Privacy_Params.equals(name)) {
					parsePrivacyBudget(node, policy);
				} else if (Ele_Policy_Rules.equals(name)) {
					parseRules(node, policy);
				}
			}
			//perform policy analysis
			analyzePolicy(policy);
		} catch (ParseException e) {
			throw e;
		} catch (Exception e) {
			throw new ParseException("Fail to parse privacy policy at " + path, e);
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
		policy.setVocabularyLocation(XMLUtil.toUri(location));
		VocabularyParser vocabParser = new VocabularyParser();
		Vocabulary vocabulary = vocabParser.parse(location);
		policy.setUserContainer(vocabulary.getUserContainer());
		policy.setDataContainer(vocabulary.getDataContainer());

	}

	private void parsePrivacyBudget(Node budgetNode, Policy policy) throws Exception {
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
			}
		}
	}

	private void analyzePolicy(Policy policy) throws ParseException {
		for (IPolicyAnalyzer analyzer : analyzers) {
			boolean error = analyzer.analyze(policy);
			if (error && analyzer.stopOnError()) {
				throw new ParseException(analyzer.errorMsg());
			}
		}
	}

}
