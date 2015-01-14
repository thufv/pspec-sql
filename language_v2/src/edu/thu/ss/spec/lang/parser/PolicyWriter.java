package edu.thu.ss.spec.lang.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.util.WritingException;
import edu.thu.ss.spec.util.XMLUtil;

/**
 * Output analyzed policy to xml
 * 
 * @author luochen
 * 
 */
public class PolicyWriter implements ParserConstant {

	public void output(Policy policy, String path) throws WritingException {
		Document document = null;
		try {
			document = XMLUtil.newDocument();
		} catch (Exception e) {
			throw new WritingException("Fail to create XML document.", e);
		}

		Element root = document.createElement(Ele_Policy);
		document.appendChild(root);

		root.setAttribute(ParserConstant.Attr_XMLNs, XMLNs);

		Info info = policy.getInfo();
		Element infoEle = info.outputType(document, Ele_Policy_Info);
		root.appendChild(infoEle);

		Element vocabEle = outputVocabularyRef(policy, document);
		root.appendChild(vocabEle);

		Element rulesEle = document.createElement(Ele_Policy_Rules);
		root.appendChild(rulesEle);

		for (ExpandedRule rule : policy.getExpandedRules()) {
			Element ruleEle = rule.outputElement(document);
			rulesEle.appendChild(ruleEle);
		}

		try {
			XMLUtil.writeDocument(document, path);
		} catch (Exception e) {
			throw new WritingException("Fail to output policy to " + path, e);
		}

	}

	private Element outputVocabularyRef(Policy policy, Document document) {
		Element vocabEle = document.createElement(Ele_Policy_Vocabulary_Ref);

		Element userEle = document.createElement(Ele_Policy_Vocabulary_User);
		vocabEle.appendChild(userEle);
		userEle.setAttribute(ParserConstant.Attr_Refid, policy.getUserRef());

		Element dataEle = document.createElement(Ele_Policy_Vocabulary_Data);
		vocabEle.appendChild(dataEle);
		dataEle.setAttribute(ParserConstant.Attr_Refid, policy.getDataRef());

		vocabEle.setAttribute(Attr_Policy_Vocabulary_location,
				policy.getVocabularyLocation());

		return vocabEle;
	}
}
