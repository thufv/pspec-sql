package org.apache.spark.sql.execution;
// no position
public  class EvaluatePython$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final EvaluatePython$ MODULE$ = null;
  public   EvaluatePython$ () { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.EvaluatePython apply (org.apache.spark.sql.execution.PythonUDF udf, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child) { throw new RuntimeException(); }
  /**
   * Helper for converting a Scala object to a java suitable for pyspark serialization.
   */
  public  Object toJava (Object obj, org.apache.spark.sql.types.DataType dataType) { throw new RuntimeException(); }
  /**
   * Convert Row into Java Array (for pickled into Python)
   */
  public  java.lang.Object[] rowToArray (org.apache.spark.sql.Row row, scala.collection.Seq<org.apache.spark.sql.types.DataType> fields) { throw new RuntimeException(); }
  public  Object fromJava (Object obj, org.apache.spark.sql.types.DataType dataType) { throw new RuntimeException(); }
}
