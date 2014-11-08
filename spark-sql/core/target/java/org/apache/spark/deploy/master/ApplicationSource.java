package org.apache.spark.deploy.master;
public  class ApplicationSource implements org.apache.spark.metrics.source.Source {
  public  org.apache.spark.deploy.master.ApplicationInfo application () { throw new RuntimeException(); }
  // not preceding
  public   ApplicationSource (org.apache.spark.deploy.master.ApplicationInfo application) { throw new RuntimeException(); }
  public  com.codahale.metrics.MetricRegistry metricRegistry () { throw new RuntimeException(); }
  public  java.lang.String sourceName () { throw new RuntimeException(); }
}
