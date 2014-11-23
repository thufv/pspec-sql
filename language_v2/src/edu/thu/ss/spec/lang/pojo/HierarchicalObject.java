package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

public class HierarchicalObject<T extends HierarchicalObject<T>> extends DescribedObject {
	protected String parentId;

	protected T parent;
	protected List<T> children = null;

	public T getParent() {
		return parent;
	}

	public List<T> getChildren() {
		return children;
	}

	@SuppressWarnings("unchecked")
	public boolean descedantOf(T obj) {
		return obj.ancestorOf((T) this);
	}

	public String getParentId() {
		return parentId;
	}

	@SuppressWarnings("unchecked")
	public void buildRelation(T... children) {
		for (T child : children) {
			if (this.children == null) {
				this.children = new ArrayList<>();
			}
			this.children.add(child);
			child.parent = (T) this;
		}
	}

	public boolean ancestorOf(T obj) {
		T p = obj;
		while (p != null) {
			if (this.id.equals(p.id)) {
				return true;
			}
			p = p.parent;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		T other = (T) obj;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public String toFullString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		if (parentId != null) {
			sb.append("\tparentId: ");
			sb.append(parentId);
		}
		if (children != null) {
			sb.append("\tchildIds: ");
			for (T child : children) {
				sb.append(child.id);
				sb.append(' ');
			}
		}
		return sb.toString();
	}

	@Override
	public void parse(Node objNode) {
		super.parse(objNode);

		this.parentId = XMLUtil.getAttrValue(objNode, ParserConstant.Attr_Parent);

	}
}
