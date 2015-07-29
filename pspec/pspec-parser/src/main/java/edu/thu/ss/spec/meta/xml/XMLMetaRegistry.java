package edu.thu.ss.spec.meta.xml;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.meta.BaseType;
import edu.thu.ss.spec.meta.Column;
import edu.thu.ss.spec.meta.ConditionalColumn;
import edu.thu.ss.spec.meta.Database;
import edu.thu.ss.spec.meta.JoinCondition;
import edu.thu.ss.spec.meta.MetaRegistry;
import edu.thu.ss.spec.meta.Table;
import edu.thu.ss.spec.meta.User;

public class XMLMetaRegistry implements MetaRegistry {
	private Info info = new Info();
	protected URI policyLocation;

	private Map<String, Database> databases = new HashMap<>();
	private Map<String, User> userList = new HashMap<>();
	private Policy policy = null;
	private Map<String, Set<String>> scope = null;

	private URI path;

	public XMLMetaRegistry() {

	}

	public XMLMetaRegistry(String id) {
		info = new Info();
		info.setId(id);
	}

	@Override
	public boolean applicable(String databaseName, String tableName) {
		Table table = lookupTable(databaseName, tableName);
		return table != null;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public URI getPath() {
		return path;
	}

	public void setPath(URI path) {
		this.path = path;
	}

	public URI getPolicyLocation() {
		return policyLocation;
	}

	public void setPolicyLocation(URI policyLocation) {
		this.policyLocation = policyLocation;
	}

	@Override
	public Map<String, Set<String>> getScope() {
		if (scope == null) {
			scope = new HashMap<>();
			for (Database db : databases.values()) {
				Set<String> set = scope.get(db.getName());
				if (set == null) {
					set = new HashSet<>();
					scope.put(db.getName(), set);
				}
				set.addAll(db.getTables().keySet());
			}
		}
		return scope;
	}

	@Override
	public Policy getPolicy() {
		return policy;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public void addDatabase(Database database) {
		this.databases.put(database.getName(), database);
	}

	void addUser(User user) {
		this.userList.put(user.getName(), user);
	}

	@Override
	public Map<String, Database> getDatabases() {
		return databases;
	}
	
	public Database getDatabase(String databaseName) {
		return databases.get(databaseName);
	}

	@Override
	public BaseType lookup(String databaseName, String tableName, String columnName) {
		Column column = lookupColumn(databaseName, tableName, columnName);
		if (column == null) {
			return null;
		} else {
			return column.getType();
		}
	}

	@Override
	public Integer getMultiplicity(String databaseName, String tableName, String columnName) {
		Column column = lookupColumn(databaseName, tableName, columnName);
		if (column == null) {
			return null;
		} else {
			return column.getMultiplicity();
		}
	}

	@Override
	public boolean isJoinable(String databaseName, String tableName, String columnName) {
		Column column = lookupColumn(databaseName, tableName, columnName);
		if (column == null) {
			return false;
		} else {
			return column.isJoinable();
		}
	}

	private Table lookupTable(String databaseName, String tableName) {
		Database database = databases.get(databaseName);
		if (database == null) {
			return null;
		}
		return database.getTable(tableName);
	}

	@Override
	public DesensitizeOperation lookup(DataCategory data, String udf, String databaseName,
			String tableName, String columnName) {
		return null;
	}

	@Override
	public Map<JoinCondition, BaseType> conditionalLookup(String databaseName, String tableName,
			String columnName) {
		ConditionalColumn column = lookupConditionalColumn(databaseName, tableName, columnName);
		if (column == null) {
			return null;
		}
		return column.getTypes();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String ul : userList.keySet()) {
			sb.append(userList.get(ul));
			sb.append('\n');
		}
		for (String db : databases.keySet()) {
			sb.append(databases.get(db));
			sb.append('\n');
		}
		return sb.toString();
	}

	private Column lookupColumn(String databaseName, String tableName, String columnName) {
		Table table = lookupTable(databaseName, tableName);
		if (table == null) {
			return null;
		}
		return table.getColumn(columnName);
	}

	private ConditionalColumn lookupConditionalColumn(String databaseName, String tableName,
			String columnName) {
		Table table = lookupTable(databaseName, tableName);
		if (table == null) {
			return null;
		}
		return table.getConditionalColumn(columnName);
	}

}
