package edu.thu.ss.spec.meta;

public class ArrayType extends ComplexType<Integer> {

	@Override
	protected String getTypeName() {
		return "Array Type";
	}

	@Override
	protected String getSelectorName() {
		return "Index";
	}

}
