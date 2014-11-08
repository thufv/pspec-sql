package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 */
public abstract class SparkPlan extends org.apache.spark.sql.catalyst.plans.QueryPlan<org.apache.spark.sql.execution.SparkPlan> implements org.apache.spark.Logging, scala.Serializable {
  static protected  java.lang.ThreadLocal<org.apache.spark.sql.SQLContext> currentContext () { throw new RuntimeException(); }
  public   SparkPlan () { throw new RuntimeException(); }
  /**
   * A handle to the SQL Context that was used to create this plan.   Since many operators need
   * access to the sqlContext for RDD operations or configuration this field is automatically
   * populated by the query planning infrastructure.
   */
  protected  org.apache.spark.sql.SQLContext sqlContext () { throw new RuntimeException(); }
  protected  org.apache.spark.SparkContext sparkContext () { throw new RuntimeException(); }
  public  boolean codegenEnabled () { throw new RuntimeException(); }
  /** Overridden make copy also propogates sqlContext to copied plan. */
  public  org.apache.spark.sql.execution.SparkPlan makeCopy (java.lang.Object[] newArgs) { throw new RuntimeException(); }
  /** Specifies how data is partitioned across different nodes in the cluster. */
  public  org.apache.spark.sql.catalyst.plans.physical.Partitioning outputPartitioning () { throw new RuntimeException(); }
  /** Specifies any partition requirements on the input data for this operator. */
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.plans.physical.Distribution> requiredChildDistribution () { throw new RuntimeException(); }
  /**
   * Runs this query returning the result as an RDD.
   */
  public abstract  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> execute () ;
  /**
   * Runs this query returning the result as an array.
   */
  public  org.apache.spark.sql.catalyst.expressions.Row[] executeCollect () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.expressions.Projection newProjection (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
  protected  scala.Function0<org.apache.spark.sql.catalyst.expressions.MutableProjection> newMutableProjection (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
  protected  scala.Function1<org.apache.spark.sql.catalyst.expressions.Row, java.lang.Object> newPredicate (org.apache.spark.sql.catalyst.expressions.Expression expression, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
  protected  scala.math.Ordering<org.apache.spark.sql.catalyst.expressions.Row> newOrdering (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> order, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
}
