package org.apache.spark.ui.storage;
/** Page showing list of RDD's currently stored in the cluster */
private  class StoragePage extends org.apache.spark.ui.WebUIPage {
  public   StoragePage (org.apache.spark.ui.storage.StorageTab parent) { throw new RuntimeException(); }
  private  org.apache.spark.ui.storage.StorageListener listener () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.xml.Node> render (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
  /** Header fields for the RDD table */
  private  scala.collection.Seq<java.lang.String> rddHeader () { throw new RuntimeException(); }
  /** Render an HTML row representing an RDD */
  private  scala.collection.Seq<scala.xml.Node> rddRow (org.apache.spark.storage.RDDInfo rdd) { throw new RuntimeException(); }
}
