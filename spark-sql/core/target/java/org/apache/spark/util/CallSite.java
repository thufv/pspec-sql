package org.apache.spark.util;
/** CallSite represents a place in user code. It can have a short and a long form. */
private  class CallSite implements scala.Product, scala.Serializable {
  public  java.lang.String shortForm () { throw new RuntimeException(); }
  public  java.lang.String longForm () { throw new RuntimeException(); }
  // not preceding
  public   CallSite (java.lang.String shortForm, java.lang.String longForm) { throw new RuntimeException(); }
}
