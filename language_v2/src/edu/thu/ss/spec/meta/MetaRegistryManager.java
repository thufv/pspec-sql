package edu.thu.ss.spec.meta;

public class MetaRegistryManager {

	private static MetaRegistry registry = null;

	public static synchronized MetaRegistry get() {
		return registry;
	}

	public static synchronized void set(MetaRegistry registry) {
		MetaRegistryManager.registry = registry;
	}

}
