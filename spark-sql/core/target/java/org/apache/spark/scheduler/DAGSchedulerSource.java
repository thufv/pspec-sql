package org.apache.spark.scheduler;
private  class DAGSchedulerSource implements org.apache.spark.metrics.source.Source {
  public  org.apache.spark.scheduler.DAGScheduler dagScheduler () { throw new RuntimeException(); }
  // not preceding
  public   DAGSchedulerSource (org.apache.spark.scheduler.DAGScheduler dagScheduler, org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  public  com.codahale.metrics.MetricRegistry metricRegistry () { throw new RuntimeException(); }
  public  java.lang.String sourceName () { throw new RuntimeException(); }
}
