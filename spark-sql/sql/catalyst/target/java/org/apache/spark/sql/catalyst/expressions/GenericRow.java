package org.apache.spark.sql.catalyst.expressions;
/**
 * A row implementation that uses an array of objects as the underlying storage.  Note that, while
 * the array is not copied, and thus could technically be mutated after creation, this is not
 * allowed.
 */
public  class GenericRow implements org.apache.spark.sql.catalyst.expressions.Row {
  protected  java.lang.Object[] values () { throw new RuntimeException(); }
  // not preceding
  public   GenericRow (java.lang.Object[] values) { throw new RuntimeException(); }
  /** No-arg constructor for serialization. */
  public   GenericRow () { throw new RuntimeException(); }
  public   GenericRow (int size) { throw new RuntimeException(); }
  public  scala.collection.Iterator<java.lang.Object> iterator () { throw new RuntimeException(); }
  public  int length () { throw new RuntimeException(); }
  public  Object apply (int i) { throw new RuntimeException(); }
  public  boolean isNullAt (int i) { throw new RuntimeException(); }
  public  int getInt (int i) { throw new RuntimeException(); }
  public  long getLong (int i) { throw new RuntimeException(); }
  public  double getDouble (int i) { throw new RuntimeException(); }
  public  float getFloat (int i) { throw new RuntimeException(); }
  public  boolean getBoolean (int i) { throw new RuntimeException(); }
  public  short getShort (int i) { throw new RuntimeException(); }
  public  byte getByte (int i) { throw new RuntimeException(); }
  public  java.lang.String getString (int i) { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.GenericRow copy () { throw new RuntimeException(); }
}
