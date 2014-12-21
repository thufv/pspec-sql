package edu.thu.ss.spec.meta;

public abstract class BaseType {

	protected PrimitiveType[] primitives;

	public abstract PrimitiveType[] toPrimitives();

	public abstract String toString(int l);
}
