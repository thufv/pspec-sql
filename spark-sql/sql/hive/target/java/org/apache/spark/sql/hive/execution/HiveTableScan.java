package org.apache.spark.sql.hive.execution;
/**
 * :: DeveloperApi ::
 * The Hive table scan operator.  Column and partition pruning are both handled.
 * <p>
 * @param attributes Attributes to be fetched from the Hive table.
 * @param relation The Hive table be be scanned.
 * @param partitionPruningPred An optional partition pruning predicate for partitioned table.
 */
public  class HiveTableScan extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.LeafNode, scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes () { throw new RuntimeException(); }
  public  org.apache.spark.sql.hive.MetastoreRelation relation () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.sql.catalyst.expressions.Expression> partitionPruningPred () { throw new RuntimeException(); }
  public  org.apache.spark.sql.hive.HiveContext context () { throw new RuntimeException(); }
  // not preceding
  public   HiveTableScan (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes, org.apache.spark.sql.hive.MetastoreRelation relation, scala.Option<org.apache.spark.sql.catalyst.expressions.Expression> partitionPruningPred, org.apache.spark.sql.hive.HiveContext context) { throw new RuntimeException(); }
  private  Object castFromString (java.lang.String value, org.apache.spark.sql.catalyst.types.DataType dataType) { throw new RuntimeException(); }
  private  void addColumnMetadataToConf (org.apache.hadoop.hive.conf.HiveConf hiveConf) { throw new RuntimeException(); }
  /**
   * Prunes partitions not involve the query plan.
   * <p>
   * @param partitions All partitions of the relation.
   * @return Partitions that are involved in the query plan.
   */
  private  scala.collection.Seq<org.apache.hadoop.hive.ql.metadata.Partition> prunePartitions (scala.collection.Seq<org.apache.hadoop.hive.ql.metadata.Partition> partitions) { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
}
