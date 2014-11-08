package org.apache.spark.deploy.master;
private  class WorkerInfo implements scala.Serializable {
  public  java.lang.String id () { throw new RuntimeException(); }
  public  java.lang.String host () { throw new RuntimeException(); }
  public  int port () { throw new RuntimeException(); }
  public  int cores () { throw new RuntimeException(); }
  public  int memory () { throw new RuntimeException(); }
  public  akka.actor.ActorRef actor () { throw new RuntimeException(); }
  public  int webUiPort () { throw new RuntimeException(); }
  public  java.lang.String publicAddress () { throw new RuntimeException(); }
  // not preceding
  public   WorkerInfo (java.lang.String id, java.lang.String host, int port, int cores, int memory, akka.actor.ActorRef actor, int webUiPort, java.lang.String publicAddress) { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.deploy.master.ExecutorInfo> executors () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.deploy.master.DriverInfo> drivers () { throw new RuntimeException(); }
  public  scala.Enumeration.Value state () { throw new RuntimeException(); }
  public  int coresUsed () { throw new RuntimeException(); }
  public  int memoryUsed () { throw new RuntimeException(); }
  public  long lastHeartbeat () { throw new RuntimeException(); }
  public  int coresFree () { throw new RuntimeException(); }
  public  int memoryFree () { throw new RuntimeException(); }
  private  void readObject (java.io.ObjectInputStream in) { throw new RuntimeException(); }
  private  void init () { throw new RuntimeException(); }
  public  java.lang.String hostPort () { throw new RuntimeException(); }
  public  void addExecutor (org.apache.spark.deploy.master.ExecutorInfo exec) { throw new RuntimeException(); }
  public  void removeExecutor (org.apache.spark.deploy.master.ExecutorInfo exec) { throw new RuntimeException(); }
  public  boolean hasExecutor (org.apache.spark.deploy.master.ApplicationInfo app) { throw new RuntimeException(); }
  public  void addDriver (org.apache.spark.deploy.master.DriverInfo driver) { throw new RuntimeException(); }
  public  void removeDriver (org.apache.spark.deploy.master.DriverInfo driver) { throw new RuntimeException(); }
  public  java.lang.String webUiAddress () { throw new RuntimeException(); }
  public  void setState (scala.Enumeration.Value state) { throw new RuntimeException(); }
}
