package org.apache.spark.sql.catalyst.expressions;
// no position
/**
 * A row with no data.  Calling any methods will result in an error.  Can be used as a placeholder.
 */
public  class EmptyRow implements org.apache.spark.sql.catalyst.expressions.Row {
  static public  Object apply (int i) { throw new RuntimeException(); }
  static public  scala.collection.Iterator<scala.runtime.Nothing$> iterator () { throw new RuntimeException(); }
  static public  int length () { throw new RuntimeException(); }
  static public  boolean isNullAt (int i) { throw new RuntimeException(); }
  static public  int getInt (int i) { throw new RuntimeException(); }
  static public  long getLong (int i) { throw new RuntimeException(); }
  static public  double getDouble (int i) { throw new RuntimeException(); }
  static public  float getFloat (int i) { throw new RuntimeException(); }
  static public  boolean getBoolean (int i) { throw new RuntimeException(); }
  static public  short getShort (int i) { throw new RuntimeException(); }
  static public  byte getByte (int i) { throw new RuntimeException(); }
  static public  java.lang.String getString (int i) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.expressions.EmptyRow$ copy () { throw new RuntimeException(); }
}
