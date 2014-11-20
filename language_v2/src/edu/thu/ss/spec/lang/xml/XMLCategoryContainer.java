package edu.thu.ss.spec.lang.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import edu.thu.ss.spec.lang.analyzer.CategoryVisitor;
import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.pojo.BaseCategory;
import edu.thu.ss.spec.util.XMLUtil;

public abstract class XMLCategoryContainer<T extends BaseCategory<T>> extends XMLDescribedObject {

	protected String base;
	protected XMLCategoryContainer<T> baseContainer;
	protected List<T> categories = new LinkedList<>();
	protected Map<String, T> index = new HashMap<>();
	protected List<T> root = new ArrayList<>();
	protected boolean resolved = false;
	protected XMLCategoryContainer<T> childContainer;

	public void accept(CategoryVisitor<T> visitor) {
		for (T t : root) {
			visitor.visit(t);
		}
	}

	public boolean isResolved() {
		return resolved;
	}

	public boolean isLeaf() {
		return childContainer == null;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public XMLCategoryContainer<T> getBaseContainer() {
		return baseContainer;
	}

	public void setBaseContainer(XMLCategoryContainer<T> baseContainer) {
		this.baseContainer = baseContainer;
		baseContainer.childContainer = this;
	}

	public T get(String id) {
		T t = index.get(id);
		if (t != null) {
			return t;
		} else {
			if (baseContainer != null) {
				return baseContainer.get(id);
			} else {
				return null;
			}
		}
	}

	public void set(String id, T category) {
		index.put(id, category);
		categories.add(category);
	}

	public String getBase() {
		return base;
	}

	public List<T> getCategories() {
		return categories;
	}

	public Map<String, T> getIndex() {
		return index;
	}

	public List<T> getRoot() {
		return root;
	}

	public void setBase(String base) {
		this.base = base;
	}

	@Override
	public void parse(Node categoryNode) {
		super.parse(categoryNode);

		this.base = XMLUtil.getAttrValue(categoryNode, ParserConstant.Attr_Vocabulary_Base);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (baseContainer != null) {
			sb.append(baseContainer.toString());
		}
		for (T node : root) {
			toString(node, sb);
			sb.append("\n");
		}
		return sb.toString();
	}

	private void toString(T node, StringBuilder sb) {
		sb.append(node.toFullString());
		sb.append("\n");
		if (node.getChildren() != null) {
			for (T child : node.getChildren()) {
				toString(child, sb);
			}
		}

	}
}
