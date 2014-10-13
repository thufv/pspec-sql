package edu.thu.ss.xml.pojo;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import edu.thu.ss.xml.parser.ParserConstant;
import edu.thu.ss.xml.parser.XMLUtil;

public abstract class CategoryContainer<T> extends DescribedObject {

	protected String base;

	protected Map<String, T> container;
	protected List<T> root;

	public T get(String id) {
		return container.get(id);
	}

	public void set(String id, T category) {
		container.put(id, category);
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	@Override
	public void parse(Node categoryNode) {
		super.parse(categoryNode);

		this.base = XMLUtil.getAttrValue(categoryNode, ParserConstant.Attr_Vocabulary_Base);
	}

}
