package edu.thu.ss.spec.meta;

import java.util.Map;
import java.util.Set;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Policy;

/**
 * interface for meta registry
 * @author luochen
 *
 */
public interface MetaRegistry {

	/**
	 * lookup {@link DataCategory} for column in table and database
	 * @param database
	 * @param table
	 * @param column
	 * @return {@link DataCategory}
	 */
	public BaseType lookup(String database, String table, String column);

	/**
	 * lookup conditional {@link DataCategory} for column in table and database
	 * @param database
	 * @param table
	 * @param column
	 * @return {@link JoinCondition} for each {@link DataCategory}
	 */
	public Map<JoinCondition, BaseType> conditionalLookup(String database, String table, String column);

	/**
	 * given a {@link DataCategory} and udf, lookup the corresponding {@link DesensitizeOperation}
	 * @param data
	 * @param udf
	 * @param database
	 * @param table
	 * @param column
	 * @return {@link DesensitizeOperation}
	 */
	public DesensitizeOperation lookup(DataCategory data, String udf, String database, String table,
			String column);

	public Map<String, Database> getDatabases();

	public Policy getPolicy();

	/**
	 * whether the {@link MetaRegistry} is applicable for table in database.
	 * @param database
	 * @param table
	 * @return isApplicable
	 */
	public boolean applicable(String database, String table);

	/**
	 * returns the scope of {@link MetaRegistry}
	 * @return database -> {table}
	 */
	public Map<String, Set<String>> getScope();
}
