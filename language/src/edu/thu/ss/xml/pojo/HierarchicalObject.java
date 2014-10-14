package edu.thu.ss.xml.pojo;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import edu.thu.ss.xml.parser.ParserConstant;
import edu.thu.ss.xml.parser.XMLUtil;

public class HierarchicalObject extends DescribedObject {
	protected String parentId;

	protected HierarchicalObject parent;
	protected List<HierarchicalObject> children = new ArrayList<>();

	public void buildRelation(HierarchicalObject... children) {
		for (HierarchicalObject child : children) {
			this.children.add(child);
			child.parent = this;
		}
	}

	public boolean ancestorOf(HierarchicalObject obj) {
		HierarchicalObject p = obj.parent;
		while (p != null) {
			if (this.id.equals(p.id)) {
				return true;
			}
			p = p.parent;
		}
		return false;
	}

	public boolean descedantOf(HierarchicalObject obj) {
		return obj.ancestorOf(this);
	}

	public String getParentId() {
		return parentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HierarchicalObject other = (HierarchicalObject) obj;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		return true;
	}

	@Override
	public void parse(Node objNode) {
		super.parse(objNode);

		this.parentId = XMLUtil.getAttrValue(objNode, ParserConstant.Attr_Parent);

	}
}
