package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
	protected CategoryContainer<T> baseContainer;

	protected Map<String, T> categories = new LinkedHashMap<>();

	/**
	 * all root categories in the container
	 */
	protected List<T> root = new ArrayList<>();

	protected boolean resolved = false;

	protected boolean leaf = true;

	public void add(T t) {
		categories.put(t.id, t);
		if (t.parentId.isEmpty()) {
			root.add(t);
		}
		t.setContainer(this);
	}

	public List<T> getRoot() {
		return root;
	}

	public List<T> materializeRoots() {
		List<T> list = new ArrayList<>();
		materializeRoots(list);
		return list;
	}

	private void materializeRoots(List<T> list) {
		if (baseContainer != null) {
			baseContainer.materializeRoots(list);
		}
		list.addAll(root);
	}

	public void cascadeRemove(T t) {
		remove(t);
		if (t.children != null) {
			for (T c : t.children) {
				cascadeRemove(c);
			}
		}
	}

	public void remove(T t) {
		categories.remove(t.id);
		if (t.parentId.isEmpty()) {
			root.remove(t);
		}
		t.containerId = "";
	}

	public void update(String newId, T category) {
		categories.remove(category.getId());
		category.setId(newId);
		categories.put(newId, category);
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

	public boolean directContains(String id) {
		return categories.containsKey(id);
	}

	public Map<String, T> getIndex() {
		return categories;
	}

	//public List<T> getRoot() {
	//	return root;
	//}

	@Override
	public void parse(Node categoryNode) {
		super.parse(categoryNode);
	}

	@Override
	public Element outputType(Document document, String name) {
		Element element = super.outputType(document, name);
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
