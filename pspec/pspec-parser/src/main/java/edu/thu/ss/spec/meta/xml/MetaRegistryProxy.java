package edu.thu.ss.spec.meta.xml;

import java.util.Map;
import java.util.Set;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.meta.BaseType;
import edu.thu.ss.spec.meta.Database;
import edu.thu.ss.spec.meta.JoinCondition;
import edu.thu.ss.spec.meta.MetaRegistry;

public class MetaRegistryProxy implements MetaRegistry {

	private MetaRegistry registry = null;

	public MetaRegistryProxy(MetaRegistry registry) {
		this.registry = registry;
	}

	@Override
	public Map<String, Set<String>> getScope() {
		return registry.getScope();
	}

	@Override
	public Policy getPolicy() {
		return registry.getPolicy();
	}

	@Override
	public boolean applicable(String database, String table) {
		return registry.applicable(database.toLowerCase(), table.toLowerCase());
	}

	@Override
	public Map<String, Database> getDatabases() {
		return registry.getDatabases();
	}

	@Override
	public BaseType lookup(String databaseName, String tableName, String columnName) {
		return registry.lookup(databaseName.toLowerCase(), tableName.toLowerCase(),
				columnName.toLowerCase());
	}

	@Override
	public Integer getMultiplicity(String database, String table, String column) {
		return registry.getMultiplicity(database.toLowerCase(), table.toLowerCase(),
				column.toLowerCase());
	}

	@Override
	public boolean isJoinable(String database, String table, String column) {
		return registry.isJoinable(database.toLowerCase(), table.toLowerCase(), column.toLowerCase());
	}

	@Override
	public Map<JoinCondition, BaseType> conditionalLookup(String databaseName, String tableName,
			String columnName) {
		return registry.conditionalLookup(databaseName.toLowerCase(), tableName.toLowerCase(),
				columnName.toLowerCase());
	}

	@Override
	public DesensitizeOperation lookup(DataCategory data, String udf, String databaseName,
			String tableName, String columnName) {
		return registry.lookup(data, udf.toLowerCase(), databaseName.toLowerCase(),
				tableName.toLowerCase(), columnName.toLowerCase());
	}

	@Override
	public String toString() {
		return registry.toString();
	}

}
