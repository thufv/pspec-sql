package org.apache.spark.scheduler;
private  class FIFOSchedulableBuilder implements org.apache.spark.scheduler.SchedulableBuilder, org.apache.spark.Logging {
  public  org.apache.spark.scheduler.Pool rootPool () { throw new RuntimeException(); }
  // not preceding
  public   FIFOSchedulableBuilder (org.apache.spark.scheduler.Pool rootPool) { throw new RuntimeException(); }
  public  void buildPools () { throw new RuntimeException(); }
  public  void addTaskSetManager (org.apache.spark.scheduler.Schedulable manager, java.util.Properties properties) { throw new RuntimeException(); }
}
