package org.apache.spark;
private  class CleanRDD implements org.apache.spark.CleanupTask, scala.Product, scala.Serializable {
  public  int rddId () { throw new RuntimeException(); }
  // not preceding
  public   CleanRDD (int rddId) { throw new RuntimeException(); }
}
