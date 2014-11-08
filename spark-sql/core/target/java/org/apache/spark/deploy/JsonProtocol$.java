package org.apache.spark.deploy;
// no position
private  class JsonProtocol$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final JsonProtocol$ MODULE$ = null;
  public   JsonProtocol$ () { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JObject writeWorkerInfo (org.apache.spark.deploy.master.WorkerInfo obj) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JObject writeApplicationInfo (org.apache.spark.deploy.master.ApplicationInfo obj) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JObject writeApplicationDescription (org.apache.spark.deploy.ApplicationDescription obj) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JObject writeExecutorRunner (org.apache.spark.deploy.worker.ExecutorRunner obj) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JObject writeDriverInfo (org.apache.spark.deploy.master.DriverInfo obj) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JObject writeMasterState (org.apache.spark.deploy.DeployMessages.MasterStateResponse obj) { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JObject writeWorkerState (org.apache.spark.deploy.DeployMessages.WorkerStateResponse obj) { throw new RuntimeException(); }
}
