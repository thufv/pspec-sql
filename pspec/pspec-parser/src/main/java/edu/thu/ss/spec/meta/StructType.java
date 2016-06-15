package edu.thu.ss.spec.meta;


public class StructType extends ComplexType<String> {

	@Override
	protected String getSelectorName() {
		return "Field";
	}

	@Override
	protected String getTypeName() {
		return "Struct Type";
	}
}
