package edu.thu.ss.spec.meta;


public abstract class BaseType {

	protected PrimitiveType[] primitives;

	protected BaseType[] subTypesArray;

	public abstract PrimitiveType[] toPrimitives();

	public abstract BaseType[] toSubTypes();

	public abstract String toString(int l);
}
