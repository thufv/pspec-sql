package org.apache.spark.scheduler;
// no position
private  class StatsReportListener$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final StatsReportListener$ MODULE$ = null;
  public   StatsReportListener$ () { throw new RuntimeException(); }
  public  int[] percentiles () { throw new RuntimeException(); }
  public  double[] probabilities () { throw new RuntimeException(); }
  public  java.lang.String percentilesHeader () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.util.Distribution> extractDoubleDistribution (scala.collection.Seq<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics, scala.Function2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics, scala.Option<java.lang.Object>> getMetric) { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.util.Distribution> extractLongDistribution (scala.collection.Seq<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics, scala.Function2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics, scala.Option<java.lang.Object>> getMetric) { throw new RuntimeException(); }
  public  void showDistribution (java.lang.String heading, org.apache.spark.util.Distribution d, scala.Function1<java.lang.Object, java.lang.String> formatNumber) { throw new RuntimeException(); }
  public  void showDistribution (java.lang.String heading, scala.Option<org.apache.spark.util.Distribution> dOpt, scala.Function1<java.lang.Object, java.lang.String> formatNumber) { throw new RuntimeException(); }
  public  void showDistribution (java.lang.String heading, scala.Option<org.apache.spark.util.Distribution> dOpt, java.lang.String format) { throw new RuntimeException(); }
  public  void showDistribution (java.lang.String heading, java.lang.String format, scala.Function2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics, scala.Option<java.lang.Object>> getMetric, scala.collection.Seq<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics) { throw new RuntimeException(); }
  public  void showBytesDistribution (java.lang.String heading, scala.Function2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics, scala.Option<java.lang.Object>> getMetric, scala.collection.Seq<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics) { throw new RuntimeException(); }
  public  void showBytesDistribution (java.lang.String heading, scala.Option<org.apache.spark.util.Distribution> dOpt) { throw new RuntimeException(); }
  public  void showBytesDistribution (java.lang.String heading, org.apache.spark.util.Distribution dist) { throw new RuntimeException(); }
  public  void showMillisDistribution (java.lang.String heading, scala.Option<org.apache.spark.util.Distribution> dOpt) { throw new RuntimeException(); }
  public  void showMillisDistribution (java.lang.String heading, scala.Function2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics, scala.Option<java.lang.Object>> getMetric, scala.collection.Seq<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics) { throw new RuntimeException(); }
  public  long seconds () { throw new RuntimeException(); }
  public  long minutes () { throw new RuntimeException(); }
  public  long hours () { throw new RuntimeException(); }
  /**
   * Reformat a time interval in milliseconds to a prettier format for output
   */
  public  java.lang.String millisToString (long ms) { throw new RuntimeException(); }
}
