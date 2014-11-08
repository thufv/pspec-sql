package org.apache.spark.rdd;
/**
 * :: Experimental ::
 * A set of asynchronous RDD actions available through an implicit conversion.
 * Import <code>org.apache.spark.SparkContext._</code> at the top of your program to use these functions.
 */
public  class AsyncRDDActions<T extends java.lang.Object> implements scala.Serializable, org.apache.spark.Logging {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   AsyncRDDActions (org.apache.spark.rdd.RDD<T> self, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  /**
   * Returns a future for counting the number of elements in the RDD.
   */
  public  org.apache.spark.FutureAction<java.lang.Object> countAsync () { throw new RuntimeException(); }
  /**
   * Returns a future for retrieving all elements of this RDD.
   */
  public  org.apache.spark.FutureAction<scala.collection.Seq<T>> collectAsync () { throw new RuntimeException(); }
  /**
   * Returns a future for retrieving the first num elements of the RDD.
   */
  public  org.apache.spark.FutureAction<scala.collection.Seq<T>> takeAsync (int num) { throw new RuntimeException(); }
  /**
   * Applies a function f to all elements of this RDD.
   */
  public  org.apache.spark.FutureAction<scala.runtime.BoxedUnit> foreachAsync (scala.Function1<T, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
  /**
   * Applies a function f to each partition of this RDD.
   */
  public  org.apache.spark.FutureAction<scala.runtime.BoxedUnit> foreachPartitionAsync (scala.Function1<scala.collection.Iterator<T>, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
}
