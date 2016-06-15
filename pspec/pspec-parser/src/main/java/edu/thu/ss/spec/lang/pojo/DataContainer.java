package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

/**
 * class for data container
 * @author luochen
 *
 */
public class DataContainer extends CategoryContainer<DataCategory> {

	@Override
	public void parse(Node categoryNode) {
		super.parse(categoryNode);

		NodeList list = categoryNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Vocabulary_Data_Category.equals(name)) {
				DataCategory data = new DataCategory();
				data.parse(node);
				data.setContainer(this);
				add(data);
			}
		}
	}

	@Override
	public DataContainer getBaseContainer() {
		return (DataContainer) baseContainer;
	}

	@Override
	public Element outputElement(Document document) {
		Element element = super.outputType(document,
				ParserConstant.Ele_Vocabulary_Data_Category_Container);

		for (DataCategory data : categories.values()) {
			Element dataEle = data.outputElement(document);
			element.appendChild(dataEle);
		}
		return element;
	}

}
