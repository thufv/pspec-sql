package org.apache.spark.deploy.master;
private  class ApplicationInfo implements scala.Serializable {
  public  long startTime () { throw new RuntimeException(); }
  public  java.lang.String id () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.ApplicationDescription desc () { throw new RuntimeException(); }
  public  java.util.Date submitDate () { throw new RuntimeException(); }
  public  akka.actor.ActorRef driver () { throw new RuntimeException(); }
  // not preceding
  public   ApplicationInfo (long startTime, java.lang.String id, org.apache.spark.deploy.ApplicationDescription desc, java.util.Date submitDate, akka.actor.ActorRef driver, int defaultCores) { throw new RuntimeException(); }
  public  scala.Enumeration.Value state () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.deploy.master.ExecutorInfo> executors () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<org.apache.spark.deploy.master.ExecutorInfo> removedExecutors () { throw new RuntimeException(); }
  public  int coresGranted () { throw new RuntimeException(); }
  public  long endTime () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.master.ApplicationSource appSource () { throw new RuntimeException(); }
  private  int nextExecutorId () { throw new RuntimeException(); }
  private  void readObject (java.io.ObjectInputStream in) { throw new RuntimeException(); }
  private  void init () { throw new RuntimeException(); }
  private  int newExecutorId (scala.Option<java.lang.Object> useID) { throw new RuntimeException(); }
  public  org.apache.spark.deploy.master.ExecutorInfo addExecutor (org.apache.spark.deploy.master.WorkerInfo worker, int cores, scala.Option<java.lang.Object> useID) { throw new RuntimeException(); }
  public  void removeExecutor (org.apache.spark.deploy.master.ExecutorInfo exec) { throw new RuntimeException(); }
  private  int myMaxCores () { throw new RuntimeException(); }
  public  int coresLeft () { throw new RuntimeException(); }
  private  int _retryCount () { throw new RuntimeException(); }
  public  int retryCount () { throw new RuntimeException(); }
  public  int incrementRetryCount () { throw new RuntimeException(); }
  public  void markFinished (scala.Enumeration.Value endState) { throw new RuntimeException(); }
  public  long duration () { throw new RuntimeException(); }
}
