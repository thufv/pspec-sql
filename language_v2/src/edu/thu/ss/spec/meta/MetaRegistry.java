package edu.thu.ss.spec.meta;

import java.util.Map;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.UserCategory;

public interface MetaRegistry {

	public DataCategory lookup(String database, String table, String column);

	public Map<JoinCondition, DataCategory> conditionalLookup(String database, String table, String column);

	public DesensitizeOperation lookup(DataCategory data, String udf, String database, String table, String column);

	public UserCategory currentUser();

	public Map<String, Database> getDatabases();

}
