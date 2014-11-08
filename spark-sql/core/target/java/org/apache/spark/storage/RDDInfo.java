package org.apache.spark.storage;
public  class RDDInfo implements scala.math.Ordered<org.apache.spark.storage.RDDInfo> {
  static public  org.apache.spark.storage.RDDInfo fromRdd (org.apache.spark.rdd.RDD<?> rdd) { throw new RuntimeException(); }
  public  int id () { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
  public  int numPartitions () { throw new RuntimeException(); }
  public  org.apache.spark.storage.StorageLevel storageLevel () { throw new RuntimeException(); }
  // not preceding
  public   RDDInfo (int id, java.lang.String name, int numPartitions, org.apache.spark.storage.StorageLevel storageLevel) { throw new RuntimeException(); }
  public  int numCachedPartitions () { throw new RuntimeException(); }
  public  long memSize () { throw new RuntimeException(); }
  public  long diskSize () { throw new RuntimeException(); }
  public  long tachyonSize () { throw new RuntimeException(); }
  public  boolean isCached () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  int compare (org.apache.spark.storage.RDDInfo that) { throw new RuntimeException(); }
}
