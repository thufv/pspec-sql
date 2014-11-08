package org.apache.spark.scheduler;
public  class SparkListenerJobStart implements org.apache.spark.scheduler.SparkListenerEvent, scala.Product, scala.Serializable {
  public  int jobId () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.Object> stageIds () { throw new RuntimeException(); }
  public  java.util.Properties properties () { throw new RuntimeException(); }
  // not preceding
  public   SparkListenerJobStart (int jobId, scala.collection.Seq<java.lang.Object> stageIds, java.util.Properties properties) { throw new RuntimeException(); }
}
