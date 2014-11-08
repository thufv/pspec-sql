package org.apache.spark.sql.catalyst.plans.physical;
/**
 * Represents a partitioning where rows are split up across partitions based on the hash
 * of <code>expressions</code>.  All rows where <code>expressions</code> evaluate to the same values are guaranteed to be
 * in the same partition.
 */
public  class HashPartitioning extends org.apache.spark.sql.catalyst.expressions.Expression implements org.apache.spark.sql.catalyst.plans.physical.Partitioning, scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions () { throw new RuntimeException(); }
  public  int numPartitions () { throw new RuntimeException(); }
  // not preceding
  public   HashPartitioning (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions, int numPartitions) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.IntegerType$ dataType () { throw new RuntimeException(); }
  private  scala.collection.immutable.Set<org.apache.spark.sql.catalyst.expressions.Expression> clusteringSet () { throw new RuntimeException(); }
  public  boolean satisfies (org.apache.spark.sql.catalyst.plans.physical.Distribution required) { throw new RuntimeException(); }
  public  boolean compatibleWith (org.apache.spark.sql.catalyst.plans.physical.Partitioning other) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression.EvaluatedType eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
