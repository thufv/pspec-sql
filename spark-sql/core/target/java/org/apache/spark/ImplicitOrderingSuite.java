package org.apache.spark;
public  class ImplicitOrderingSuite extends org.scalatest.FunSuite implements org.apache.spark.LocalSparkContext {
  static public  class NonOrderedClass {
    public   NonOrderedClass () { throw new RuntimeException(); }
  }
  static public  class ComparableClass implements java.lang.Comparable<org.apache.spark.ImplicitOrderingSuite.ComparableClass> {
    public   ComparableClass () { throw new RuntimeException(); }
    public  int compareTo (org.apache.spark.ImplicitOrderingSuite.ComparableClass o) { throw new RuntimeException(); }
  }
  static public  class OrderedClass implements scala.math.Ordered<org.apache.spark.ImplicitOrderingSuite.OrderedClass> {
    public   OrderedClass () { throw new RuntimeException(); }
    public  int compare (org.apache.spark.ImplicitOrderingSuite.OrderedClass o) { throw new RuntimeException(); }
  }
  static public  scala.collection.immutable.List<scala.Tuple2<java.lang.Object, java.lang.String>> basicMapExpectations (org.apache.spark.rdd.RDD<java.lang.Object> rdd) { throw new RuntimeException(); }
  static public  scala.collection.immutable.List<scala.Tuple2<java.lang.Object, java.lang.String>> otherRDDMethodExpectations (org.apache.spark.rdd.RDD<java.lang.Object> rdd) { throw new RuntimeException(); }
  public   ImplicitOrderingSuite () { throw new RuntimeException(); }
}
