package edu.thu.ss.spec.global;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.thu.ss.spec.meta.MetaRegistry;

public class MetaManager {

	private static List<MetaRegistry> registries = new LinkedList<>();

	private static Map<String, Map<String, MetaRegistry>> registryIndex = new HashMap<>();

	public static synchronized MetaRegistry get(String database, String table) {
		Map<String, MetaRegistry> map = registryIndex.get(database);
		if (map != null) {
			return map.get(table);
		} else {
			return null;
		}
	}

	public static synchronized void add(MetaRegistry registry) {
		registries.add(registry);
		Map<String, Set<String>> scope = registry.getScope();
		for (Entry<String, Set<String>> e : scope.entrySet()) {
			Map<String, MetaRegistry> index = registryIndex.get(e.getKey());
			if (index == null) {
				index = new HashMap<>();
				registryIndex.put(e.getKey(), index);
			}
			for (String table : e.getValue()) {
				if (index.containsKey(table)) {
					throw new RuntimeException("database: " + e.getKey() + " table: " + table + " is already defined.");
				}
				index.put(table, registry);
			}
		}
	}

	public static synchronized boolean isDefined(String database, String table) {
		return get(database, table) != null;
	}

}
