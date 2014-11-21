package edu.thu.ss.spec.lang.pojo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

public abstract class CategoryRef<T extends Category<T>> extends ObjectRef {
	protected T category;

	protected Set<ObjectRef> excludeRefs = new HashSet<>();

	protected Set<T> excludes = new HashSet<>();

	protected Set<T> materialized;

	public boolean contains(T t) {
		return category.ancestorOf(t);
	}

	public Set<ObjectRef> getExcludeRefs() {
		return excludeRefs;
	}

	public Set<T> getExcludes() {
		return excludes;
	}

	public Set<T> getMaterialized() {
		return materialized;
	}

	public void materialize(CategoryContainer<T> container, Map<T, Set<T>> cache) {
		materialized = cache.get(category);
		if (materialized != null) {
			return;
		}
		materialized = getDescendants(category, container, cache);
		for (T excluded : excludes) {
			Set<T> excludes = getDescendants(excluded, container, cache);
			materialized.removeAll(excludes);
		}
	}

	public void materialize(Set<T> materialized) {
		this.materialized = materialized;
	}

	private Set<T> getDescendants(T category, CategoryContainer<T> container, Map<T, Set<T>> cache) {
		Set<T> descendants = cache.get(category);
		if (descendants == null) {
			descendants = category.getDescendants(container);
			cache.put(category, descendants);
		}
		return descendants;

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

	abstract protected void parseExclude(Node node);

	public T getCategory() {
		return category;
	}

}
