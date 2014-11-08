package org.apache.spark.deploy.master;
public  class ZooKeeperPersistenceEngine implements org.apache.spark.deploy.master.PersistenceEngine, org.apache.spark.Logging {
  public   ZooKeeperPersistenceEngine (akka.serialization.Serialization serialization, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  java.lang.String WORKING_DIR () { throw new RuntimeException(); }
  public  org.apache.curator.framework.CuratorFramework zk () { throw new RuntimeException(); }
  public  void addApplication (org.apache.spark.deploy.master.ApplicationInfo app) { throw new RuntimeException(); }
  public  void removeApplication (org.apache.spark.deploy.master.ApplicationInfo app) { throw new RuntimeException(); }
  public  void addDriver (org.apache.spark.deploy.master.DriverInfo driver) { throw new RuntimeException(); }
  public  void removeDriver (org.apache.spark.deploy.master.DriverInfo driver) { throw new RuntimeException(); }
  public  void addWorker (org.apache.spark.deploy.master.WorkerInfo worker) { throw new RuntimeException(); }
  public  void removeWorker (org.apache.spark.deploy.master.WorkerInfo worker) { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
  public  scala.Tuple3<scala.collection.Seq<org.apache.spark.deploy.master.ApplicationInfo>, scala.collection.Seq<org.apache.spark.deploy.master.DriverInfo>, scala.collection.Seq<org.apache.spark.deploy.master.WorkerInfo>> readPersistedData () { throw new RuntimeException(); }
  private  void serializeIntoFile (java.lang.String path, java.lang.Object value) { throw new RuntimeException(); }
  public <T extends java.lang.Object> scala.Option<T> deserializeFromFile (java.lang.String filename, scala.reflect.Manifest<T> m) { throw new RuntimeException(); }
}
