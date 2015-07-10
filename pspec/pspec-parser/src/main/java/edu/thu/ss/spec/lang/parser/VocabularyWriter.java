package edu.thu.ss.spec.lang.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.util.XMLUtil;

public class VocabularyWriter implements ParserConstant {

	public void output(Vocabulary vocabulary, String path) throws WritingException {
		Document document = null;
		try {
			document = XMLUtil.newDocument();
		} catch (Exception e) {
			throw new WritingException("Fail to create XML document.", e);
		}

		Element root = document.createElement(Ele_Vocabulary);
		document.appendChild(root);

		root.setAttribute(ParserConstant.Attr_XMLNs, XMLNs);

		if (vocabulary.getBase() != null) {
			root.setAttribute(Attr_Vocabulary_Base, vocabulary.getBase().toString());
		}

		Info info = vocabulary.getInfo();
		Element infoEle = info.outputType(document, Ele_Vocabulary_Info);
		root.appendChild(infoEle);

		Element userEle = vocabulary.getUserContainer().outputElement(document);
		root.appendChild(userEle);

		Element dataEle = vocabulary.getDataContainer().outputElement(document);
		root.appendChild(dataEle);

		try {
			XMLUtil.writeDocument(document, path);
		} catch (Exception e) {
			throw new WritingException("Fail to output vocabulary to " + path, e);
		}

	}
}
