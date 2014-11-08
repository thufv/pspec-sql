package org.apache.spark;
public  class ShuffleSuite extends org.scalatest.FunSuite implements org.scalatest.Matchers, org.apache.spark.LocalSparkContext {
  static public  class NonJavaSerializableClass implements java.lang.Comparable<org.apache.spark.ShuffleSuite.NonJavaSerializableClass> {
    public  int value () { throw new RuntimeException(); }
    // not preceding
    public   NonJavaSerializableClass (int value) { throw new RuntimeException(); }
    public  int compareTo (org.apache.spark.ShuffleSuite.NonJavaSerializableClass o) { throw new RuntimeException(); }
  }
  static public  int mergeCombineException (int x, int y) { throw new RuntimeException(); }
  public   ShuffleSuite () { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
}
