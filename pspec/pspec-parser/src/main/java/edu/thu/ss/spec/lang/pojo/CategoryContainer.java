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

	protected static int nextId = 0;

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

	public CategoryContainer() {
		this.id = String.valueOf(nextId++);
	}

	public void add(T t) {
		categories.put(t.id, t);
		if (t.parentId.isEmpty()) {
			root.add(t);
		}
		t.setContainer(this);
	}

	public void cascadeRemove(T t) {
		remove(t);
		if (t.children != null) {
			for (T c : t.children) {
				if (contains(c)) {
					cascadeRemove(c);
				}
			}
		}
	}

	public boolean contains(String id) {
		return get(id) != null;
	}

	public boolean directContains(T category) {
		return this.equals(category.container) && categories.containsKey(category.id);
	}

	public boolean contains(T category) {
		if (directContains(category)) {
			return true;
		}
		if (baseContainer != null) {
			return baseContainer.contains(category);
		}
		return false;
	}

	public boolean duplicate(String id) {
		if (baseContainer == null) {
			return false;
		}
		if (categories.containsKey(id)) {
			return baseContainer.contains(id);
		} else {
			return baseContainer.duplicate(id);
		}

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

	public void updateRoot() {
		if (baseContainer != null) {
			baseContainer.updateRoot();
		}

		root.clear();
		for (T category : categories.values()) {
			if (category.parent == null) {
				root.add(category);
			}
		}

	}

	public CategoryContainer<T> getBaseContainer() {
		return baseContainer;
	}

	public Collection<T> getCategories() {
		return categories.values();
	}

	public List<T> getChildren(T t) {
		if (t.getChildren() == null) {
			return null;
		}
		List<T> result = new ArrayList<>(t.getChildren().size());
		for (T child : t.getChildren()) {
			if (this.contains(child)) {
				result.add(child);
			}
		}
		return result;
	}

	public Map<String, T> getIndex() {
		return categories;
	}

	public List<T> getRoot() {
		return root;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public boolean isResolved() {
		return resolved;
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

	@Override
	public Element outputType(Document document, String name) {
		Element element = document.createElement(name);
		return element;
	}

	@Override
	public void parse(Node categoryNode) {
		String oldId = id;
		super.parse(categoryNode);
		this.id = oldId;
	}

	public void remove(T t) {
		categories.remove(t.id);
		if (t.parentId.isEmpty()) {
			root.remove(t);
		}
		t.setContainer(null);
	}

	public void setBaseContainer(CategoryContainer<T> baseContainer) {
		this.baseContainer = baseContainer;
		if (baseContainer != null) {
			baseContainer.leaf = false;
		}
	}

	@Override
	public void setId(String id) {
		super.setId(id);
		for (T category : categories.values()) {
			category.containerId = id;
		}
	}

	public void setParent(T category, T newParent) {
		T oldParent = category.getParent();
		if (oldParent == null) {
			root.remove(category);
		} else {
			oldParent.removeRelation(category);

		}

		category.setParent(newParent);
		if (newParent != null) {
			newParent.buildRelation(category);
		} else {
			category.setParentId("");
			root.add(category);
		}
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
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

	public void update(String newId, T category) {
		categories.remove(category.getId());
		category.setId(newId);
		categories.put(newId, category);
	}
}
