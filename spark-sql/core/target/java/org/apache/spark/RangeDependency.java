package org.apache.spark;
/**
 * :: DeveloperApi ::
 * Represents a one-to-one dependency between ranges of partitions in the parent and child RDDs.
 * @param rdd the parent RDD
 * @param inStart the start of the range in the parent RDD
 * @param outStart the start of the range in the child RDD
 * @param length the length of the range
 */
public  class RangeDependency<T extends java.lang.Object> extends org.apache.spark.NarrowDependency<T> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   RangeDependency (org.apache.spark.rdd.RDD<T> rdd, int inStart, int outStart, int length) { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.Object> getParents (int partitionId) { throw new RuntimeException(); }
}
