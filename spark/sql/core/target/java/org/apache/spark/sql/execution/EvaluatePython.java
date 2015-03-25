package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 * Evaluates a {@link PythonUDF}, appending the result to the end of the input tuple.
 */
public  class EvaluatePython extends org.apache.spark.sql.catalyst.plans.logical.UnaryNode implements scala.Product, scala.Serializable {
  static public  org.apache.spark.sql.execution.EvaluatePython apply (org.apache.spark.sql.execution.PythonUDF udf, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child) { throw new RuntimeException(); }
  /**
   * Helper for converting a Scala object to a java suitable for pyspark serialization.
   */
  static public  Object toJava (Object obj, org.apache.spark.sql.types.DataType dataType) { throw new RuntimeException(); }
  /**
   * Convert Row into Java Array (for pickled into Python)
   */
  static public  java.lang.Object[] rowToArray (org.apache.spark.sql.Row row, scala.collection.Seq<org.apache.spark.sql.types.DataType> fields) { throw new RuntimeException(); }
  static public  Object fromJava (Object obj, org.apache.spark.sql.types.DataType dataType) { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.PythonUDF udf () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.AttributeReference resultAttribute () { throw new RuntimeException(); }
  // not preceding
  public   EvaluatePython (org.apache.spark.sql.execution.PythonUDF udf, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child, org.apache.spark.sql.catalyst.expressions.AttributeReference resultAttribute) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.AttributeSet references () { throw new RuntimeException(); }
}
