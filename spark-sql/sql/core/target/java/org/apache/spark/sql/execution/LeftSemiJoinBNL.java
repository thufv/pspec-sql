package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 * Using BroadcastNestedLoopJoin to calculate left semi join result when there's no join keys
 * for hash join.
 */
public  class LeftSemiJoinBNL extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.BinaryNode, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.execution.SparkPlan streamed () { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.SparkPlan broadcast () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.sql.catalyst.expressions.Expression> condition () { throw new RuntimeException(); }
  // not preceding
  public   LeftSemiJoinBNL (org.apache.spark.sql.execution.SparkPlan streamed, org.apache.spark.sql.execution.SparkPlan broadcast, scala.Option<org.apache.spark.sql.catalyst.expressions.Expression> condition) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.physical.Partitioning outputPartitioning () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  /** The Streamed Relation */
  public  org.apache.spark.sql.execution.SparkPlan left () { throw new RuntimeException(); }
  /** The Broadcast relation */
  public  org.apache.spark.sql.execution.SparkPlan right () { throw new RuntimeException(); }
  public  scala.Function1<org.apache.spark.sql.catalyst.expressions.Row, java.lang.Object> boundCondition () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () { throw new RuntimeException(); }
}
