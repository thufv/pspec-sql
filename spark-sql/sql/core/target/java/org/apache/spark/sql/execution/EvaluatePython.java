package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 * Evaluates a {@link PythonUDF}, appending the result to the end of the input tuple.
 */
public  class EvaluatePython extends org.apache.spark.sql.catalyst.plans.logical.UnaryNode implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.execution.PythonUDF udf () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child () { throw new RuntimeException(); }
  // not preceding
  public   EvaluatePython (org.apache.spark.sql.execution.PythonUDF udf, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan child) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.AttributeReference resultAttribute () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
}
