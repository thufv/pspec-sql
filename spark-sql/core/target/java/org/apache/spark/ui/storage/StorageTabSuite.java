package org.apache.spark.ui.storage;
/**
 * Test various functionality in the StorageListener that supports the StorageTab.
 */
public  class StorageTabSuite extends org.scalatest.FunSuite implements org.scalatest.BeforeAndAfter {
  public   StorageTabSuite () { throw new RuntimeException(); }
  private  org.apache.spark.scheduler.LiveListenerBus bus () { throw new RuntimeException(); }
  private  org.apache.spark.storage.StorageStatusListener storageStatusListener () { throw new RuntimeException(); }
  private  org.apache.spark.ui.storage.StorageListener storageListener () { throw new RuntimeException(); }
  private  org.apache.spark.storage.StorageLevel memAndDisk () { throw new RuntimeException(); }
  private  org.apache.spark.storage.StorageLevel memOnly () { throw new RuntimeException(); }
  private  org.apache.spark.storage.StorageLevel none () { throw new RuntimeException(); }
  private  org.apache.spark.scheduler.TaskInfo taskInfo () { throw new RuntimeException(); }
  private  org.apache.spark.scheduler.TaskInfo taskInfo1 () { throw new RuntimeException(); }
  private  org.apache.spark.storage.RDDInfo rddInfo0 () { throw new RuntimeException(); }
  private  org.apache.spark.storage.RDDInfo rddInfo1 () { throw new RuntimeException(); }
  private  org.apache.spark.storage.RDDInfo rddInfo2 () { throw new RuntimeException(); }
  private  org.apache.spark.storage.RDDInfo rddInfo3 () { throw new RuntimeException(); }
  private  org.apache.spark.storage.BlockManagerId bm1 () { throw new RuntimeException(); }
}
