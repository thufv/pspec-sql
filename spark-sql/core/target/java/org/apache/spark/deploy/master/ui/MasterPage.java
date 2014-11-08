package org.apache.spark.deploy.master.ui;
private  class MasterPage extends org.apache.spark.ui.WebUIPage {
  public   MasterPage (org.apache.spark.deploy.master.ui.MasterWebUI parent) { throw new RuntimeException(); }
  private  akka.actor.ActorRef master () { throw new RuntimeException(); }
  private  scala.concurrent.duration.FiniteDuration timeout () { throw new RuntimeException(); }
  public  org.json4s.JsonAST.JValue renderJson (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
  /** Index view listing applications and executors */
  public  scala.collection.Seq<scala.xml.Node> render (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
  private  scala.collection.Seq<scala.xml.Node> workerRow (org.apache.spark.deploy.master.WorkerInfo worker) { throw new RuntimeException(); }
  private  scala.collection.Seq<scala.xml.Node> appRow (org.apache.spark.deploy.master.ApplicationInfo app) { throw new RuntimeException(); }
  private  scala.collection.Seq<scala.xml.Node> driverRow (org.apache.spark.deploy.master.DriverInfo driver) { throw new RuntimeException(); }
}
