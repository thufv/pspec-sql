package org.apache.spark.deploy.master;
private  class Master implements akka.actor.Actor, org.apache.spark.util.ActorLogReceive, org.apache.spark.Logging {
  static public  java.lang.String systemName () { throw new RuntimeException(); }
  static private  java.lang.String actorName () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex sparkUrlRegex () { throw new RuntimeException(); }
  static public  void main (java.lang.String[] argStrings) { throw new RuntimeException(); }
  /** Returns an `akka.tcp://...` URL for the Master actor given a sparkUrl `spark://host:ip`. */
  static public  java.lang.String toAkkaUrl (java.lang.String sparkUrl) { throw new RuntimeException(); }
  // not preceding
  static public  scala.Tuple3<akka.actor.ActorSystem, java.lang.Object, java.lang.Object> startSystemAndActor (java.lang.String host, int port, int webUiPort, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  org.apache.spark.SecurityManager securityMgr () { throw new RuntimeException(); }
  // not preceding
  public   Master (java.lang.String host, int port, int webUiPort, org.apache.spark.SecurityManager securityMgr) { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  public  java.text.SimpleDateFormat createDateFormat () { throw new RuntimeException(); }
  public  long WORKER_TIMEOUT () { throw new RuntimeException(); }
  public  int RETAINED_APPLICATIONS () { throw new RuntimeException(); }
  public  int RETAINED_DRIVERS () { throw new RuntimeException(); }
  public  int REAPER_ITERATIONS () { throw new RuntimeException(); }
  public  java.lang.String RECOVERY_DIR () { throw new RuntimeException(); }
  public  java.lang.String RECOVERY_MODE () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<org.apache.spark.deploy.master.WorkerInfo> workers () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.deploy.master.WorkerInfo> idToWorker () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<akka.actor.Address, org.apache.spark.deploy.master.WorkerInfo> addressToWorker () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<org.apache.spark.deploy.master.ApplicationInfo> apps () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.deploy.master.ApplicationInfo> idToApp () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<akka.actor.ActorRef, org.apache.spark.deploy.master.ApplicationInfo> actorToApp () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<akka.actor.Address, org.apache.spark.deploy.master.ApplicationInfo> addressToApp () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<org.apache.spark.deploy.master.ApplicationInfo> waitingApps () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<org.apache.spark.deploy.master.ApplicationInfo> completedApps () { throw new RuntimeException(); }
  public  int nextAppNumber () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, org.apache.spark.ui.SparkUI> appIdToUI () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashSet<org.apache.spark.deploy.master.DriverInfo> drivers () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<org.apache.spark.deploy.master.DriverInfo> completedDrivers () { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<org.apache.spark.deploy.master.DriverInfo> waitingDrivers () { throw new RuntimeException(); }
  public  int nextDriverNumber () { throw new RuntimeException(); }
  public  org.apache.spark.metrics.MetricsSystem masterMetricsSystem () { throw new RuntimeException(); }
  public  org.apache.spark.metrics.MetricsSystem applicationMetricsSystem () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.master.MasterSource masterSource () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.master.ui.MasterWebUI webUi () { throw new RuntimeException(); }
  public  java.lang.String masterPublicAddress () { throw new RuntimeException(); }
  public  java.lang.String masterUrl () { throw new RuntimeException(); }
  public  java.lang.String masterWebUiUrl () { throw new RuntimeException(); }
  public  scala.Enumeration.Value state () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.master.PersistenceEngine persistenceEngine () { throw new RuntimeException(); }
  public  akka.actor.ActorRef leaderElectionAgent () { throw new RuntimeException(); }
  private  akka.actor.Cancellable recoveryCompletionTask () { throw new RuntimeException(); }
  public  boolean spreadOutApps () { throw new RuntimeException(); }
  public  int defaultCores () { throw new RuntimeException(); }
  public  void preStart () { throw new RuntimeException(); }
  public  void preRestart (java.lang.Throwable reason, scala.Option<java.lang.Object> message) { throw new RuntimeException(); }
  public  void postStop () { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receiveWithLogging () { throw new RuntimeException(); }
  public  boolean canCompleteRecovery () { throw new RuntimeException(); }
  public  void beginRecovery (scala.collection.Seq<org.apache.spark.deploy.master.ApplicationInfo> storedApps, scala.collection.Seq<org.apache.spark.deploy.master.DriverInfo> storedDrivers, scala.collection.Seq<org.apache.spark.deploy.master.WorkerInfo> storedWorkers) { throw new RuntimeException(); }
  public  void completeRecovery () { throw new RuntimeException(); }
  /**
   * Can an app use the given worker? True if the worker has enough memory and we haven't already
   * launched an executor for the app on it (right now the standalone backend doesn't like having
   * two executors on the same worker).
   */
  public  boolean canUse (org.apache.spark.deploy.master.ApplicationInfo app, org.apache.spark.deploy.master.WorkerInfo worker) { throw new RuntimeException(); }
  /**
   * Schedule the currently available resources among waiting apps. This method will be called
   * every time a new app joins or resource availability changes.
   */
  private  void schedule () { throw new RuntimeException(); }
  public  void launchExecutor (org.apache.spark.deploy.master.WorkerInfo worker, org.apache.spark.deploy.master.ExecutorInfo exec) { throw new RuntimeException(); }
  public  boolean registerWorker (org.apache.spark.deploy.master.WorkerInfo worker) { throw new RuntimeException(); }
  public  void removeWorker (org.apache.spark.deploy.master.WorkerInfo worker) { throw new RuntimeException(); }
  public  void relaunchDriver (org.apache.spark.deploy.master.DriverInfo driver) { throw new RuntimeException(); }
  public  org.apache.spark.deploy.master.ApplicationInfo createApplication (org.apache.spark.deploy.ApplicationDescription desc, akka.actor.ActorRef driver) { throw new RuntimeException(); }
  public  void registerApplication (org.apache.spark.deploy.master.ApplicationInfo app) { throw new RuntimeException(); }
  public  void finishApplication (org.apache.spark.deploy.master.ApplicationInfo app) { throw new RuntimeException(); }
  public  void removeApplication (org.apache.spark.deploy.master.ApplicationInfo app, scala.Enumeration.Value state) { throw new RuntimeException(); }
  /**
   * Rebuild a new SparkUI from the given application's event logs.
   * Return whether this is successful.
   */
  public  boolean rebuildSparkUI (org.apache.spark.deploy.master.ApplicationInfo app) { throw new RuntimeException(); }
  /** Generate a new app ID given a app's submission date */
  public  java.lang.String newApplicationId (java.util.Date submitDate) { throw new RuntimeException(); }
  /** Check for, and remove, any timed-out workers */
  public  void timeOutDeadWorkers () { throw new RuntimeException(); }
  public  java.lang.String newDriverId (java.util.Date submitDate) { throw new RuntimeException(); }
  public  org.apache.spark.deploy.master.DriverInfo createDriver (org.apache.spark.deploy.DriverDescription desc) { throw new RuntimeException(); }
  public  void launchDriver (org.apache.spark.deploy.master.WorkerInfo worker, org.apache.spark.deploy.master.DriverInfo driver) { throw new RuntimeException(); }
  public  void removeDriver (java.lang.String driverId, scala.Enumeration.Value finalState, scala.Option<java.lang.Exception> exception) { throw new RuntimeException(); }
}
