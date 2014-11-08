package org.apache.spark;
/**
 * :: DeveloperApi ::
 * Base class for dependencies.
 */
public abstract class Dependency<T extends java.lang.Object> implements scala.Serializable {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   Dependency () { throw new RuntimeException(); }
  public abstract  org.apache.spark.rdd.RDD<T> rdd () ;
}
