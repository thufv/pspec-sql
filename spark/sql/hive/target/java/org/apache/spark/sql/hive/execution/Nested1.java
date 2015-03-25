package org.apache.spark.sql.hive.execution;
public  class Nested1 implements scala.Product, scala.Serializable {
  public  org.apache.spark.sql.hive.execution.Nested2 f1 () { throw new RuntimeException(); }
  // not preceding
  public   Nested1 (org.apache.spark.sql.hive.execution.Nested2 f1) { throw new RuntimeException(); }
}
