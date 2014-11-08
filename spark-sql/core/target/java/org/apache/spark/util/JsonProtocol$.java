package org.apache.spark.util;
// no position
private  class JsonProtocol$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final JsonProtocol$ MODULE$ = null;
  public   JsonProtocol$ () { throw new RuntimeException(); }
  private  org.json4s.DefaultFormats$ format () { throw new RuntimeException(); }
  /** ------------------------------------------------- *
   * JSON serialization methods for SparkListenerEvents |
   * -------------------------------------------------- */
  public  org.json4s.JsonAST.JValue sparkEventToJson (org.apache.spark.scheduler.SparkListenerEvent event) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue stageSubmittedToJson (org.apache.spark.scheduler.SparkListenerStageSubmitted stageSubmitted) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue stageCompletedToJson (org.apache.spark.scheduler.SparkListenerStageCompleted stageCompleted) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue taskStartToJson (org.apache.spark.scheduler.SparkListenerTaskStart taskStart) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue taskGettingResultToJson (org.apache.spark.scheduler.SparkListenerTaskGettingResult taskGettingResult) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue taskEndToJson (org.apache.spark.scheduler.SparkListenerTaskEnd taskEnd) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue jobStartToJson (org.apache.spark.scheduler.SparkListenerJobStart jobStart) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue jobEndToJson (org.apache.spark.scheduler.SparkListenerJobEnd jobEnd) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue environmentUpdateToJson (org.apache.spark.scheduler.SparkListenerEnvironmentUpdate environmentUpdate) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue blockManagerAddedToJson (org.apache.spark.scheduler.SparkListenerBlockManagerAdded blockManagerAdded) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue blockManagerRemovedToJson (org.apache.spark.scheduler.SparkListenerBlockManagerRemoved blockManagerRemoved) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue unpersistRDDToJson (org.apache.spark.scheduler.SparkListenerUnpersistRDD unpersistRDD) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue applicationStartToJson (org.apache.spark.scheduler.SparkListenerApplicationStart applicationStart) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue applicationEndToJson (org.apache.spark.scheduler.SparkListenerApplicationEnd applicationEnd) { throw new RuntimeException(); }
  /** ------------------------------------------------------------------- *
   * JSON serialization methods for classes SparkListenerEvents depend on |
   * -------------------------------------------------------------------- */
  public  org.json4s.JsonAST.JValue stageInfoToJson (org.apache.spark.scheduler.StageInfo stageInfo) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue taskInfoToJson (org.apache.spark.scheduler.TaskInfo taskInfo) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue accumulableInfoToJson (org.apache.spark.scheduler.AccumulableInfo accumulableInfo) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue taskMetricsToJson (org.apache.spark.executor.TaskMetrics taskMetrics) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue shuffleReadMetricsToJson (org.apache.spark.executor.ShuffleReadMetrics shuffleReadMetrics) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue shuffleWriteMetricsToJson (org.apache.spark.executor.ShuffleWriteMetrics shuffleWriteMetrics) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue inputMetricsToJson (org.apache.spark.executor.InputMetrics inputMetrics) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue taskEndReasonToJson (org.apache.spark.TaskEndReason taskEndReason) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue blockManagerIdToJson (org.apache.spark.storage.BlockManagerId blockManagerId) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue jobResultToJson (org.apache.spark.scheduler.JobResult jobResult) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue rddInfoToJson (org.apache.spark.storage.RDDInfo rddInfo) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue storageLevelToJson (org.apache.spark.storage.StorageLevel storageLevel) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue blockStatusToJson (org.apache.spark.storage.BlockStatus blockStatus) { throw new RuntimeException(); }
  /** ------------------------------ *
   * Util JSON serialization methods |
   * ------------------------------- */
  public  org.json4s.JsonAST.JValue mapToJson (scala.collection.Map<java.lang.String, java.lang.String> m) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue propertiesToJson (java.util.Properties properties) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue UUIDToJson (java.util.UUID id) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue stackTraceToJson (java.lang.StackTraceElement[] stackTrace) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue exceptionToJson (java.lang.Exception exception) { throw new RuntimeException(); }
  /** --------------------------------------------------- *
   * JSON deserialization methods for SparkListenerEvents |
   * ---------------------------------------------------- */
  public  org.apache.spark.scheduler.SparkListenerEvent sparkEventFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerStageSubmitted stageSubmittedFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerStageCompleted stageCompletedFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerTaskStart taskStartFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerTaskGettingResult taskGettingResultFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerTaskEnd taskEndFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerJobStart jobStartFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerJobEnd jobEndFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerEnvironmentUpdate environmentUpdateFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerBlockManagerAdded blockManagerAddedFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerBlockManagerRemoved blockManagerRemovedFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerUnpersistRDD unpersistRDDFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerApplicationStart applicationStartFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.SparkListenerApplicationEnd applicationEndFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  /** --------------------------------------------------------------------- *
   * JSON deserialization methods for classes SparkListenerEvents depend on |
   * ---------------------------------------------------------------------- */
  public  org.apache.spark.scheduler.StageInfo stageInfoFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.TaskInfo taskInfoFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.AccumulableInfo accumulableInfoFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.executor.TaskMetrics taskMetricsFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.executor.ShuffleReadMetrics shuffleReadMetricsFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.executor.ShuffleWriteMetrics shuffleWriteMetricsFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.executor.InputMetrics inputMetricsFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.TaskEndReason taskEndReasonFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockManagerId blockManagerIdFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.JobResult jobResultFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.storage.RDDInfo rddInfoFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel storageLevelFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  org.apache.spark.storage.BlockStatus blockStatusFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  /** -------------------------------- *
   * Util JSON deserialization methods |
   * --------------------------------- */
  public  scala.collection.Map<java.lang.String, java.lang.String> mapFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  java.util.Properties propertiesFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  java.util.UUID UUIDFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  java.lang.StackTraceElement[] stackTraceFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
  public  java.lang.Exception exceptionFromJson (org.json4s.JsonAST.JValue json) { throw new RuntimeException(); }
}
