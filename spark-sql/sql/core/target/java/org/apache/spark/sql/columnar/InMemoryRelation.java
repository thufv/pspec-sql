package org.apache.spark.sql.columnar;
private  class InMemoryRelation extends org.apache.spark.sql.catalyst.plans.logical.LogicalPlan implements org.apache.spark.sql.catalyst.analysis.MultiInstanceRelation, scala.Product, scala.Serializable {
  static public  org.apache.spark.sql.columnar.InMemoryRelation apply (boolean useCompression, int batchSize, org.apache.spark.sql.execution.SparkPlan child) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  boolean useCompression () { throw new RuntimeException(); }
  public  int batchSize () { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.SparkPlan child () { throw new RuntimeException(); }
  private  org.apache.spark.rdd.RDD<java.nio.ByteBuffer[]> _cachedColumnBuffers () { throw new RuntimeException(); }
  // not preceding
  public   InMemoryRelation (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, boolean useCompression, int batchSize, org.apache.spark.sql.execution.SparkPlan child, org.apache.spark.rdd.RDD<java.nio.ByteBuffer[]> _cachedColumnBuffers) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan.Statistics statistics () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.runtime.Nothing$> children () { throw new RuntimeException(); }
  public  org.apache.spark.sql.columnar.InMemoryRelation newInstance () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<java.nio.ByteBuffer[]> cachedColumnBuffers () { throw new RuntimeException(); }
}
