package edu.thu.ss.xml.pojo;

public class CategoryRef<T extends HierarchicalObject<T>> extends ObjectRef {
	protected T category;

	public T getCategory() {
		return category;
	}

}
