package org.apache.spark.executor;
private  class MesosExecutorBackend implements org.apache.mesos.Executor, org.apache.spark.executor.ExecutorBackend, org.apache.spark.Logging {
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  public   MesosExecutorBackend () { throw new RuntimeException(); }
  public  org.apache.spark.executor.Executor executor () { throw new RuntimeException(); }
  public  org.apache.mesos.ExecutorDriver driver () { throw new RuntimeException(); }
  public  void statusUpdate (long taskId, scala.Enumeration.Value state, java.nio.ByteBuffer data) { throw new RuntimeException(); }
  public  void registered (org.apache.mesos.ExecutorDriver driver, org.apache.mesos.Protos.ExecutorInfo executorInfo, org.apache.mesos.Protos.FrameworkInfo frameworkInfo, org.apache.mesos.Protos.SlaveInfo slaveInfo) { throw new RuntimeException(); }
  public  void launchTask (org.apache.mesos.ExecutorDriver d, org.apache.mesos.Protos.TaskInfo taskInfo) { throw new RuntimeException(); }
  public  void error (org.apache.mesos.ExecutorDriver d, java.lang.String message) { throw new RuntimeException(); }
  public  void killTask (org.apache.mesos.ExecutorDriver d, org.apache.mesos.Protos.TaskID t) { throw new RuntimeException(); }
  public  void reregistered (org.apache.mesos.ExecutorDriver d, org.apache.mesos.Protos.SlaveInfo p2) { throw new RuntimeException(); }
  public  void disconnected (org.apache.mesos.ExecutorDriver d) { throw new RuntimeException(); }
  public  void frameworkMessage (org.apache.mesos.ExecutorDriver d, byte[] data) { throw new RuntimeException(); }
  public  void shutdown (org.apache.mesos.ExecutorDriver d) { throw new RuntimeException(); }
}
