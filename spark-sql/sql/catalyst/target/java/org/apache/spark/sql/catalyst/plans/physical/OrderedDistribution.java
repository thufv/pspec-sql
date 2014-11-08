package org.apache.spark.sql.catalyst.plans.physical;
/**
 * Represents data where tuples have been ordered according to the <code>ordering</code>
 * {@link Expression Expressions}.  This is a strictly stronger guarantee than
 * {@link ClusteredDistribution} as an ordering will ensure that tuples that share the same value for
 * the ordering expressions are contiguous and will never be split across partitions.
 */
public  class OrderedDistribution implements org.apache.spark.sql.catalyst.plans.physical.Distribution, scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> ordering () { throw new RuntimeException(); }
  // not preceding
  public   OrderedDistribution (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder> ordering) { throw new RuntimeException(); }
  public  scala.collection.immutable.Set<org.apache.spark.sql.catalyst.expressions.Expression> clustering () { throw new RuntimeException(); }
}
