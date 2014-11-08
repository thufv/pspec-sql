package org.apache.spark.sql.catalyst;
public  class GenericData<A extends java.lang.Object> implements scala.Product, scala.Serializable {
  public  A genericField () { throw new RuntimeException(); }
  // not preceding
  public   GenericData (A genericField) { throw new RuntimeException(); }
}
