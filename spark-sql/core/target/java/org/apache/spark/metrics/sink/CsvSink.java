package org.apache.spark.metrics.sink;
private  class CsvSink implements org.apache.spark.metrics.sink.Sink {
  public  java.util.Properties property () { throw new RuntimeException(); }
  public  com.codahale.metrics.MetricRegistry registry () { throw new RuntimeException(); }
  // not preceding
  public   CsvSink (java.util.Properties property, com.codahale.metrics.MetricRegistry registry, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  public  java.lang.String CSV_KEY_PERIOD () { throw new RuntimeException(); }
  public  java.lang.String CSV_KEY_UNIT () { throw new RuntimeException(); }
  public  java.lang.String CSV_KEY_DIR () { throw new RuntimeException(); }
  public  int CSV_DEFAULT_PERIOD () { throw new RuntimeException(); }
  public  java.lang.String CSV_DEFAULT_UNIT () { throw new RuntimeException(); }
  public  java.lang.String CSV_DEFAULT_DIR () { throw new RuntimeException(); }
  public  int pollPeriod () { throw new RuntimeException(); }
  public  java.util.concurrent.TimeUnit pollUnit () { throw new RuntimeException(); }
  public  java.lang.String pollDir () { throw new RuntimeException(); }
  public  com.codahale.metrics.CsvReporter reporter () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  public  void report () { throw new RuntimeException(); }
}
