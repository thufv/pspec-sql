package edu.thu.ss.spec.lang.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

/**
 * base class for category reference
 * @author luochen
 *
 * @param <T>
 */
public abstract class CategoryRef<T extends Category<T>> extends ObjectRef {
	/**
	 * referred category
	 */
	protected T category;

	/**
	 * excluded category ids
	 */
	protected Set<ObjectRef> excludeRefs = new LinkedHashSet<>();

	/**
	 * excluded categories
	 */
	protected Set<T> excludes = new HashSet<>();

	/**
	 * the materialized categories, calculated as {@link #category} - {@link #excludes}
	 */
	protected Set<T> materialized;

	public boolean contains(T t) {
		return materialized.contains(t);
	}

	public Set<ObjectRef> getExcludeRefs() {
		return excludeRefs;
	}

	public Set<T> getExcludes() {
		return excludes;
	}

	public T getCategory() {
		return category;
	}

	public void setCategory(T category) {
		this.category = category;
	}

	/**
	 * adds a excluded data category with simplification
	 * @param exclude
	 */
	public void exclude(T exclude) {
		if (!category.ancestorOf(exclude)) {
			throw new IllegalArgumentException("excluded category: " + exclude.getId()
					+ " must be a descedent of target category: " + category.getId());
		}
		Iterator<T> it = excludes.iterator();
		while (it.hasNext()) {
			T t = it.next();
			if (t.ancestorOf(exclude)) {
				return;
			} else if (exclude.ancestorOf(t)) {
				it.remove();
			}
		}
		excludes.add(exclude);
	}

	public Set<T> getMaterialized() {
		return materialized;
	}

	public boolean isError() {
		return error;
	}

	/**
	 * performs materialization based on specified container
	 * 
	 * @param container
	 * @param cache
	 */
	public void materialize(CategoryContainer<T> container) {
		materialized = new HashSet<>();
		materialized.addAll(category.getDescendants(container));
		for (T excluded : excludes) {
			materialized.removeAll(excluded.getDescendants(container));
		}
	}

	public void materialize(Set<T> materialized) {
		this.materialized = materialized;
	}

	@Override
	public void parse(Node refNode) {
		super.parse(refNode);
		NodeList list = refNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Exclude.equals(name)) {
				parseExclude(node);
			}
		}
	}

	@Override
	public Element outputType(Document document, String name) {
		Element element = document.createElement(name);
		element.setAttribute(ParserConstant.Attr_Refid, category.id);
		return element;
	}

	abstract protected void parseExclude(Node node);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((excludeRefs == null) ? 0 : excludeRefs.hashCode());
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
		CategoryRef other = (CategoryRef) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (excludeRefs == null) {
			if (other.excludeRefs != null)
				return false;
		} else if (!excludeRefs.equals(other.excludeRefs))
			return false;
		return true;
	}

}
