package org.apache.spark.scheduler;
/**
 * A backend interface for scheduling systems that allows plugging in different ones under
 * TaskSchedulerImpl. We assume a Mesos-like model where the application gets resource offers as
 * machines become available and can launch tasks on them.
 */
private abstract interface SchedulerBackend {
  public abstract  void start () ;
  public abstract  void stop () ;
  public abstract  void reviveOffers () ;
  public abstract  int defaultParallelism () ;
  public  void killTask (long taskId, java.lang.String executorId, boolean interruptThread) ;
  public  boolean isReady () ;
}
