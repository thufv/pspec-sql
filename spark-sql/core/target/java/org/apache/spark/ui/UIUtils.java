package org.apache.spark.ui;
// no position
/** Utility functions for generating XML pages with spark content. */
private  class UIUtils implements org.apache.spark.Logging {
  static public  java.lang.String TABLE_CLASS () { throw new RuntimeException(); }
  static private  java.lang.ThreadLocal<java.text.SimpleDateFormat> dateFormat () { throw new RuntimeException(); }
  static public  java.lang.String formatDate (java.util.Date date) { throw new RuntimeException(); }
  static public  java.lang.String formatDate (long timestamp) { throw new RuntimeException(); }
  static public  java.lang.String formatDuration (long milliseconds) { throw new RuntimeException(); }
  /** Generate a verbose human-readable string representing a duration such as "5 second 35 ms" */
  static public  java.lang.String formatDurationVerbose (long ms) { throw new RuntimeException(); }
  /** Generate a human-readable string representing a number (e.g. 100 K) */
  static public  java.lang.String formatNumber (double records) { throw new RuntimeException(); }
  static public  java.lang.String uiRoot () { throw new RuntimeException(); }
  static public  java.lang.String prependBaseUri (java.lang.String basePath, java.lang.String resource) { throw new RuntimeException(); }
  static public  scala.xml.NodeBuffer commonHeaderNodes () { throw new RuntimeException(); }
  /** Returns a spark page with correctly formatted headers */
  static public  scala.collection.Seq<scala.xml.Node> headerSparkPage (java.lang.String title, scala.Function0<scala.collection.Seq<scala.xml.Node>> content, org.apache.spark.ui.SparkUITab activeTab, scala.Option<java.lang.Object> refreshInterval) { throw new RuntimeException(); }
  /** Returns a page with the spark css/js and a simple format. Used for scheduler UI. */
  static public  scala.collection.Seq<scala.xml.Node> basicSparkPage (scala.Function0<scala.collection.Seq<scala.xml.Node>> content, java.lang.String title) { throw new RuntimeException(); }
  /** Returns an HTML table constructed by generating a row for each object in a sequence. */
  static public <T extends java.lang.Object> scala.collection.Seq<scala.xml.Node> listingTable (scala.collection.Seq<java.lang.String> headers, scala.Function1<T, scala.collection.Seq<scala.xml.Node>> generateDataRow, scala.collection.Seq<T> data, boolean fixedWidth) { throw new RuntimeException(); }
}
