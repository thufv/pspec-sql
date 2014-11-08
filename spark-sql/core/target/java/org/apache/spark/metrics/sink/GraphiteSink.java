package org.apache.spark.metrics.sink;
private  class GraphiteSink implements org.apache.spark.metrics.sink.Sink {
  public  java.util.Properties property () { throw new RuntimeException(); }
  public  com.codahale.metrics.MetricRegistry registry () { throw new RuntimeException(); }
  // not preceding
  public   GraphiteSink (java.util.Properties property, com.codahale.metrics.MetricRegistry registry, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  public  int GRAPHITE_DEFAULT_PERIOD () { throw new RuntimeException(); }
  public  java.lang.String GRAPHITE_DEFAULT_UNIT () { throw new RuntimeException(); }
  public  java.lang.String GRAPHITE_DEFAULT_PREFIX () { throw new RuntimeException(); }
  public  java.lang.String GRAPHITE_KEY_HOST () { throw new RuntimeException(); }
  public  java.lang.String GRAPHITE_KEY_PORT () { throw new RuntimeException(); }
  public  java.lang.String GRAPHITE_KEY_PERIOD () { throw new RuntimeException(); }
  public  java.lang.String GRAPHITE_KEY_UNIT () { throw new RuntimeException(); }
  public  java.lang.String GRAPHITE_KEY_PREFIX () { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> propertyToOption (java.lang.String prop) { throw new RuntimeException(); }
  public  java.lang.String host () { throw new RuntimeException(); }
  public  int port () { throw new RuntimeException(); }
  public  int pollPeriod () { throw new RuntimeException(); }
  public  java.util.concurrent.TimeUnit pollUnit () { throw new RuntimeException(); }
  public  java.lang.String prefix () { throw new RuntimeException(); }
  public  com.codahale.metrics.graphite.Graphite graphite () { throw new RuntimeException(); }
  public  com.codahale.metrics.graphite.GraphiteReporter reporter () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  public  void report () { throw new RuntimeException(); }
}
