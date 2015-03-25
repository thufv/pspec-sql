package org.apache.spark.sql.execution.joins;
/**
 * :: DeveloperApi ::
 * Performs a hash based outer join for two child relations by shuffling the data using
 * the join keys. This operator requires loading the associated partition in both side into memory.
 */
public  class HashOuterJoin extends org.apache.spark.sql.execution.SparkPlan implements org.apache.spark.sql.execution.BinaryNode, scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> leftKeys () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> rightKeys () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.JoinType joinType () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.sql.catalyst.expressions.Expression> condition () { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.SparkPlan left () { throw new RuntimeException(); }
  public  org.apache.spark.sql.execution.SparkPlan right () { throw new RuntimeException(); }
  // not preceding
  public   HashOuterJoin (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> leftKeys, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> rightKeys, org.apache.spark.sql.catalyst.plans.JoinType joinType, scala.Option<org.apache.spark.sql.catalyst.expressions.Expression> condition, org.apache.spark.sql.execution.SparkPlan left, org.apache.spark.sql.execution.SparkPlan right) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.physical.Partitioning outputPartitioning () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.catalyst.plans.physical.ClusteredDistribution> requiredChildDistribution () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.sql.Row> DUMMY_LIST () { throw new RuntimeException(); }
  private  scala.collection.Seq<org.apache.spark.sql.Row> EMPTY_LIST () { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.expressions.GenericRow leftNullRow () { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.expressions.GenericRow rightNullRow () { throw new RuntimeException(); }
  private  scala.Function1<org.apache.spark.sql.Row, java.lang.Object> boundCondition () { throw new RuntimeException(); }
  private  scala.collection.Iterator<org.apache.spark.sql.Row> leftOuterIterator (org.apache.spark.sql.Row key, org.apache.spark.sql.catalyst.expressions.JoinedRow joinedRow, scala.collection.Iterable<org.apache.spark.sql.Row> rightIter) { throw new RuntimeException(); }
  private  scala.collection.Iterator<org.apache.spark.sql.Row> rightOuterIterator (org.apache.spark.sql.Row key, scala.collection.Iterable<org.apache.spark.sql.Row> leftIter, org.apache.spark.sql.catalyst.expressions.JoinedRow joinedRow) { throw new RuntimeException(); }
  private  scala.collection.Iterator<org.apache.spark.sql.Row> fullOuterIterator (org.apache.spark.sql.Row key, scala.collection.Iterable<org.apache.spark.sql.Row> leftIter, scala.collection.Iterable<org.apache.spark.sql.Row> rightIter, org.apache.spark.sql.catalyst.expressions.JoinedRow joinedRow) { throw new RuntimeException(); }
  private  java.util.HashMap<org.apache.spark.sql.Row, org.apache.spark.util.collection.CompactBuffer<org.apache.spark.sql.Row>> buildHashTable (scala.collection.Iterator<org.apache.spark.sql.Row> iter, org.apache.spark.sql.catalyst.expressions.Projection keyGenerator) { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> execute () { throw new RuntimeException(); }
}
