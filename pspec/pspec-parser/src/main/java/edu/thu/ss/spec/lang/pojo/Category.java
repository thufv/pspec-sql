package edu.thu.ss.spec.lang.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * a base class of category, each category is global unique by
 * {@link Category#id} and {@link Category#containerId}
 * 
 * @author luochen
 * 
 */
public abstract class Category<T extends Category<T>> extends HierarchicalObject<T> {

	protected String containerId;

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	/**
	 * @param container
	 * @return all descendants in the container of a given category
	 */
	@SuppressWarnings("unchecked")
	public Set<T> getDescendants(CategoryContainer<T> container) {
		Set<T> set = new HashSet<>();
		collectDescendants(set, (T) this, container);
		return set;
	}

	private void collectDescendants(Set<T> set, T t, CategoryContainer<T> container) {
		if (t.children == null) {
			set.add(t);
			return;
		}
		for (T child : t.children) {
			if (container.contains(child.id)) {
				collectDescendants(set, child, container);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((containerId == null) ? 0 : containerId.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (containerId == null) {
			if (other.containerId != null)
				return false;
		} else if (!containerId.equals(other.containerId))
			return false;
		return true;
	}

}
