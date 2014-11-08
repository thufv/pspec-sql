package org.apache.spark.sql.hive;
private abstract interface HiveStrategies {
  public abstract  org.apache.spark.sql.hive.HiveContext hiveContext () ;
  // no position
  public  class ParquetConversion extends org.apache.spark.sql.catalyst.planning.QueryPlanner.Strategy {
    /**
     * :: Experimental ::
     * Finds table scans that would use the Hive SerDe and replaces them with our own native parquet
     * table scan operator.
     * <p>
     * TODO: Much of this logic is duplicated in HiveTableScan.  Ideally we would do some refactoring
     * but since this is after the code freeze for 1.1 all logic is here to minimize disruption.
     * <p>
     * Other issues:
     *  - Much of this logic assumes case insensitive resolution.
     */
    public   ParquetConversion () { throw new RuntimeException(); }
    public  class LogicalPlanHacks {
      public   LogicalPlanHacks (org.apache.spark.sql.SchemaRDD s) { throw new RuntimeException(); }
      public  org.apache.spark.sql.SchemaRDD lowerCase () { throw new RuntimeException(); }
      public  org.apache.spark.sql.SchemaRDD addPartitioningAttributes (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attrs) { throw new RuntimeException(); }
    }
    public  class PhysicalPlanHacks {
      public   PhysicalPlanHacks (org.apache.spark.sql.execution.SparkPlan originalPlan) { throw new RuntimeException(); }
      public  org.apache.spark.sql.execution.OutputFaker fakeOutput (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> newOutput) { throw new RuntimeException(); }
    }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.hive.HiveStrategies.ParquetConversion$ ParquetConversion () ;
  // no position
  public  class Scripts extends org.apache.spark.sql.catalyst.planning.QueryPlanner.Strategy {
    public   Scripts () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.hive.HiveStrategies.Scripts$ Scripts () ;
  // no position
  public  class DataSinks extends org.apache.spark.sql.catalyst.planning.QueryPlanner.Strategy {
    public   DataSinks () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.hive.HiveStrategies.DataSinks$ DataSinks () ;
  // no position
  public  class HiveTableScans extends org.apache.spark.sql.catalyst.planning.QueryPlanner.Strategy {
    /**
     * Retrieves data using a HiveTableScan.  Partition pruning predicates are also detected and
     * applied.
     */
    public   HiveTableScans () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // not preceding
  public  org.apache.spark.sql.hive.HiveStrategies.HiveTableScans$ HiveTableScans () ;
  public  class HiveCommandStrategy extends org.apache.spark.sql.catalyst.planning.QueryPlanner.Strategy implements scala.Product, scala.Serializable {
    public  org.apache.spark.sql.hive.HiveContext context () { throw new RuntimeException(); }
    // not preceding
    public   HiveCommandStrategy (org.apache.spark.sql.hive.HiveContext context) { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.execution.SparkPlan> apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // no position
  public  class HiveCommandStrategy extends scala.runtime.AbstractFunction1<org.apache.spark.sql.hive.HiveContext, org.apache.spark.sql.hive.HiveStrategies.HiveCommandStrategy> implements scala.Serializable {
    // not preceding
    public   HiveCommandStrategy () { throw new RuntimeException(); }
  }
}
