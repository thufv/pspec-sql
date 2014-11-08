package org.apache.spark.deploy.master;
private  class BlackHolePersistenceEngine implements org.apache.spark.deploy.master.PersistenceEngine {
  public   BlackHolePersistenceEngine () { throw new RuntimeException(); }
  public  void addApplication (org.apache.spark.deploy.master.ApplicationInfo app) { throw new RuntimeException(); }
  public  void removeApplication (org.apache.spark.deploy.master.ApplicationInfo app) { throw new RuntimeException(); }
  public  void addWorker (org.apache.spark.deploy.master.WorkerInfo worker) { throw new RuntimeException(); }
  public  void removeWorker (org.apache.spark.deploy.master.WorkerInfo worker) { throw new RuntimeException(); }
  public  void addDriver (org.apache.spark.deploy.master.DriverInfo driver) { throw new RuntimeException(); }
  public  void removeDriver (org.apache.spark.deploy.master.DriverInfo driver) { throw new RuntimeException(); }
  public  scala.Tuple3<scala.collection.immutable.Nil$, scala.collection.immutable.Nil$, scala.collection.immutable.Nil$> readPersistedData () { throw new RuntimeException(); }
}
