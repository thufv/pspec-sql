package org.apache.spark.deploy.master;
/**
 * Allows Master to persist any state that is necessary in order to recover from a failure.
 * The following semantics are required:
 *   - addApplication and addWorker are called before completing registration of a new app/worker.
 *   - removeApplication and removeWorker are called at any time.
 * Given these two requirements, we will have all apps and workers persisted, but
 * we might not have yet deleted apps or workers that finished (so their liveness must be verified
 * during recovery).
 */
private abstract interface PersistenceEngine {
  public abstract  void addApplication (org.apache.spark.deploy.master.ApplicationInfo app) ;
  public abstract  void removeApplication (org.apache.spark.deploy.master.ApplicationInfo app) ;
  public abstract  void addWorker (org.apache.spark.deploy.master.WorkerInfo worker) ;
  public abstract  void removeWorker (org.apache.spark.deploy.master.WorkerInfo worker) ;
  public abstract  void addDriver (org.apache.spark.deploy.master.DriverInfo driver) ;
  public abstract  void removeDriver (org.apache.spark.deploy.master.DriverInfo driver) ;
  /**
   * Returns the persisted data sorted by their respective ids (which implies that they're
   * sorted by time of creation).
   */
  public abstract  scala.Tuple3<scala.collection.Seq<org.apache.spark.deploy.master.ApplicationInfo>, scala.collection.Seq<org.apache.spark.deploy.master.DriverInfo>, scala.collection.Seq<org.apache.spark.deploy.master.WorkerInfo>> readPersistedData () ;
  public  void close () ;
}
