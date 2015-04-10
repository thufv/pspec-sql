package edu.thu.ss.spec.meta;

public class MapType extends ComplexType<String> {

	@Override
	protected String getSelectorName() {
		return "Key";
	}

	@Override
	protected String getTypeName() {
		return "Map Type";
	}

}
