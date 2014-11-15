package edu.thu.ss.spec.lang.analyzer;

import edu.thu.ss.spec.lang.pojo.HierarchicalObject;

public interface CategoryVisitor<T extends HierarchicalObject<T>> {

	public void visit(T category);
}
