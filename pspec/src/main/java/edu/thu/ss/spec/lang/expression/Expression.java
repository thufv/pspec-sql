package edu.thu.ss.spec.lang.expression;

import java.util.Set;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.Parsable;
import edu.thu.ss.spec.lang.pojo.Writable;

public abstract class Expression<T> implements Parsable, Writable{
	public enum ExpressionTypes {
		binaryComparison, binaryPredicate, function, term
	}
	
	public ExpressionTypes ExpressionType;
	public Set<T> dataSet;
	public abstract <T> Set<T> getDataSet();
	public abstract String toString();
	
	public ExpressionTypes getExpressionType() {
		return ExpressionType;
	}
}
