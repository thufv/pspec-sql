package org.apache.spark.scheduler;
private  class FairSchedulableBuilder implements org.apache.spark.scheduler.SchedulableBuilder, org.apache.spark.Logging {
  public  org.apache.spark.scheduler.Pool rootPool () { throw new RuntimeException(); }
  // not preceding
  public   FairSchedulableBuilder (org.apache.spark.scheduler.Pool rootPool, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> schedulerAllocFile () { throw new RuntimeException(); }
  public  java.lang.String DEFAULT_SCHEDULER_FILE () { throw new RuntimeException(); }
  public  java.lang.String FAIR_SCHEDULER_PROPERTIES () { throw new RuntimeException(); }
  public  java.lang.String DEFAULT_POOL_NAME () { throw new RuntimeException(); }
  public  java.lang.String MINIMUM_SHARES_PROPERTY () { throw new RuntimeException(); }
  public  java.lang.String SCHEDULING_MODE_PROPERTY () { throw new RuntimeException(); }
  public  java.lang.String WEIGHT_PROPERTY () { throw new RuntimeException(); }
  public  java.lang.String POOL_NAME_PROPERTY () { throw new RuntimeException(); }
  public  java.lang.String POOLS_PROPERTY () { throw new RuntimeException(); }
  public  scala.Enumeration.Value DEFAULT_SCHEDULING_MODE () { throw new RuntimeException(); }
  public  int DEFAULT_MINIMUM_SHARE () { throw new RuntimeException(); }
  public  int DEFAULT_WEIGHT () { throw new RuntimeException(); }
  public  void buildPools () { throw new RuntimeException(); }
  private  void buildDefaultPool () { throw new RuntimeException(); }
  private  void buildFairSchedulerPool (java.io.InputStream is) { throw new RuntimeException(); }
  public  void addTaskSetManager (org.apache.spark.scheduler.Schedulable manager, java.util.Properties properties) { throw new RuntimeException(); }
}
