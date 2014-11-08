package org.apache.spark.scheduler;
public  class FakeSchedulerBackend implements org.apache.spark.scheduler.SchedulerBackend {
  public   FakeSchedulerBackend () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  public  void reviveOffers () { throw new RuntimeException(); }
  public  int defaultParallelism () { throw new RuntimeException(); }
}
