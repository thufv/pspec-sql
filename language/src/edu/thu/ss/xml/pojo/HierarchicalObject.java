package edu.thu.ss.xml.pojo;

import java.util.List;

import org.w3c.dom.Node;

import edu.thu.ss.xml.parser.ParserConstant;
import edu.thu.ss.xml.parser.XMLUtil;

public class HierarchicalObject extends DescribedObject {
	protected String parentId;

	protected HierarchicalObject parent;
	protected List<HierarchicalObject> children;

	public void buildRelation(HierarchicalObject... children) {
		for (HierarchicalObject child : children) {
			this.children.add(child);
			child.parent = this;
		}
	}

	@Override
	public void parse(Node objNode) {
		super.parse(objNode);

		this.parentId = XMLUtil.getAttrValue(objNode, ParserConstant.Attr_Parent);

	}
}
