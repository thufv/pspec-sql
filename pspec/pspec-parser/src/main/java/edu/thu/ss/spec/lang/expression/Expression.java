package edu.thu.ss.spec.lang.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.thu.ss.spec.lang.pojo.Parsable;
import edu.thu.ss.spec.lang.pojo.Writable;

public abstract class Expression<T> implements Parsable, Writable {

	/**
   * Get all the variables<T> in expression
   * which may be absent from the DataRef(s) defined in Rule Scope
   */
	public abstract Set<T> getDataSet();
	
	/**
   * Split the expression to several sub-expressions that 
 	 * are required to be true at the same time
   * <p>E.g. AND(P1, P2, P3) => List[P1, P2, P3]
   */
	public List<Expression<T>> split() {
		List<Expression<T>> list = new ArrayList<>();
		list.add(this);
		return list;
	}
	
	public abstract String toString();
}
