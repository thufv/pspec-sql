package org.apache.spark.sql.hive.execution;
public  class Nested implements scala.Product, scala.Serializable {
  public  int a () { throw new RuntimeException(); }
  public  int B () { throw new RuntimeException(); }
  // not preceding
  public   Nested (int a, int B) { throw new RuntimeException(); }
}
