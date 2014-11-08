package org.apache.spark.api.java;
// no position
public  class JavaSparkContext$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final JavaSparkContext$ MODULE$ = null;
  public   JavaSparkContext$ () { throw new RuntimeException(); }
  public  org.apache.spark.api.java.JavaSparkContext fromSparkContext (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  public  org.apache.spark.SparkContext toSparkContext (org.apache.spark.api.java.JavaSparkContext jsc) { throw new RuntimeException(); }
  /**
   * Find the JAR from which a given class was loaded, to make it easy for users to pass
   * their JARs to SparkContext.
   */
  public  java.lang.String[] jarOfClass (java.lang.Class<?> cls) { throw new RuntimeException(); }
  /**
   * Find the JAR that contains the class of a particular object, to make it easy for users
   * to pass their JARs to SparkContext. In most cases you can call jarOfObject(this) in
   * your driver program.
   */
  public  java.lang.String[] jarOfObject (java.lang.Object obj) { throw new RuntimeException(); }
  /**
   * Produces a ClassTag[T], which is actually just a casted ClassTag[AnyRef].
   * <p>
   * This method is used to keep ClassTags out of the external Java API, as the Java compiler
   * cannot produce them automatically. While this ClassTag-faking does please the compiler,
   * it can cause problems at runtime if the Scala API relies on ClassTags for correctness.
   * <p>
   * Often, though, a ClassTag[AnyRef] will not lead to incorrect behavior, just worse performance
   * or security issues. For instance, an Array[AnyRef] can hold any type T, but may lose primitive
   * specialization.
   */
  private <T extends java.lang.Object> scala.reflect.ClassTag<T> fakeClassTag () { throw new RuntimeException(); }
}
