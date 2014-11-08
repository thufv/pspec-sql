package org.apache.spark;
/**
 * :: DeveloperApi ::
 * Represents a one-to-one dependency between partitions of the parent and child RDDs.
 */
public  class OneToOneDependency<T extends java.lang.Object> extends org.apache.spark.NarrowDependency<T> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   OneToOneDependency (org.apache.spark.rdd.RDD<T> rdd) { throw new RuntimeException(); }
  public  scala.collection.immutable.List<java.lang.Object> getParents (int partitionId) { throw new RuntimeException(); }
}
