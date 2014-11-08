package org.apache.spark.deploy;
// no position
private  class SparkDocker$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SparkDocker$ MODULE$ = null;
  public   SparkDocker$ () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.TestMasterInfo startMaster (java.lang.String mountDir) { throw new RuntimeException(); }
  public  org.apache.spark.deploy.TestWorkerInfo startWorker (java.lang.String mountDir, java.lang.String masters) { throw new RuntimeException(); }
  private  scala.Tuple3<java.lang.String, org.apache.spark.deploy.DockerId, java.io.File> startNode (scala.sys.process.ProcessBuilder dockerCmd) { throw new RuntimeException(); }
}
