package org.apache.spark.scheduler;
private  class RuntimePercentage implements scala.Product, scala.Serializable {
  static public  org.apache.spark.scheduler.RuntimePercentage apply (long totalTime, org.apache.spark.executor.TaskMetrics metrics) { throw new RuntimeException(); }
  public  double executorPct () { throw new RuntimeException(); }
  public  scala.Option<java.lang.Object> fetchPct () { throw new RuntimeException(); }
  public  double other () { throw new RuntimeException(); }
  // not preceding
  public   RuntimePercentage (double executorPct, scala.Option<java.lang.Object> fetchPct, double other) { throw new RuntimeException(); }
}
