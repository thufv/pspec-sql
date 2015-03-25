package org.apache.spark.sql.hive;
public  class MetastoreRelation extends org.apache.spark.sql.catalyst.plans.logical.LeafNode implements scala.Product, scala.Serializable {
  public  class SchemaAttribute {
    public   SchemaAttribute (org.apache.hadoop.hive.metastore.api.FieldSchema f) { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.expressions.AttributeReference toAttribute () { throw new RuntimeException(); }
  }
  public  java.lang.String databaseName () { throw new RuntimeException(); }
  public  java.lang.String tableName () { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> alias () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.metastore.api.Table table () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.hadoop.hive.metastore.api.Partition> partitions () { throw new RuntimeException(); }
  // not preceding
  public   MetastoreRelation (java.lang.String databaseName, java.lang.String tableName, scala.Option<java.lang.String> alias, org.apache.hadoop.hive.metastore.api.Table table, scala.collection.Seq<org.apache.hadoop.hive.metastore.api.Partition> partitions, org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.ql.metadata.Table hiveQlTable () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.hadoop.hive.ql.metadata.Partition> hiveQlPartitions () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.Statistics statistics () { throw new RuntimeException(); }
  /** Only compare database and tablename, not alias. */
  public  boolean sameResult (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.ql.plan.TableDesc tableDesc () { throw new RuntimeException(); }
  public  scala.collection.mutable.Buffer<org.apache.spark.sql.catalyst.expressions.AttributeReference> partitionKeys () { throw new RuntimeException(); }
  /** Non-partitionKey attributes */
  public  scala.collection.mutable.Buffer<org.apache.spark.sql.catalyst.expressions.AttributeReference> attributes () { throw new RuntimeException(); }
  public  scala.collection.mutable.Buffer<org.apache.spark.sql.catalyst.expressions.AttributeReference> output () { throw new RuntimeException(); }
  /** An attribute map that can be used to lookup original attributes based on expression id. */
  public  org.apache.spark.sql.catalyst.expressions.AttributeMap<org.apache.spark.sql.catalyst.expressions.AttributeReference> attributeMap () { throw new RuntimeException(); }
  /** An attribute map for determining the ordinal for non-partition columns. */
  public  org.apache.spark.sql.catalyst.expressions.AttributeMap<java.lang.Object> columnOrdinals () { throw new RuntimeException(); }
  /**
   * added by luochen
   */
  public  edu.thu.ss.spec.lang.pojo.Policy calculateLabels () { throw new RuntimeException(); }
}
