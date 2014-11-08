package org.apache.spark.ui;
/**
 * A tab that represents a collection of pages.
 * The prefix is appended to the parent address to form a full path, and must not contain slashes.
 */
private abstract class WebUITab {
  public  java.lang.String prefix () { throw new RuntimeException(); }
  // not preceding
  public   WebUITab (org.apache.spark.ui.WebUI parent, java.lang.String prefix) { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<org.apache.spark.ui.WebUIPage> pages () { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
  /** Attach a page to this tab. This prepends the page's prefix with the tab's own prefix. */
  public  void attachPage (org.apache.spark.ui.WebUIPage page) { throw new RuntimeException(); }
  /** Get a list of header tabs from the parent UI. */
  public  scala.collection.Seq<org.apache.spark.ui.WebUITab> headerTabs () { throw new RuntimeException(); }
  public  java.lang.String basePath () { throw new RuntimeException(); }
}
