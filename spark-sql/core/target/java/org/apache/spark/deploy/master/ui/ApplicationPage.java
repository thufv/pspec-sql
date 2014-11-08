package org.apache.spark.deploy.master.ui;
private  class ApplicationPage extends org.apache.spark.ui.WebUIPage {
  public   ApplicationPage (org.apache.spark.deploy.master.ui.MasterWebUI parent) { throw new RuntimeException(); }
  private  akka.actor.ActorRef master () { throw new RuntimeException(); }
  private  scala.concurrent.duration.FiniteDuration timeout () { throw new RuntimeException(); }
  /** Executor details for a particular application */
  public  org.json4s.JsonAST.JValue renderJson (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
  /** Executor details for a particular application */
  public  scala.collection.Seq<scala.xml.Node> render (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
  private  scala.collection.Seq<scala.xml.Node> executorRow (org.apache.spark.deploy.master.ExecutorInfo executor) { throw new RuntimeException(); }
}
