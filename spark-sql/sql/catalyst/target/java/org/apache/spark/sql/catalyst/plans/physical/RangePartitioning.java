package org.apache.spark.sql.catalyst.plans.physical;
/**
 * Represents a partitioning where rows are split across partitions based on some total ordering of
 * the expressions specified in <code>ordering</code>.  When data is partitioned in this manner the following
 * two conditions are guaranteed to hold:
 *  - All row where the expressions in <code>ordering</code> evaluate to the same values will be in the same
 *    partition.
 *  - Each partition will have a <code>min</code> and <code>max</code> row, relative to the given ordering.  All rows
 *    that are in between <code>min</code> and <code>max</code> in this <code>ordering</code> will reside in this partition.
 * <p>
 * This class extends expression primarily so that transformations over expression will descend
 * into its child.
 */
public  class RangePartitioning extends org.apache.spark.sql.catalyst.expressions.Expression implements org.apache.spark.sql.catalyst.plans.physical.Partitioning, scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> ordering () { throw new RuntimeException(); }
  public  int numPartitions () { throw new RuntimeException(); }
  // not preceding
  public   RangePartitioning (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> ordering, int numPartitions) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> children () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.IntegerType$ dataType () { throw new RuntimeException(); }
  private  scala.collection.immutable.Set<org.apache.spark.sql.catalyst.expressions.Expression> clusteringSet () { throw new RuntimeException(); }
  public  boolean satisfies (org.apache.spark.sql.catalyst.plans.physical.Distribution required) { throw new RuntimeException(); }
  public  boolean compatibleWith (org.apache.spark.sql.catalyst.plans.physical.Partitioning other) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression.EvaluatedType eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
