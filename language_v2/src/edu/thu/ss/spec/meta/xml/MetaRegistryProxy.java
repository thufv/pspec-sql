package edu.thu.ss.spec.meta.xml;

import java.util.Map;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.meta.JoinCondition;
import edu.thu.ss.spec.meta.MetaRegistry;

public class MetaRegistryProxy implements MetaRegistry {

	private MetaRegistry registry = null;

	public MetaRegistryProxy(MetaRegistry registry) {
		this.registry = registry;
	}

	@Override
	public DataCategory lookup(String databaseName, String tableName, String columnName) {
		return registry.lookup(databaseName.toLowerCase(), tableName.toLowerCase(), columnName.toLowerCase());
	}

	@Override
	public Map<JoinCondition, DataCategory> conditionalLookup(String databaseName, String tableName, String columnName) {
		return registry.conditionalLookup(databaseName.toLowerCase(), tableName.toLowerCase(), columnName.toLowerCase());
	}

	@Override
	public DesensitizeOperation lookup(DataCategory data, String udf, String databaseName, String tableName,
			String columnName) {
		return registry.lookup(data, udf.toLowerCase(), databaseName.toLowerCase(), tableName.toLowerCase(),
				columnName.toLowerCase());
	}

	@Override
	public UserCategory currentUser() {
		return registry.currentUser();
	}

	@Override
	public String toString() {
		return registry.toString();
	}

}
