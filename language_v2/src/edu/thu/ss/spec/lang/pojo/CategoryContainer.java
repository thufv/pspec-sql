package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import edu.thu.ss.spec.lang.analyzer.CategoryVisitor;
import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

public abstract class CategoryContainer<T extends Category<T>> extends DescribedObject {

	protected String baseId;
	protected CategoryContainer<T> baseContainer;

	protected Map<String, T> index = new LinkedHashMap<>();
	protected List<T> root = new ArrayList<>();
	protected boolean resolved = false;
	protected boolean leaf = true;

	public void accept(CategoryVisitor<T> visitor) {
		for (T t : root) {
			visitor.visit(t);
		}
	}

	public boolean isResolved() {
		return resolved;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public CategoryContainer<T> getBaseContainer() {
		return baseContainer;
	}

	public Collection<T> getCategories() {
		return index.values();
	}

	public void setBaseContainer(CategoryContainer<T> baseContainer) {
		this.baseContainer = baseContainer;
		baseContainer.leaf = false;
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

	public boolean contains(String id) {
		return get(id) != null;
	}

	public void set(String id, T category) {
		index.put(id, category);
	}

	public String getBase() {
		return baseId;
	}

	public Map<String, T> getIndex() {
		return index;
	}

	public List<T> getRoot() {
		return root;
	}

	public void setBase(String base) {
		this.baseId = base;
	}

	@Override
	public void parse(Node categoryNode) {
		super.parse(categoryNode);

		this.baseId = XMLUtil.getAttrValue(categoryNode, ParserConstant.Attr_Vocabulary_Base);
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
