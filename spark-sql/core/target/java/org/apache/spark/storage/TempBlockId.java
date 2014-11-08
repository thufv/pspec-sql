package org.apache.spark.storage;
/** Id associated with temporary data managed as blocks. Not serializable. */
private  class TempBlockId extends org.apache.spark.storage.BlockId implements scala.Product, scala.Serializable {
  public  java.util.UUID id () { throw new RuntimeException(); }
  // not preceding
  public   TempBlockId (java.util.UUID id) { throw new RuntimeException(); }
  public  java.lang.String name () { throw new RuntimeException(); }
}
