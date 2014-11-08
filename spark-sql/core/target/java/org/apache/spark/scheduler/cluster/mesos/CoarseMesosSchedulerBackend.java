package org.apache.spark.scheduler.cluster.mesos;
/**
 * A SchedulerBackend that runs tasks on Mesos, but uses "coarse-grained" tasks, where it holds
 * onto each Mesos node for the duration of the Spark job instead of relinquishing cores whenever
 * a task is done. It launches Spark tasks within the coarse-grained Mesos tasks using the
 * CoarseGrainedSchedulerBackend mechanism. This class is useful for lower and more predictable
 * latency.
 * <p>
 * Unfortunately this has a bit of duplication from MesosSchedulerBackend, but it seems hard to
 * remove this.
 */
private  class CoarseMesosSchedulerBackend extends org.apache.spark.scheduler.cluster.CoarseGrainedSchedulerBackend implements org.apache.mesos.Scheduler, org.apache.spark.Logging {
  public   CoarseMesosSchedulerBackend (org.apache.spark.scheduler.TaskSchedulerImpl scheduler, org.apache.spark.SparkContext sc, java.lang.String master) { throw new RuntimeException(); }
  public  int MAX_SLAVE_FAILURES () { throw new RuntimeException(); }
  public  boolean isRegistered () { throw new RuntimeException(); }
  public  java.lang.Object registeredLock () { throw new RuntimeException(); }
  public  org.apache.mesos.SchedulerDriver driver () { throw new RuntimeException(); }
  public  int maxCores () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, java.lang.Object> coresByTaskId () { throw new RuntimeException(); }
  public  int totalCoresAcquired () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<java.lang.String> slaveIdsWithExecutors () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, java.lang.String> taskIdToSlaveId () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, java.lang.Object> failuresBySlaveId () { throw new RuntimeException(); }
  public  java.lang.String executorSparkHome () { throw new RuntimeException(); }
  public  int extraCoresPerSlave () { throw new RuntimeException(); }
  public  int nextMesosTaskId () { throw new RuntimeException(); }
  public  int newMesosTaskId () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  org.apache.mesos.Protos.CommandInfo createCommand (org.apache.mesos.Protos.Offer offer, int numCores) { throw new RuntimeException(); }
  public  void offerRescinded (org.apache.mesos.SchedulerDriver d, org.apache.mesos.Protos.OfferID o) { throw new RuntimeException(); }
  public  void registered (org.apache.mesos.SchedulerDriver d, org.apache.mesos.Protos.FrameworkID frameworkId, org.apache.mesos.Protos.MasterInfo masterInfo) { throw new RuntimeException(); }
  public  void waitForRegister () { throw new RuntimeException(); }
  public  void disconnected (org.apache.mesos.SchedulerDriver d) { throw new RuntimeException(); }
  public  void reregistered (org.apache.mesos.SchedulerDriver d, org.apache.mesos.Protos.MasterInfo masterInfo) { throw new RuntimeException(); }
  /**
   * Method called by Mesos to offer resources on slaves. We respond by launching an executor,
   * unless we've already launched more than we wanted to.
   */
  public  void resourceOffers (org.apache.mesos.SchedulerDriver d, java.util.List<org.apache.mesos.Protos.Offer> offers) { throw new RuntimeException(); }
  /** Helper function to pull out a resource from a Mesos Resources protobuf */
  private  double getResource (java.util.List<org.apache.mesos.Protos.Resource> res, java.lang.String name) { throw new RuntimeException(); }
  /** Build a Mesos resource protobuf object */
  private  org.apache.mesos.Protos.Resource createResource (java.lang.String resourceName, double quantity) { throw new RuntimeException(); }
  /** Check whether a Mesos task state represents a finished task */
  private  boolean isFinished (org.apache.mesos.Protos.TaskState state) { throw new RuntimeException(); }
  public  void statusUpdate (org.apache.mesos.SchedulerDriver d, org.apache.mesos.Protos.TaskStatus status) { throw new RuntimeException(); }
  public  void error (org.apache.mesos.SchedulerDriver d, java.lang.String message) { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
  public  void frameworkMessage (org.apache.mesos.SchedulerDriver d, org.apache.mesos.Protos.ExecutorID e, org.apache.mesos.Protos.SlaveID s, byte[] b) { throw new RuntimeException(); }
  public  void slaveLost (org.apache.mesos.SchedulerDriver d, org.apache.mesos.Protos.SlaveID slaveId) { throw new RuntimeException(); }
  public  void executorLost (org.apache.mesos.SchedulerDriver d, org.apache.mesos.Protos.ExecutorID e, org.apache.mesos.Protos.SlaveID s, int status) { throw new RuntimeException(); }
}
