package org.apache.spark.deploy.master.ui;
private  class HistoryNotFoundPage extends org.apache.spark.ui.WebUIPage {
  public   HistoryNotFoundPage (org.apache.spark.deploy.master.ui.MasterWebUI parent) { throw new RuntimeException(); }
  /**
   * Render a page that conveys failure in loading application history.
   * <p>
   * This accepts 3 HTTP parameters:
   *   msg = message to display to the user
   *   title = title of the page
   *   exception = detailed description of the exception in loading application history (if any)
   * <p>
   * Parameters "msg" and "exception" are assumed to be UTF-8 encoded.
   */
  public  scala.collection.Seq<scala.xml.Node> render (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
}
