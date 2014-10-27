package edu.thu.ss.lang.analyzer;

import edu.thu.ss.lang.pojo.HierarchicalObject;

public interface CategoryVisitor<T extends HierarchicalObject<T>> {

	public void visit(T category);
}
