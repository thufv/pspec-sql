package org.apache.spark.sql.hive.execution;
/**
 * Create table and insert the query result into it.
 * @param database the database name of the new relation
 * @param tableName the table name of the new relation
 * @param query the query whose result will be insert into the new relation
 * @param allowExisting allow continue working if it's already exists, otherwise
 *                      raise exception
 * @param desc the CreateTableDesc, which may contains serde, storage handler etc.
 * <p>
 */
public  class CreateTableAsSelect extends org.apache.spark.sql.catalyst.plans.logical.Command implements org.apache.spark.sql.execution.RunnableCommand, scala.Product, scala.Serializable {
  public  java.lang.String database () { throw new RuntimeException(); }
  public  java.lang.String tableName () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan query () { throw new RuntimeException(); }
  public  boolean allowExisting () { throw new RuntimeException(); }
  public  scala.Option<org.apache.hadoop.hive.ql.plan.CreateTableDesc> desc () { throw new RuntimeException(); }
  // not preceding
  public   CreateTableAsSelect (java.lang.String database, java.lang.String tableName, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan query, boolean allowExisting, scala.Option<org.apache.hadoop.hive.ql.plan.CreateTableDesc> desc) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.Row> run (org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  public  java.lang.String argString () { throw new RuntimeException(); }
}
