package org.apache.spark.sql.execution;
private abstract class SparkStrategies extends org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan> {
  public   SparkStrategies () { throw new RuntimeException(); }
  // no position
  public  class LeftSemiJoin extends org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan>.Strategy implements org.apache.spark.sql.catalyst.expressions.PredicateHelper {
    public   LeftSemiJoin () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.execution.SparkStrategies.LeftSemiJoin$ LeftSemiJoin () { throw new RuntimeException(); }
  // no position
  public  class HashJoin extends org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan>.Strategy implements org.apache.spark.sql.catalyst.expressions.PredicateHelper {
    /**
     * Uses the ExtractEquiJoinKeys pattern to find joins where at least some of the predicates can be
     * evaluated by matching hash keys.
     * <p>
     * This strategy applies a simple optimization based on the estimates of the physical sizes of
     * the two join sides.  When planning a {@link execution.BroadcastHashJoin}, if one side has an
     * estimated physical size smaller than the user-settable threshold
     * {@link org.apache.spark.sql.SQLConf.AUTO_BROADCASTJOIN_THRESHOLD}, the planner would mark it as the
     * ''build'' relation and mark the other relation as the ''stream'' side.  The build table will be
     * ''broadcasted'' to all of the executors involved in the join, as a
     * {@link org.apache.spark.broadcast.Broadcast} object.  If both estimates exceed the threshold, they
     * will instead be used to decide the build side in a {@link execution.ShuffledHashJoin}.
     */
    public   HashJoin () { throw new RuntimeException(); }
    private  scala.collection.immutable.List<org.apache.spark.sql.execution.SparkPlan> makeBroadcastHashJoin (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> leftKeys, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> rightKeys, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan left, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan right, scala.Option<org.apache.spark.sql.catalyst.expressions.Expression> condition, org.apache.spark.sql.execution.BuildSide side) { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.execution.SparkStrategies.HashJoin$ HashJoin () { throw new RuntimeException(); }
  // no position
  public  class HashAggregation extends org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan>.Strategy {
    public   HashAggregation () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
    public  boolean canBeCodeGened (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.AggregateExpression> aggs) { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.AggregateExpression> allAggregates (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> exprs) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.execution.SparkStrategies.HashAggregation$ HashAggregation () { throw new RuntimeException(); }
  // no position
  public  class BroadcastNestedLoopJoin extends org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan>.Strategy {
    public   BroadcastNestedLoopJoin () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.execution.SparkStrategies.BroadcastNestedLoopJoin$ BroadcastNestedLoopJoin () { throw new RuntimeException(); }
  // no position
  public  class CartesianProduct extends org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan>.Strategy {
    public   CartesianProduct () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.execution.SparkStrategies.CartesianProduct$ CartesianProduct () { throw new RuntimeException(); }
  protected  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> singleRowRdd () { throw new RuntimeException(); }
  // no position
  public  class TakeOrdered extends org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan>.Strategy {
    public   TakeOrdered () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.execution.SparkStrategies.TakeOrdered$ TakeOrdered () { throw new RuntimeException(); }
  // no position
  public  class ParquetOperations extends org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan>.Strategy {
    public   ParquetOperations () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.execution.SparkStrategies.ParquetOperations$ ParquetOperations () { throw new RuntimeException(); }
  // no position
  public  class InMemoryScans extends org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan>.Strategy {
    public   InMemoryScans () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.execution.SparkStrategies.InMemoryScans$ InMemoryScans () { throw new RuntimeException(); }
  // no position
  public  class BasicOperators extends org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan>.Strategy {
    public   BasicOperators () { throw new RuntimeException(); }
    public  int numPartitions () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.execution.SparkStrategies.BasicOperators$ BasicOperators () { throw new RuntimeException(); }
  public  class CommandStrategy extends org.apache.spark.sql.catalyst.planning.QueryPlanner<org.apache.spark.sql.execution.SparkPlan>.Strategy implements scala.Product, scala.Serializable {
    public  org.apache.spark.sql.SQLContext context () { throw new RuntimeException(); }
    // not preceding
    public   CommandStrategy (org.apache.spark.sql.SQLContext context) { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // no position
  public  class CommandStrategy extends scala.runtime.AbstractFunction1<org.apache.spark.sql.SQLContext, org.apache.spark.sql.execution.SparkStrategies.CommandStrategy> implements scala.Serializable {
    public   CommandStrategy () { throw new RuntimeException(); }
  }
}
