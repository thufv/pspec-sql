package edu.thu.ss.spec.meta;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.UserCategory;

public interface MetaRegistry {

	public DataCategory lookup(String database, String table, String column);

	public DesensitizeOperation lookup(String udf, DataCategory data, String database, String table, String column);

	public UserCategory currentUser();

}
