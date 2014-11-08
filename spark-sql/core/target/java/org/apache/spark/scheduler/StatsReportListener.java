package org.apache.spark.scheduler;
/**
 * :: DeveloperApi ::
 * Simple SparkListener that logs a few summary statistics when each stage completes
 */
public  class StatsReportListener implements org.apache.spark.scheduler.SparkListener, org.apache.spark.Logging {
  static public  int[] percentiles () { throw new RuntimeException(); }
  static public  double[] probabilities () { throw new RuntimeException(); }
  static public  java.lang.String percentilesHeader () { throw new RuntimeException(); }
  static public  scala.Option<org.apache.spark.util.Distribution> extractDoubleDistribution (scala.collection.Seq<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics, scala.Function2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics, scala.Option<java.lang.Object>> getMetric) { throw new RuntimeException(); }
  static public  scala.Option<org.apache.spark.util.Distribution> extractLongDistribution (scala.collection.Seq<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics, scala.Function2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics, scala.Option<java.lang.Object>> getMetric) { throw new RuntimeException(); }
  static public  void showDistribution (java.lang.String heading, org.apache.spark.util.Distribution d, scala.Function1<java.lang.Object, java.lang.String> formatNumber) { throw new RuntimeException(); }
  static public  void showDistribution (java.lang.String heading, scala.Option<org.apache.spark.util.Distribution> dOpt, scala.Function1<java.lang.Object, java.lang.String> formatNumber) { throw new RuntimeException(); }
  static public  void showDistribution (java.lang.String heading, scala.Option<org.apache.spark.util.Distribution> dOpt, java.lang.String format) { throw new RuntimeException(); }
  static public  void showDistribution (java.lang.String heading, java.lang.String format, scala.Function2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics, scala.Option<java.lang.Object>> getMetric, scala.collection.Seq<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics) { throw new RuntimeException(); }
  static public  void showBytesDistribution (java.lang.String heading, scala.Function2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics, scala.Option<java.lang.Object>> getMetric, scala.collection.Seq<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics) { throw new RuntimeException(); }
  static public  void showBytesDistribution (java.lang.String heading, scala.Option<org.apache.spark.util.Distribution> dOpt) { throw new RuntimeException(); }
  static public  void showBytesDistribution (java.lang.String heading, org.apache.spark.util.Distribution dist) { throw new RuntimeException(); }
  static public  void showMillisDistribution (java.lang.String heading, scala.Option<org.apache.spark.util.Distribution> dOpt) { throw new RuntimeException(); }
  static public  void showMillisDistribution (java.lang.String heading, scala.Function2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics, scala.Option<java.lang.Object>> getMetric, scala.collection.Seq<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics) { throw new RuntimeException(); }
  static public  long seconds () { throw new RuntimeException(); }
  static public  long minutes () { throw new RuntimeException(); }
  static public  long hours () { throw new RuntimeException(); }
  /**
   * Reformat a time interval in milliseconds to a prettier format for output
   */
  static public  java.lang.String millisToString (long ms) { throw new RuntimeException(); }
  public   StatsReportListener () { throw new RuntimeException(); }
  private  scala.collection.mutable.Buffer<scala.Tuple2<org.apache.spark.scheduler.TaskInfo, org.apache.spark.executor.TaskMetrics>> taskInfoMetrics () { throw new RuntimeException(); }
  public  void onTaskEnd (org.apache.spark.scheduler.SparkListenerTaskEnd taskEnd) { throw new RuntimeException(); }
  public  void onStageCompleted (org.apache.spark.scheduler.SparkListenerStageCompleted stageCompleted) { throw new RuntimeException(); }
}
