package org.apache.spark.scheduler;
public  class TaskSchedulerImplSuite extends org.scalatest.FunSuite implements org.apache.spark.LocalSparkContext, org.apache.spark.Logging {
  public   TaskSchedulerImplSuite () { throw new RuntimeException(); }
  public  org.apache.spark.scheduler.FakeTaskSetManager createDummyTaskSetManager (int priority, int stage, int numTasks, org.apache.spark.scheduler.TaskSchedulerImpl cs, org.apache.spark.scheduler.TaskSet taskSet) { throw new RuntimeException(); }
  public  int resourceOffer (org.apache.spark.scheduler.Pool rootPool) { throw new RuntimeException(); }
  public  void checkTaskSetId (org.apache.spark.scheduler.Pool rootPool, int expectedTaskSetId) { throw new RuntimeException(); }
}
