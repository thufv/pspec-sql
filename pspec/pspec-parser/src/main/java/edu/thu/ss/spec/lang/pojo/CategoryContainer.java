package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

/**
 * a base class for category container
 * 
 * @author luochen
 * 
 */
public abstract class CategoryContainer<T extends Category<T>> extends DescribedObject {

	/**
	 * container id for base container
	 */
	protected String baseId;
	protected CategoryContainer<T> baseContainer;

	protected Map<String, T> categories = new LinkedHashMap<>();

	/**
	 * all root categories in the container
	 */
	protected List<T> root = new ArrayList<>();

	protected boolean resolved = false;

	protected boolean leaf = true;

	public void add(T c) {
		this.set(c.id, c);
		c.containerId = this.id;
	}

	/**
	 * lookup a category by id recursively.
	 * 
	 * @param id
	 * @return category
	 */
	public T get(String id) {
		T t = categories.get(id);
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

	public CategoryContainer<T> getBaseContainer() {
		return baseContainer;
	}

	public Collection<T> getCategories() {
		return categories.values();
	}

	public void setBaseContainer(CategoryContainer<T> baseContainer) {
		this.baseContainer = baseContainer;
		baseContainer.leaf = false;
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

	public boolean contains(String id) {
		return get(id) != null;
	}

	public void set(String id, T category) {
		categories.put(id, category);
	}

	public String getBase() {
		return baseId;
	}

	public Map<String, T> getIndex() {
		return categories;
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
	public Element outputType(Document document, String name) {
		Element element = super.outputType(document, name);
		if (this.baseId != null) {
			element.setAttribute(ParserConstant.Attr_Vocabulary_Base, this.baseId);
		}
		return element;
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
