package org.apache.spark;
/**
 * :: DeveloperApi ::
 * Base class for dependencies where each partition of the child RDD depends on a small number
 * of partitions of the parent RDD. Narrow dependencies allow for pipelined execution.
 */
public abstract class NarrowDependency<T extends java.lang.Object> extends org.apache.spark.Dependency<T> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   NarrowDependency (org.apache.spark.rdd.RDD<T> _rdd) { throw new RuntimeException(); }
  /**
   * Get the parent partitions for a child partition.
   * @param partitionId a partition of the child RDD
   * @return the partitions of the parent RDD that the child partition depends upon
   */
  public abstract  scala.collection.Seq<java.lang.Object> getParents (int partitionId) ;
  public  org.apache.spark.rdd.RDD<T> rdd () { throw new RuntimeException(); }
}
