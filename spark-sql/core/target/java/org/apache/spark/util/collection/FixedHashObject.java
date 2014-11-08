package org.apache.spark.util.collection;
/**
 * A dummy class that always returns the same hash code, to easily test hash collisions
 */
public  class FixedHashObject implements scala.Serializable, scala.Product {
  public  int v () { throw new RuntimeException(); }
  public  int h () { throw new RuntimeException(); }
  // not preceding
  public   FixedHashObject (int v, int h) { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
}
