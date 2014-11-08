package org.apache.spark.metrics.sink;
private  interface Sink {
  public abstract  void start () ;
  public abstract  void stop () ;
  public abstract  void report () ;
}
