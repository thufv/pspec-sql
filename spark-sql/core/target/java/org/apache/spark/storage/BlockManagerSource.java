package org.apache.spark.storage;
private  class BlockManagerSource implements org.apache.spark.metrics.source.Source {
  public  org.apache.spark.storage.BlockManager blockManager () { throw new RuntimeException(); }
  // not preceding
  public   BlockManagerSource (org.apache.spark.storage.BlockManager blockManager, org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  public  com.codahale.metrics.MetricRegistry metricRegistry () { throw new RuntimeException(); }
  public  java.lang.String sourceName () { throw new RuntimeException(); }
}
