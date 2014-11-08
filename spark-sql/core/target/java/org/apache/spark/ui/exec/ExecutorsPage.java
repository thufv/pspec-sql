package org.apache.spark.ui.exec;
private  class ExecutorsPage extends org.apache.spark.ui.WebUIPage {
  public   ExecutorsPage (org.apache.spark.ui.exec.ExecutorsTab parent) { throw new RuntimeException(); }
  private  org.apache.spark.ui.exec.ExecutorsListener listener () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.xml.Node> render (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
  /** Render an HTML row representing an executor */
  private  scala.collection.Seq<scala.xml.Node> execRow (org.apache.spark.ui.exec.ExecutorSummaryInfo info) { throw new RuntimeException(); }
  /** Represent an executor's info as a map given a storage status index */
  private  org.apache.spark.ui.exec.ExecutorSummaryInfo getExecInfo (int statusId) { throw new RuntimeException(); }
}
