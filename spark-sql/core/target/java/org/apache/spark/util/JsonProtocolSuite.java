package org.apache.spark.util;
public  class JsonProtocolSuite extends org.scalatest.FunSuite {
  public   JsonProtocolSuite () { throw new RuntimeException(); }
  /** -------------------------- *
   | Helper test running methods |
   * --------------------------- */
  private  void testEvent (org.apache.spark.scheduler.SparkListenerEvent event, java.lang.String jsonString) { throw new RuntimeException(); }
  private  void testRDDInfo (org.apache.spark.storage.RDDInfo info) { throw new RuntimeException(); }
  private  void testStageInfo (org.apache.spark.scheduler.StageInfo info) { throw new RuntimeException(); }
  private  void testStorageLevel (org.apache.spark.storage.StorageLevel level) { throw new RuntimeException(); }
  private  void testTaskMetrics (org.apache.spark.executor.TaskMetrics metrics) { throw new RuntimeException(); }
  private  void testBlockManagerId (org.apache.spark.storage.BlockManagerId id) { throw new RuntimeException(); }
  private  void testTaskInfo (org.apache.spark.scheduler.TaskInfo info) { throw new RuntimeException(); }
  private  void testJobResult (org.apache.spark.scheduler.JobResult result) { throw new RuntimeException(); }
  private  void testTaskEndReason (org.apache.spark.TaskEndReason reason) { throw new RuntimeException(); }
  private  void testBlockId (org.apache.spark.storage.BlockId blockId) { throw new RuntimeException(); }
  /** -------------------------------- *
   | Util methods for comparing events |
   * --------------------------------- */
  private  void assertEquals (org.apache.spark.scheduler.SparkListenerEvent event1, org.apache.spark.scheduler.SparkListenerEvent event2) { throw new RuntimeException(); }
  private  void assertEquals (org.apache.spark.scheduler.StageInfo info1, org.apache.spark.scheduler.StageInfo info2) { throw new RuntimeException(); }
  private  void assertEquals (org.apache.spark.storage.RDDInfo info1, org.apache.spark.storage.RDDInfo info2) { throw new RuntimeException(); }
  private  void assertEquals (org.apache.spark.storage.StorageLevel level1, org.apache.spark.storage.StorageLevel level2) { throw new RuntimeException(); }
  private  void assertEquals (org.apache.spark.scheduler.TaskInfo info1, org.apache.spark.scheduler.TaskInfo info2) { throw new RuntimeException(); }
  private  void assertEquals (org.apache.spark.executor.TaskMetrics metrics1, org.apache.spark.executor.TaskMetrics metrics2) { throw new RuntimeException(); }
  private  void assertEquals (org.apache.spark.executor.ShuffleReadMetrics metrics1, org.apache.spark.executor.ShuffleReadMetrics metrics2) { throw new RuntimeException(); }
  private  void assertEquals (org.apache.spark.executor.ShuffleWriteMetrics metrics1, org.apache.spark.executor.ShuffleWriteMetrics metrics2) { throw new RuntimeException(); }
  private  void assertEquals (org.apache.spark.executor.InputMetrics metrics1, org.apache.spark.executor.InputMetrics metrics2) { throw new RuntimeException(); }
  private  void assertEquals (org.apache.spark.storage.BlockManagerId bm1, org.apache.spark.storage.BlockManagerId bm2) { throw new RuntimeException(); }
  private  void assertEquals (org.apache.spark.scheduler.JobResult result1, org.apache.spark.scheduler.JobResult result2) { throw new RuntimeException(); }
  private  void assertEquals (org.apache.spark.TaskEndReason reason1, org.apache.spark.TaskEndReason reason2) { throw new RuntimeException(); }
  private  void assertEquals (scala.collection.Map<java.lang.String, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>>> details1, scala.collection.Map<java.lang.String, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>>> details2) { throw new RuntimeException(); }
  private  void assertEquals (java.lang.Exception exception1, java.lang.Exception exception2) { throw new RuntimeException(); }
  private  void assertJsonStringEquals (java.lang.String json1, java.lang.String json2) { throw new RuntimeException(); }
  private <T extends java.lang.Object> void assertSeqEquals (scala.collection.Seq<T> seq1, scala.collection.Seq<T> seq2, scala.Function2<T, T, scala.runtime.BoxedUnit> assertEquals) { throw new RuntimeException(); }
  private <T extends java.lang.Object> void assertOptionEquals (scala.Option<T> opt1, scala.Option<T> opt2, scala.Function2<T, T, scala.runtime.BoxedUnit> assertEquals) { throw new RuntimeException(); }
  /**
   * Use different names for methods we pass in to assertSeqEquals or assertOptionEquals
   */
  private  void assertShuffleReadEquals (org.apache.spark.executor.ShuffleReadMetrics r1, org.apache.spark.executor.ShuffleReadMetrics r2) { throw new RuntimeException(); }
  private  void assertShuffleWriteEquals (org.apache.spark.executor.ShuffleWriteMetrics w1, org.apache.spark.executor.ShuffleWriteMetrics w2) { throw new RuntimeException(); }
  private  void assertInputMetricsEquals (org.apache.spark.executor.InputMetrics i1, org.apache.spark.executor.InputMetrics i2) { throw new RuntimeException(); }
  private  void assertTaskMetricsEquals (org.apache.spark.executor.TaskMetrics t1, org.apache.spark.executor.TaskMetrics t2) { throw new RuntimeException(); }
  private  void assertBlocksEquals (scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> blocks1, scala.collection.Seq<scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus>> blocks2) { throw new RuntimeException(); }
  private  void assertBlockEquals (scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus> b1, scala.Tuple2<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus> b2) { throw new RuntimeException(); }
  private  void assertStackTraceElementEquals (java.lang.StackTraceElement ste1, java.lang.StackTraceElement ste2) { throw new RuntimeException(); }
  /** ----------------------------------- *
   | Util methods for constructing events |
   * ------------------------------------ */
  private  java.util.Properties properties () { throw new RuntimeException(); }
  private  java.lang.StackTraceElement[] stackTrace () { throw new RuntimeException(); }
  private  org.apache.spark.storage.RDDInfo makeRddInfo (int a, int b, int c, long d, long e) { throw new RuntimeException(); }
  private  org.apache.spark.scheduler.StageInfo makeStageInfo (int a, int b, int c, long d, long e) { throw new RuntimeException(); }
  private  org.apache.spark.scheduler.TaskInfo makeTaskInfo (long a, int b, int c, long d, boolean speculative) { throw new RuntimeException(); }
  private  org.apache.spark.scheduler.AccumulableInfo makeAccumulableInfo (int id) { throw new RuntimeException(); }
  /**
   * Creates a TaskMetrics object describing a task that read data from Hadoop (if hasHadoopInput is
   * set to true) or read data from a shuffle otherwise.
   */
  private  org.apache.spark.executor.TaskMetrics makeTaskMetrics (long a, long b, long c, long d, int e, int f, boolean hasHadoopInput) { throw new RuntimeException(); }
  /** --------------------------------------- *
   | JSON string representation of each event |
   * ---------------------------------------- */
  private  java.lang.String stageSubmittedJsonString () { throw new RuntimeException(); }
  private  java.lang.String stageCompletedJsonString () { throw new RuntimeException(); }
  private  java.lang.String taskStartJsonString () { throw new RuntimeException(); }
  private  java.lang.String taskGettingResultJsonString () { throw new RuntimeException(); }
  private  java.lang.String taskEndJsonString () { throw new RuntimeException(); }
  private  java.lang.String taskEndWithHadoopInputJsonString () { throw new RuntimeException(); }
  private  java.lang.String jobStartJsonString () { throw new RuntimeException(); }
  private  java.lang.String jobEndJsonString () { throw new RuntimeException(); }
  private  java.lang.String environmentUpdateJsonString () { throw new RuntimeException(); }
  private  java.lang.String blockManagerAddedJsonString () { throw new RuntimeException(); }
  private  java.lang.String blockManagerRemovedJsonString () { throw new RuntimeException(); }
  private  java.lang.String unpersistRDDJsonString () { throw new RuntimeException(); }
  private  java.lang.String applicationStartJsonString () { throw new RuntimeException(); }
  private  java.lang.String applicationEndJsonString () { throw new RuntimeException(); }
}
