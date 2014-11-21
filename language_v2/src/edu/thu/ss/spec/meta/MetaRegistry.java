package edu.thu.ss.spec.meta;

import java.util.Map;
import java.util.Set;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Policy;

public interface MetaRegistry {

	public DataCategory lookup(String database, String table, String column);

	public Map<JoinCondition, DataCategory> conditionalLookup(String database, String table, String column);

	public DesensitizeOperation lookup(DataCategory data, String udf, String database, String table, String column);

	public Map<String, Database> getDatabases();

	public Policy getPolicy();

	public boolean applicable(String database, String table);

	public Map<String, Set<String>> getScope();
}
