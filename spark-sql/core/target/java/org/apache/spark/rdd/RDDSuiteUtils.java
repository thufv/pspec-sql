package org.apache.spark.rdd;
// no position
public  class RDDSuiteUtils {
  static public  class Person implements scala.Product, scala.Serializable {
    public  java.lang.String first () { throw new RuntimeException(); }
    public  java.lang.String last () { throw new RuntimeException(); }
    public  int age () { throw new RuntimeException(); }
    // not preceding
    public   Person (java.lang.String first, java.lang.String last, int age) { throw new RuntimeException(); }
  }
  // no position
  // not preceding
  static public  class Person$ extends scala.runtime.AbstractFunction3<java.lang.String, java.lang.String, java.lang.Object, org.apache.spark.rdd.RDDSuiteUtils.Person> implements scala.Serializable {
    public   Person$ () { throw new RuntimeException(); }
  }
  // no position
  static public  class AgeOrdering$ implements scala.math.Ordering<org.apache.spark.rdd.RDDSuiteUtils.Person> {
    public   AgeOrdering$ () { throw new RuntimeException(); }
    public  int compare (org.apache.spark.rdd.RDDSuiteUtils.Person a, org.apache.spark.rdd.RDDSuiteUtils.Person b) { throw new RuntimeException(); }
  }
  // no position
  static public  class NameOrdering$ implements scala.math.Ordering<org.apache.spark.rdd.RDDSuiteUtils.Person> {
    public   NameOrdering$ () { throw new RuntimeException(); }
    public  int compare (org.apache.spark.rdd.RDDSuiteUtils.Person a, org.apache.spark.rdd.RDDSuiteUtils.Person b) { throw new RuntimeException(); }
  }
}
