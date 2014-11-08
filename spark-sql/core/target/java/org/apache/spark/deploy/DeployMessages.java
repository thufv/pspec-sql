package org.apache.spark.deploy;
// no position
/** Contains messages sent between Scheduler actor nodes. */
private  class DeployMessages {
  static public  class RegisterWorker implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String id () { throw new RuntimeException(); }
    public  java.lang.String host () { throw new RuntimeException(); }
    public  int port () { throw new RuntimeException(); }
    public  int cores () { throw new RuntimeException(); }
    public  int memory () { throw new RuntimeException(); }
    public  int webUiPort () { throw new RuntimeException(); }
    public  java.lang.String publicAddress () { throw new RuntimeException(); }
    // not preceding
    public   RegisterWorker (java.lang.String id, java.lang.String host, int port, int cores, int memory, int webUiPort, java.lang.String publicAddress) { throw new RuntimeException(); }
  }
  // no position
  static public  class RegisterWorker$ extends scala.runtime.AbstractFunction7<java.lang.String, java.lang.String, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.String, org.apache.spark.deploy.DeployMessages.RegisterWorker> implements scala.Serializable {
    public   RegisterWorker$ () { throw new RuntimeException(); }
  }
  static public  class ExecutorStateChanged implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String appId () { throw new RuntimeException(); }
    public  int execId () { throw new RuntimeException(); }
    public  scala.Enumeration.Value state () { throw new RuntimeException(); }
    public  scala.Option<java.lang.String> message () { throw new RuntimeException(); }
    public  scala.Option<java.lang.Object> exitStatus () { throw new RuntimeException(); }
    // not preceding
    public   ExecutorStateChanged (java.lang.String appId, int execId, scala.Enumeration.Value state, scala.Option<java.lang.String> message, scala.Option<java.lang.Object> exitStatus) { throw new RuntimeException(); }
  }
  // no position
  static public  class ExecutorStateChanged$ extends scala.runtime.AbstractFunction5<java.lang.String, java.lang.Object, scala.Enumeration.Value, scala.Option<java.lang.String>, scala.Option<java.lang.Object>, org.apache.spark.deploy.DeployMessages.ExecutorStateChanged> implements scala.Serializable {
    public   ExecutorStateChanged$ () { throw new RuntimeException(); }
  }
  static public  class DriverStateChanged implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String driverId () { throw new RuntimeException(); }
    public  scala.Enumeration.Value state () { throw new RuntimeException(); }
    public  scala.Option<java.lang.Exception> exception () { throw new RuntimeException(); }
    // not preceding
    public   DriverStateChanged (java.lang.String driverId, scala.Enumeration.Value state, scala.Option<java.lang.Exception> exception) { throw new RuntimeException(); }
  }
  // no position
  static public  class DriverStateChanged$ extends scala.runtime.AbstractFunction3<java.lang.String, scala.Enumeration.Value, scala.Option<java.lang.Exception>, org.apache.spark.deploy.DeployMessages.DriverStateChanged> implements scala.Serializable {
    public   DriverStateChanged$ () { throw new RuntimeException(); }
  }
  static public  class WorkerSchedulerStateResponse implements scala.Product, scala.Serializable {
    public  java.lang.String id () { throw new RuntimeException(); }
    public  scala.collection.immutable.List<org.apache.spark.deploy.ExecutorDescription> executors () { throw new RuntimeException(); }
    public  scala.collection.Seq<java.lang.String> driverIds () { throw new RuntimeException(); }
    // not preceding
    public   WorkerSchedulerStateResponse (java.lang.String id, scala.collection.immutable.List<org.apache.spark.deploy.ExecutorDescription> executors, scala.collection.Seq<java.lang.String> driverIds) { throw new RuntimeException(); }
  }
  // no position
  static public  class WorkerSchedulerStateResponse$ extends scala.runtime.AbstractFunction3<java.lang.String, scala.collection.immutable.List<org.apache.spark.deploy.ExecutorDescription>, scala.collection.Seq<java.lang.String>, org.apache.spark.deploy.DeployMessages.WorkerSchedulerStateResponse> implements scala.Serializable {
    public   WorkerSchedulerStateResponse$ () { throw new RuntimeException(); }
  }
  static public  class Heartbeat implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String workerId () { throw new RuntimeException(); }
    // not preceding
    public   Heartbeat (java.lang.String workerId) { throw new RuntimeException(); }
  }
  // no position
  static public  class Heartbeat$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.deploy.DeployMessages.Heartbeat> implements scala.Serializable {
    public   Heartbeat$ () { throw new RuntimeException(); }
  }
  static public  class RegisteredWorker implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String masterUrl () { throw new RuntimeException(); }
    public  java.lang.String masterWebUiUrl () { throw new RuntimeException(); }
    // not preceding
    public   RegisteredWorker (java.lang.String masterUrl, java.lang.String masterWebUiUrl) { throw new RuntimeException(); }
  }
  // no position
  static public  class RegisteredWorker$ extends scala.runtime.AbstractFunction2<java.lang.String, java.lang.String, org.apache.spark.deploy.DeployMessages.RegisteredWorker> implements scala.Serializable {
    public   RegisteredWorker$ () { throw new RuntimeException(); }
  }
  static public  class RegisterWorkerFailed implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String message () { throw new RuntimeException(); }
    // not preceding
    public   RegisterWorkerFailed (java.lang.String message) { throw new RuntimeException(); }
  }
  // no position
  static public  class RegisterWorkerFailed$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.deploy.DeployMessages.RegisterWorkerFailed> implements scala.Serializable {
    public   RegisterWorkerFailed$ () { throw new RuntimeException(); }
  }
  static public  class KillExecutor implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String masterUrl () { throw new RuntimeException(); }
    public  java.lang.String appId () { throw new RuntimeException(); }
    public  int execId () { throw new RuntimeException(); }
    // not preceding
    public   KillExecutor (java.lang.String masterUrl, java.lang.String appId, int execId) { throw new RuntimeException(); }
  }
  // no position
  static public  class KillExecutor$ extends scala.runtime.AbstractFunction3<java.lang.String, java.lang.String, java.lang.Object, org.apache.spark.deploy.DeployMessages.KillExecutor> implements scala.Serializable {
    public   KillExecutor$ () { throw new RuntimeException(); }
  }
  static public  class LaunchExecutor implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String masterUrl () { throw new RuntimeException(); }
    public  java.lang.String appId () { throw new RuntimeException(); }
    public  int execId () { throw new RuntimeException(); }
    public  org.apache.spark.deploy.ApplicationDescription appDesc () { throw new RuntimeException(); }
    public  int cores () { throw new RuntimeException(); }
    public  int memory () { throw new RuntimeException(); }
    // not preceding
    public   LaunchExecutor (java.lang.String masterUrl, java.lang.String appId, int execId, org.apache.spark.deploy.ApplicationDescription appDesc, int cores, int memory) { throw new RuntimeException(); }
  }
  // no position
  static public  class LaunchExecutor$ extends scala.runtime.AbstractFunction6<java.lang.String, java.lang.String, java.lang.Object, org.apache.spark.deploy.ApplicationDescription, java.lang.Object, java.lang.Object, org.apache.spark.deploy.DeployMessages.LaunchExecutor> implements scala.Serializable {
    public   LaunchExecutor$ () { throw new RuntimeException(); }
  }
  static public  class LaunchDriver implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String driverId () { throw new RuntimeException(); }
    public  org.apache.spark.deploy.DriverDescription driverDesc () { throw new RuntimeException(); }
    // not preceding
    public   LaunchDriver (java.lang.String driverId, org.apache.spark.deploy.DriverDescription driverDesc) { throw new RuntimeException(); }
  }
  // no position
  static public  class LaunchDriver$ extends scala.runtime.AbstractFunction2<java.lang.String, org.apache.spark.deploy.DriverDescription, org.apache.spark.deploy.DeployMessages.LaunchDriver> implements scala.Serializable {
    public   LaunchDriver$ () { throw new RuntimeException(); }
  }
  static public  class KillDriver implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String driverId () { throw new RuntimeException(); }
    // not preceding
    public   KillDriver (java.lang.String driverId) { throw new RuntimeException(); }
  }
  // no position
  static public  class KillDriver$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.deploy.DeployMessages.KillDriver> implements scala.Serializable {
    public   KillDriver$ () { throw new RuntimeException(); }
  }
  // no position
  static public  class WorkDirCleanup$ implements scala.Product, scala.Serializable {
    public   WorkDirCleanup$ () { throw new RuntimeException(); }
  }
  static public  class RegisterApplication implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  org.apache.spark.deploy.ApplicationDescription appDescription () { throw new RuntimeException(); }
    // not preceding
    public   RegisterApplication (org.apache.spark.deploy.ApplicationDescription appDescription) { throw new RuntimeException(); }
  }
  // no position
  static public  class RegisterApplication$ extends scala.runtime.AbstractFunction1<org.apache.spark.deploy.ApplicationDescription, org.apache.spark.deploy.DeployMessages.RegisterApplication> implements scala.Serializable {
    public   RegisterApplication$ () { throw new RuntimeException(); }
  }
  static public  class MasterChangeAcknowledged implements scala.Product, scala.Serializable {
    public  java.lang.String appId () { throw new RuntimeException(); }
    // not preceding
    public   MasterChangeAcknowledged (java.lang.String appId) { throw new RuntimeException(); }
  }
  // no position
  static public  class MasterChangeAcknowledged$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.deploy.DeployMessages.MasterChangeAcknowledged> implements scala.Serializable {
    public   MasterChangeAcknowledged$ () { throw new RuntimeException(); }
  }
  static public  class RegisteredApplication implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String appId () { throw new RuntimeException(); }
    public  java.lang.String masterUrl () { throw new RuntimeException(); }
    // not preceding
    public   RegisteredApplication (java.lang.String appId, java.lang.String masterUrl) { throw new RuntimeException(); }
  }
  // no position
  static public  class RegisteredApplication$ extends scala.runtime.AbstractFunction2<java.lang.String, java.lang.String, org.apache.spark.deploy.DeployMessages.RegisteredApplication> implements scala.Serializable {
    public   RegisteredApplication$ () { throw new RuntimeException(); }
  }
  static public  class ExecutorAdded implements scala.Product, scala.Serializable {
    public  int id () { throw new RuntimeException(); }
    public  java.lang.String workerId () { throw new RuntimeException(); }
    public  java.lang.String hostPort () { throw new RuntimeException(); }
    public  int cores () { throw new RuntimeException(); }
    public  int memory () { throw new RuntimeException(); }
    // not preceding
    public   ExecutorAdded (int id, java.lang.String workerId, java.lang.String hostPort, int cores, int memory) { throw new RuntimeException(); }
  }
  // no position
  static public  class ExecutorAdded$ extends scala.runtime.AbstractFunction5<java.lang.Object, java.lang.String, java.lang.String, java.lang.Object, java.lang.Object, org.apache.spark.deploy.DeployMessages.ExecutorAdded> implements scala.Serializable {
    public   ExecutorAdded$ () { throw new RuntimeException(); }
  }
  static public  class ExecutorUpdated implements scala.Product, scala.Serializable {
    public  int id () { throw new RuntimeException(); }
    public  scala.Enumeration.Value state () { throw new RuntimeException(); }
    public  scala.Option<java.lang.String> message () { throw new RuntimeException(); }
    public  scala.Option<java.lang.Object> exitStatus () { throw new RuntimeException(); }
    // not preceding
    public   ExecutorUpdated (int id, scala.Enumeration.Value state, scala.Option<java.lang.String> message, scala.Option<java.lang.Object> exitStatus) { throw new RuntimeException(); }
  }
  // no position
  static public  class ExecutorUpdated$ extends scala.runtime.AbstractFunction4<java.lang.Object, scala.Enumeration.Value, scala.Option<java.lang.String>, scala.Option<java.lang.Object>, org.apache.spark.deploy.DeployMessages.ExecutorUpdated> implements scala.Serializable {
    public   ExecutorUpdated$ () { throw new RuntimeException(); }
  }
  static public  class ApplicationRemoved implements scala.Product, scala.Serializable {
    public  java.lang.String message () { throw new RuntimeException(); }
    // not preceding
    public   ApplicationRemoved (java.lang.String message) { throw new RuntimeException(); }
  }
  // no position
  static public  class ApplicationRemoved$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.deploy.DeployMessages.ApplicationRemoved> implements scala.Serializable {
    public   ApplicationRemoved$ () { throw new RuntimeException(); }
  }
  static public  class RequestSubmitDriver implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  org.apache.spark.deploy.DriverDescription driverDescription () { throw new RuntimeException(); }
    // not preceding
    public   RequestSubmitDriver (org.apache.spark.deploy.DriverDescription driverDescription) { throw new RuntimeException(); }
  }
  // no position
  static public  class RequestSubmitDriver$ extends scala.runtime.AbstractFunction1<org.apache.spark.deploy.DriverDescription, org.apache.spark.deploy.DeployMessages.RequestSubmitDriver> implements scala.Serializable {
    public   RequestSubmitDriver$ () { throw new RuntimeException(); }
  }
  static public  class SubmitDriverResponse implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  boolean success () { throw new RuntimeException(); }
    public  scala.Option<java.lang.String> driverId () { throw new RuntimeException(); }
    public  java.lang.String message () { throw new RuntimeException(); }
    // not preceding
    public   SubmitDriverResponse (boolean success, scala.Option<java.lang.String> driverId, java.lang.String message) { throw new RuntimeException(); }
  }
  // no position
  static public  class SubmitDriverResponse$ extends scala.runtime.AbstractFunction3<java.lang.Object, scala.Option<java.lang.String>, java.lang.String, org.apache.spark.deploy.DeployMessages.SubmitDriverResponse> implements scala.Serializable {
    public   SubmitDriverResponse$ () { throw new RuntimeException(); }
  }
  static public  class RequestKillDriver implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String driverId () { throw new RuntimeException(); }
    // not preceding
    public   RequestKillDriver (java.lang.String driverId) { throw new RuntimeException(); }
  }
  // no position
  static public  class RequestKillDriver$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.deploy.DeployMessages.RequestKillDriver> implements scala.Serializable {
    public   RequestKillDriver$ () { throw new RuntimeException(); }
  }
  static public  class KillDriverResponse implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String driverId () { throw new RuntimeException(); }
    public  boolean success () { throw new RuntimeException(); }
    public  java.lang.String message () { throw new RuntimeException(); }
    // not preceding
    public   KillDriverResponse (java.lang.String driverId, boolean success, java.lang.String message) { throw new RuntimeException(); }
  }
  // no position
  static public  class KillDriverResponse$ extends scala.runtime.AbstractFunction3<java.lang.String, java.lang.Object, java.lang.String, org.apache.spark.deploy.DeployMessages.KillDriverResponse> implements scala.Serializable {
    public   KillDriverResponse$ () { throw new RuntimeException(); }
  }
  static public  class RequestDriverStatus implements org.apache.spark.deploy.DeployMessage, scala.Product, scala.Serializable {
    public  java.lang.String driverId () { throw new RuntimeException(); }
    // not preceding
    public   RequestDriverStatus (java.lang.String driverId) { throw new RuntimeException(); }
  }
  // no position
  static public  class RequestDriverStatus$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.deploy.DeployMessages.RequestDriverStatus> implements scala.Serializable {
    public   RequestDriverStatus$ () { throw new RuntimeException(); }
  }
  static public  class DriverStatusResponse implements scala.Product, scala.Serializable {
    public  boolean found () { throw new RuntimeException(); }
    public  scala.Option<scala.Enumeration.Value> state () { throw new RuntimeException(); }
    public  scala.Option<java.lang.String> workerId () { throw new RuntimeException(); }
    public  scala.Option<java.lang.String> workerHostPort () { throw new RuntimeException(); }
    public  scala.Option<java.lang.Exception> exception () { throw new RuntimeException(); }
    // not preceding
    public   DriverStatusResponse (boolean found, scala.Option<scala.Enumeration.Value> state, scala.Option<java.lang.String> workerId, scala.Option<java.lang.String> workerHostPort, scala.Option<java.lang.Exception> exception) { throw new RuntimeException(); }
  }
  // no position
  static public  class DriverStatusResponse$ extends scala.runtime.AbstractFunction5<java.lang.Object, scala.Option<scala.Enumeration.Value>, scala.Option<java.lang.String>, scala.Option<java.lang.String>, scala.Option<java.lang.Exception>, org.apache.spark.deploy.DeployMessages.DriverStatusResponse> implements scala.Serializable {
    public   DriverStatusResponse$ () { throw new RuntimeException(); }
  }
  // no position
  static public  class StopAppClient$ implements scala.Product, scala.Serializable {
    public   StopAppClient$ () { throw new RuntimeException(); }
  }
  static public  class MasterChanged implements scala.Product, scala.Serializable {
    public  java.lang.String masterUrl () { throw new RuntimeException(); }
    public  java.lang.String masterWebUiUrl () { throw new RuntimeException(); }
    // not preceding
    public   MasterChanged (java.lang.String masterUrl, java.lang.String masterWebUiUrl) { throw new RuntimeException(); }
  }
  // no position
  static public  class MasterChanged$ extends scala.runtime.AbstractFunction2<java.lang.String, java.lang.String, org.apache.spark.deploy.DeployMessages.MasterChanged> implements scala.Serializable {
    public   MasterChanged$ () { throw new RuntimeException(); }
  }
  // no position
  static public  class RequestMasterState$ implements scala.Product, scala.Serializable {
    public   RequestMasterState$ () { throw new RuntimeException(); }
  }
  static public  class MasterStateResponse implements scala.Product, scala.Serializable {
    public  java.lang.String host () { throw new RuntimeException(); }
    public  int port () { throw new RuntimeException(); }
    public  org.apache.spark.deploy.master.WorkerInfo[] workers () { throw new RuntimeException(); }
    public  org.apache.spark.deploy.master.ApplicationInfo[] activeApps () { throw new RuntimeException(); }
    public  org.apache.spark.deploy.master.ApplicationInfo[] completedApps () { throw new RuntimeException(); }
    public  org.apache.spark.deploy.master.DriverInfo[] activeDrivers () { throw new RuntimeException(); }
    public  org.apache.spark.deploy.master.DriverInfo[] completedDrivers () { throw new RuntimeException(); }
    public  scala.Enumeration.Value status () { throw new RuntimeException(); }
    // not preceding
    public   MasterStateResponse (java.lang.String host, int port, org.apache.spark.deploy.master.WorkerInfo[] workers, org.apache.spark.deploy.master.ApplicationInfo[] activeApps, org.apache.spark.deploy.master.ApplicationInfo[] completedApps, org.apache.spark.deploy.master.DriverInfo[] activeDrivers, org.apache.spark.deploy.master.DriverInfo[] completedDrivers, scala.Enumeration.Value status) { throw new RuntimeException(); }
    public  java.lang.String uri () { throw new RuntimeException(); }
  }
  // no position
  static public  class MasterStateResponse$ extends scala.runtime.AbstractFunction8<java.lang.String, java.lang.Object, org.apache.spark.deploy.master.WorkerInfo[], org.apache.spark.deploy.master.ApplicationInfo[], org.apache.spark.deploy.master.ApplicationInfo[], org.apache.spark.deploy.master.DriverInfo[], org.apache.spark.deploy.master.DriverInfo[], scala.Enumeration.Value, org.apache.spark.deploy.DeployMessages.MasterStateResponse> implements scala.Serializable {
    public   MasterStateResponse$ () { throw new RuntimeException(); }
  }
  // no position
  static public  class RequestWorkerState$ implements scala.Product, scala.Serializable {
    public   RequestWorkerState$ () { throw new RuntimeException(); }
  }
  static public  class WorkerStateResponse implements scala.Product, scala.Serializable {
    public  java.lang.String host () { throw new RuntimeException(); }
    public  int port () { throw new RuntimeException(); }
    public  java.lang.String workerId () { throw new RuntimeException(); }
    public  scala.collection.immutable.List<org.apache.spark.deploy.worker.ExecutorRunner> executors () { throw new RuntimeException(); }
    public  scala.collection.immutable.List<org.apache.spark.deploy.worker.ExecutorRunner> finishedExecutors () { throw new RuntimeException(); }
    public  scala.collection.immutable.List<org.apache.spark.deploy.worker.DriverRunner> drivers () { throw new RuntimeException(); }
    public  scala.collection.immutable.List<org.apache.spark.deploy.worker.DriverRunner> finishedDrivers () { throw new RuntimeException(); }
    public  java.lang.String masterUrl () { throw new RuntimeException(); }
    public  int cores () { throw new RuntimeException(); }
    public  int memory () { throw new RuntimeException(); }
    public  int coresUsed () { throw new RuntimeException(); }
    public  int memoryUsed () { throw new RuntimeException(); }
    public  java.lang.String masterWebUiUrl () { throw new RuntimeException(); }
    // not preceding
    public   WorkerStateResponse (java.lang.String host, int port, java.lang.String workerId, scala.collection.immutable.List<org.apache.spark.deploy.worker.ExecutorRunner> executors, scala.collection.immutable.List<org.apache.spark.deploy.worker.ExecutorRunner> finishedExecutors, scala.collection.immutable.List<org.apache.spark.deploy.worker.DriverRunner> drivers, scala.collection.immutable.List<org.apache.spark.deploy.worker.DriverRunner> finishedDrivers, java.lang.String masterUrl, int cores, int memory, int coresUsed, int memoryUsed, java.lang.String masterWebUiUrl) { throw new RuntimeException(); }
  }
  // no position
  static public  class WorkerStateResponse$ extends scala.runtime.AbstractFunction13<java.lang.String, java.lang.Object, java.lang.String, scala.collection.immutable.List<org.apache.spark.deploy.worker.ExecutorRunner>, scala.collection.immutable.List<org.apache.spark.deploy.worker.ExecutorRunner>, scala.collection.immutable.List<org.apache.spark.deploy.worker.DriverRunner>, scala.collection.immutable.List<org.apache.spark.deploy.worker.DriverRunner>, java.lang.String, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.String, org.apache.spark.deploy.DeployMessages.WorkerStateResponse> implements scala.Serializable {
    public   WorkerStateResponse$ () { throw new RuntimeException(); }
  }
  // no position
  static public  class SendHeartbeat$ implements scala.Product, scala.Serializable {
    public   SendHeartbeat$ () { throw new RuntimeException(); }
  }
}
