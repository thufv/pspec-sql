package org.apache.spark.deploy.master;
/**
 * Stores data in a single on-disk directory with one file per application and worker.
 * Files are deleted when applications and workers are removed.
 * <p>
 * @param dir Directory to store files. Created if non-existent (but not recursively).
 * @param serialization Used to serialize our objects.
 */
private  class FileSystemPersistenceEngine implements org.apache.spark.deploy.master.PersistenceEngine, org.apache.spark.Logging {
  public  java.lang.String dir () { throw new RuntimeException(); }
  public  akka.serialization.Serialization serialization () { throw new RuntimeException(); }
  // not preceding
  public   FileSystemPersistenceEngine (java.lang.String dir, akka.serialization.Serialization serialization) { throw new RuntimeException(); }
  public  void addApplication (org.apache.spark.deploy.master.ApplicationInfo app) { throw new RuntimeException(); }
  public  void removeApplication (org.apache.spark.deploy.master.ApplicationInfo app) { throw new RuntimeException(); }
  public  void addDriver (org.apache.spark.deploy.master.DriverInfo driver) { throw new RuntimeException(); }
  public  void removeDriver (org.apache.spark.deploy.master.DriverInfo driver) { throw new RuntimeException(); }
  public  void addWorker (org.apache.spark.deploy.master.WorkerInfo worker) { throw new RuntimeException(); }
  public  void removeWorker (org.apache.spark.deploy.master.WorkerInfo worker) { throw new RuntimeException(); }
  public  scala.Tuple3<scala.collection.Seq<org.apache.spark.deploy.master.ApplicationInfo>, scala.collection.Seq<org.apache.spark.deploy.master.DriverInfo>, scala.collection.Seq<org.apache.spark.deploy.master.WorkerInfo>> readPersistedData () { throw new RuntimeException(); }
  private  void serializeIntoFile (java.io.File file, java.lang.Object value) { throw new RuntimeException(); }
  public <T extends java.lang.Object> T deserializeFromFile (java.io.File file, scala.reflect.Manifest<T> m) { throw new RuntimeException(); }
}
