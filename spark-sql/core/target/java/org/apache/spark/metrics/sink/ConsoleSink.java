package org.apache.spark.metrics.sink;
private  class ConsoleSink implements org.apache.spark.metrics.sink.Sink {
  public  java.util.Properties property () { throw new RuntimeException(); }
  public  com.codahale.metrics.MetricRegistry registry () { throw new RuntimeException(); }
  // not preceding
  public   ConsoleSink (java.util.Properties property, com.codahale.metrics.MetricRegistry registry, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  public  int CONSOLE_DEFAULT_PERIOD () { throw new RuntimeException(); }
  public  java.lang.String CONSOLE_DEFAULT_UNIT () { throw new RuntimeException(); }
  public  java.lang.String CONSOLE_KEY_PERIOD () { throw new RuntimeException(); }
  public  java.lang.String CONSOLE_KEY_UNIT () { throw new RuntimeException(); }
  public  int pollPeriod () { throw new RuntimeException(); }
  public  java.util.concurrent.TimeUnit pollUnit () { throw new RuntimeException(); }
  public  com.codahale.metrics.ConsoleReporter reporter () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  public  void report () { throw new RuntimeException(); }
}
