package org.apache.spark.sql.catalyst.expressions;
// no position
/**
 * A row with no data.  Calling any methods will result in an error.  Can be used as a placeholder.
 */
public  class EmptyRow$ implements org.apache.spark.sql.catalyst.expressions.Row {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final EmptyRow$ MODULE$ = null;
  public   EmptyRow$ () { throw new RuntimeException(); }
  public  Object apply (int i) { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.runtime.Nothing$> iterator () { throw new RuntimeException(); }
  public  int length () { throw new RuntimeException(); }
  public  boolean isNullAt (int i) { throw new RuntimeException(); }
  public  int getInt (int i) { throw new RuntimeException(); }
  public  long getLong (int i) { throw new RuntimeException(); }
  public  double getDouble (int i) { throw new RuntimeException(); }
  public  float getFloat (int i) { throw new RuntimeException(); }
  public  boolean getBoolean (int i) { throw new RuntimeException(); }
  public  short getShort (int i) { throw new RuntimeException(); }
  public  byte getByte (int i) { throw new RuntimeException(); }
  public  java.lang.String getString (int i) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.EmptyRow$ copy () { throw new RuntimeException(); }
}
