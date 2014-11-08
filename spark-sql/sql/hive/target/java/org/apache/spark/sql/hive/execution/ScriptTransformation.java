package org.apache.spark.sql.hive.execution;
/**
 * :: DeveloperApi ::
 * Transforms the input by forking and running the specified script.
 * <p>
 * @param input the set of expression that should be passed to the script.
 * @param script the command that should be executed.
 * @param output the attributes that are produced by the script.
 */
public  class ScriptTransformation extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.UnaryNode, scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> input () { throw new RuntimeException(); }
  public  java.lang.String script () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.SparkPlan child () { throw new RuntimeException(); }
  // not preceding
  public   ScriptTransformation (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> input, java.lang.String script, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, org.apache.spark.sql.execution.SparkPlan child, org.apache.spark.sql.hive.HiveContext sc) { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.hive.HiveContext> otherCopyArgs () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
}
