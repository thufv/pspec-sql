package org.apache.spark.deploy.worker.ui;
private  class LogPage extends org.apache.spark.ui.WebUIPage implements org.apache.spark.Logging {
  public   LogPage (org.apache.spark.deploy.worker.ui.WorkerWebUI parent) { throw new RuntimeException(); }
  private  org.apache.spark.deploy.worker.Worker worker () { throw new RuntimeException(); }
  private  java.io.File workDir () { throw new RuntimeException(); }
  public  java.lang.String renderLog (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.xml.Node> render (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
  /** Get the part of the log files given the offset and desired length of bytes */
  private  scala.Tuple4<java.lang.String, java.lang.Object, java.lang.Object, java.lang.Object> getLog (java.lang.String logDirectory, java.lang.String logType, scala.Option<java.lang.Object> offsetOption, int byteLength) { throw new RuntimeException(); }
}
