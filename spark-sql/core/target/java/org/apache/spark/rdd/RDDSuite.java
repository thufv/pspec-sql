package org.apache.spark.rdd;
public  class RDDSuite extends org.scalatest.FunSuite implements org.apache.spark.SharedSparkContext {
  public   RDDSuite () { throw new RuntimeException(); }
  /** A contrived RDD that allows the manual addition of dependencies after creation. */
  private  class CyclicalDependencyRDD<T extends java.lang.Object> extends org.apache.spark.rdd.RDD<T> {
    // not preceding
    // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
    public   CyclicalDependencyRDD (scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
    private  scala.collection.mutable.ArrayBuffer<org.apache.spark.Dependency<?>> mutableDependencies () { throw new RuntimeException(); }
    public  scala.collection.Iterator<T> compute (org.apache.spark.Partition p, org.apache.spark.TaskContext c) { throw new RuntimeException(); }
    public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.Dependency<?>> getDependencies () { throw new RuntimeException(); }
    public  void addDependency (org.apache.spark.Dependency<?> dep) { throw new RuntimeException(); }
  }
}
