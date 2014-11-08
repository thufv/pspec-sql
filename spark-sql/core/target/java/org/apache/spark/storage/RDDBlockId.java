package org.apache.spark.storage;
public  class RDDBlockId extends org.apache.spark.storage.BlockId implements scala.Product, scala.Serializable {
  public  int rddId () { throw new RuntimeException(); }
  public  int splitIndex () { throw new RuntimeException(); }
  // not preceding
  public   RDDBlockId (int rddId, int splitIndex) { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
}
