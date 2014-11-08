package org.apache.spark.metrics.sink;
private  class JmxSink implements org.apache.spark.metrics.sink.Sink {
  public  java.util.Properties property () { throw new RuntimeException(); }
  public  com.codahale.metrics.MetricRegistry registry () { throw new RuntimeException(); }
  // not preceding
  public   JmxSink (java.util.Properties property, com.codahale.metrics.MetricRegistry registry, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  public  com.codahale.metrics.JmxReporter reporter () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  public  void report () { throw new RuntimeException(); }
}
