package edu.thu.ss.spec.lang.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
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

}
