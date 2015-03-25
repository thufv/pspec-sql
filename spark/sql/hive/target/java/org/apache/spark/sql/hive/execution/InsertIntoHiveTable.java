package org.apache.spark.sql.hive.execution;
public  class InsertIntoHiveTable extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.UnaryNode, org.apache.spark.sql.hive.HiveInspectors, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.hive.MetastoreRelation table () { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<java.lang.String, scala.Option<java.lang.String>> partition () { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.SparkPlan child () { throw new RuntimeException(); }
  public  boolean overwrite () { throw new RuntimeException(); }
  // not preceding
  public   InsertIntoHiveTable (org.apache.spark.sql.hive.MetastoreRelation table, scala.collection.immutable.Map<java.lang.String, scala.Option<java.lang.String>> partition, org.apache.spark.sql.execution.SparkPlan child, boolean overwrite) { throw new RuntimeException(); }
  public  org.apache.spark.sql.hive.HiveContext sc () { throw new RuntimeException(); }
  public  java.lang.Class<? extends org.apache.hadoop.io.Writable> outputClass () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.Context hiveContext () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.metadata.Hive db () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.serde2.Serializer newSerializer (org.apache.hadoop.hive.ql.plan.TableDesc tableDesc) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  void saveAsHiveFile (org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> rdd, java.lang.Class<?> valueClass, org.apache.spark.sql.hive.ShimFileSinkDesc fileSinkConf, org.apache.spark.SerializableWritable<org.apache.hadoop.mapred.JobConf> conf, org.apache.spark.sql.hive.SparkHiveWriterContainer writerContainer) { throw new RuntimeException(); }
  /**
   * Inserts all the rows in the table into Hive.  Row objects are properly serialized with the
   * <code>org.apache.hadoop.hive.serde2.SerDe</code> and the
   * <code>org.apache.hadoop.mapred.OutputFormat</code> provided by the table definition.
   * <p>
   * Note: this is run once and then kept to avoid double insertions.
   */
  protected  scala.collection.Seq<org.apache.spark.sql.Row> sideEffectResult () { throw new RuntimeException(); }
  public  org.apache.spark.sql.Row[] executeCollect () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> execute () { throw new RuntimeException(); }
}
