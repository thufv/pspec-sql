package edu.thu.ss.spec.lang.parser;

import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.util.WritingException;
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

		Collection<UserContainer> users = vocabulary.getUserContainers().values();

		for (UserContainer user : users) {
			Element userEle = user.outputElement(document);
			root.appendChild(userEle);
		}
		Collection<DataContainer> datas = vocabulary.getDataContainers().values();

		for (DataContainer data : datas) {
			Element dataEle = data.outputElement(document);
			root.appendChild(dataEle);

		}
		try {
			XMLUtil.writeDocument(document, path);
		} catch (Exception e) {
			throw new WritingException("Fail to output vocabulary to " + path, e);
		}

	}
}
