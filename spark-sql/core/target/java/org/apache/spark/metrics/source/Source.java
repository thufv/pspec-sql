package org.apache.spark.metrics.source;
private  interface Source {
  public abstract  java.lang.String sourceName () ;
  public abstract  com.codahale.metrics.MetricRegistry metricRegistry () ;
}
