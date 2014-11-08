package org.apache.spark.ui.storage;
/** Page showing storage details for a given RDD */
private  class RDDPage extends org.apache.spark.ui.WebUIPage {
  public   RDDPage (org.apache.spark.ui.storage.StorageTab parent) { throw new RuntimeException(); }
  private  org.apache.spark.ui.storage.StorageListener listener () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.xml.Node> render (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
  /** Header fields for the worker table */
  private  scala.collection.Seq<java.lang.String> workerHeader () { throw new RuntimeException(); }
  /** Header fields for the block table */
  private  scala.collection.Seq<java.lang.String> blockHeader () { throw new RuntimeException(); }
  /** Render an HTML row representing a worker */
  private  scala.collection.Seq<scala.xml.Node> workerRow (scala.Tuple2<java.lang.Object, org.apache.spark.storage.StorageStatus> worker) { throw new RuntimeException(); }
  /** Render an HTML row representing a block */
  private  scala.collection.Seq<scala.xml.Node> blockRow (scala.Tuple3<org.apache.spark.storage.BlockId, org.apache.spark.storage.BlockStatus, scala.collection.Seq<java.lang.String>> row) { throw new RuntimeException(); }
}
