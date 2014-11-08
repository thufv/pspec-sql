package org.apache.spark.ui;
/**
 * A page that represents the leaf node in the UI hierarchy.
 * <p>
 * The direct parent of a WebUIPage is not specified as it can be either a WebUI or a WebUITab.
 * If the parent is a WebUI, the prefix is appended to the parent's address to form a full path.
 * Else, if the parent is a WebUITab, the prefix is appended to the super prefix of the parent
 * to form a relative path. The prefix must not contain slashes.
 */
private abstract class WebUIPage {
  public  java.lang.String prefix () { throw new RuntimeException(); }
  // not preceding
  public   WebUIPage (java.lang.String prefix) { throw new RuntimeException(); }
  public abstract  scala.collection.Seq<scala.xml.Node> render (javax.servlet.http.HttpServletRequest request) ;
  public  org.json4s.JsonAST.JValue renderJson (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
}
