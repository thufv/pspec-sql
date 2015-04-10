package edu.thu.ss.spec.meta;

import edu.thu.ss.spec.global.MetaManager;

public class CompositeType extends ComplexType<String> {
	@Override
	public void add(String k, BaseType subtype) {
		k = k.toLowerCase();
		MetaManager.addExtractOperation(k);
		super.add(k, subtype);
	}

	@Override
	protected String getSelectorName() {
		return "Extract Operation";
	}

	@Override
	protected String getTypeName() {
		return "Composite Type";
	}

}
