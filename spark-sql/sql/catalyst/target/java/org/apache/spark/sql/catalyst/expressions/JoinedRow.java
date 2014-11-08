package org.apache.spark.sql.catalyst.expressions;
/**
 * A mutable wrapper that makes two rows appear as a single concatenated row.  Designed to
 * be instantiated once per thread and reused.
 */
public  class JoinedRow implements org.apache.spark.sql.catalyst.expressions.Row {
  public   JoinedRow () { throw new RuntimeException(); }
  public   JoinedRow (org.apache.spark.sql.catalyst.expressions.Row left, org.apache.spark.sql.catalyst.expressions.Row right) { throw new RuntimeException(); }
  /** Updates this JoinedRow to used point at two new base rows.  Returns itself. */
  public  org.apache.spark.sql.catalyst.expressions.Row apply (org.apache.spark.sql.catalyst.expressions.Row r1, org.apache.spark.sql.catalyst.expressions.Row r2) { throw new RuntimeException(); }
  /** Updates this JoinedRow by updating its left base row.  Returns itself. */
  public  org.apache.spark.sql.catalyst.expressions.Row withLeft (org.apache.spark.sql.catalyst.expressions.Row newLeft) { throw new RuntimeException(); }
  /** Updates this JoinedRow by updating its right base row.  Returns itself. */
  public  org.apache.spark.sql.catalyst.expressions.Row withRight (org.apache.spark.sql.catalyst.expressions.Row newRight) { throw new RuntimeException(); }
  public  scala.collection.Iterator<java.lang.Object> iterator () { throw new RuntimeException(); }
  public  int length () { throw new RuntimeException(); }
  public  Object apply (int i) { throw new RuntimeException(); }
  public  boolean isNullAt (int i) { throw new RuntimeException(); }
  public  int getInt (int i) { throw new RuntimeException(); }
  public  long getLong (int i) { throw new RuntimeException(); }
  public  double getDouble (int i) { throw new RuntimeException(); }
  public  boolean getBoolean (int i) { throw new RuntimeException(); }
  public  short getShort (int i) { throw new RuntimeException(); }
  public  byte getByte (int i) { throw new RuntimeException(); }
  public  float getFloat (int i) { throw new RuntimeException(); }
  public  java.lang.String getString (int i) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.GenericRow copy () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
