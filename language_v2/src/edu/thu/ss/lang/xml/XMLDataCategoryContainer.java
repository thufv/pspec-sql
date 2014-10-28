package edu.thu.ss.lang.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.lang.parser.ParserConstant;
import edu.thu.ss.lang.pojo.DataCategory;

public class XMLDataCategoryContainer extends XMLCategoryContainer<DataCategory> {

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
				set(data.id, data);
			}
		}
	}

}
