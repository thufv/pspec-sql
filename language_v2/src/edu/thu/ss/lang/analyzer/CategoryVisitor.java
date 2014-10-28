package edu.thu.ss.lang.analyzer;

import edu.thu.ss.lang.xml.XMLHierarchicalObject;

public interface CategoryVisitor<T extends XMLHierarchicalObject<T>> {

	public void visit(T category);
}
