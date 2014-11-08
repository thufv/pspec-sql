package org.apache.spark.storage;
private  class MemoryEntry implements scala.Product, scala.Serializable {
  public  Object value () { throw new RuntimeException(); }
  public  long size () { throw new RuntimeException(); }
  public  boolean deserialized () { throw new RuntimeException(); }
  // not preceding
  public   MemoryEntry (Object value, long size, boolean deserialized) { throw new RuntimeException(); }
}
