package org.apache.spark.scheduler;
/**
 * An interface to build Schedulable tree
 * buildPools: build the tree nodes(pools)
 * addTaskSetManager: build the leaf nodes(TaskSetManagers)
 */
private  interface SchedulableBuilder {
  public abstract  org.apache.spark.scheduler.Pool rootPool () ;
  public abstract  void buildPools () ;
  public abstract  void addTaskSetManager (org.apache.spark.scheduler.Schedulable manager, java.util.Properties properties) ;
}
