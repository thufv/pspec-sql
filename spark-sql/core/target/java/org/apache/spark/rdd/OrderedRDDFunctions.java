package org.apache.spark.rdd;
/**
 * Extra functions available on RDDs of (key, value) pairs where the key is sortable through
 * an implicit conversion. Import <code>org.apache.spark.SparkContext._</code> at the top of your program to
 * use these functions. They will work with any key type <code>K</code> that has an implicit <code>Ordering[K]</code> in
 * scope.  Ordering objects already exist for all of the standard primitive types.  Users can also
 * define their own orderings for custom types, or to override the default ordering.  The implicit
 * ordering that is in the closest scope will be used.
 * <p>
 * <pre><code>
 *   import org.apache.spark.SparkContext._
 *
 *   val rdd: RDD[(String, Int)] = ...
 *   implicit val caseInsensitiveOrdering = new Ordering[String] {
 *     override def compare(a: String, b: String) = a.toLowerCase.compare(b.toLowerCase)
 *   }
 *
 *   // Sort by key, using the above case insensitive ordering.
 *   rdd.sortByKey()
 * </code></pre>
 */
public  class OrderedRDDFunctions<K extends java.lang.Object, V extends java.lang.Object, P extends scala.Product2<K, V>> implements org.apache.spark.Logging, scala.Serializable {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(AppliedTypeTree(Select(Ident(scala), scala.Product2), List(TypeTree().setOriginal(Ident(newTypeName("K"))), TypeTree().setOriginal(Ident(newTypeName("V"))))))))
  public   OrderedRDDFunctions (org.apache.spark.rdd.RDD<P> self, scala.math.Ordering<K> evidence$1, scala.reflect.ClassTag<K> evidence$2, scala.reflect.ClassTag<V> evidence$3, scala.reflect.ClassTag<P> evidence$4) { throw new RuntimeException(); }
  private  scala.math.Ordering<K> ordering () { throw new RuntimeException(); }
  /**
   * Sort the RDD by key, so that each partition contains a sorted range of the elements. Calling
   * <code>collect</code> or <code>save</code> on the resulting RDD will return or output an ordered list of records
   * (in the <code>save</code> case, they will be written to multiple <code>part-X</code> files in the filesystem, in
   * order of the keys).
   */
  public  org.apache.spark.rdd.RDD<scala.Tuple2<K, V>> sortByKey (boolean ascending, int numPartitions) { throw new RuntimeException(); }
}
