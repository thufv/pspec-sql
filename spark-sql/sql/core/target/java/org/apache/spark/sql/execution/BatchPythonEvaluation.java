package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 * Uses PythonRDD to evaluate a {@link PythonUDF}, one partition of tuples at a time.  The input
 * data is cached and zipped with the result of the udf evaluation.
 */
public  class BatchPythonEvaluation extends org.apache.spark.sql.execution.SparkPlan implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.execution.PythonUDF udf () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.SparkPlan child () { throw new RuntimeException(); }
  // not preceding
  public   BatchPythonEvaluation (org.apache.spark.sql.execution.PythonUDF udf, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, org.apache.spark.sql.execution.SparkPlan child) { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.execution.SparkPlan> children () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
}
