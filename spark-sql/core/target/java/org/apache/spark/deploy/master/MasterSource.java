package org.apache.spark.deploy.master;
private  class MasterSource implements org.apache.spark.metrics.source.Source {
  public  org.apache.spark.deploy.master.Master master () { throw new RuntimeException(); }
  // not preceding
  public   MasterSource (org.apache.spark.deploy.master.Master master) { throw new RuntimeException(); }
  public  com.codahale.metrics.MetricRegistry metricRegistry () { throw new RuntimeException(); }
  public  java.lang.String sourceName () { throw new RuntimeException(); }
}
