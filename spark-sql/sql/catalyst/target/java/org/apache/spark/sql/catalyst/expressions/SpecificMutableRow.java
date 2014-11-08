package org.apache.spark.sql.catalyst.expressions;
/**
 * A row type that holds an array specialized container objects, of type {@link MutableValue}, chosen
 * based on the dataTypes of each column.  The intent is to decrease garbage when modifying the
 * values of primitive columns.
 */
public final class SpecificMutableRow implements org.apache.spark.sql.catalyst.expressions.MutableRow {
  public  org.apache.spark.sql.catalyst.expressions.MutableValue[] values () { throw new RuntimeException(); }
  // not preceding
  public   SpecificMutableRow (org.apache.spark.sql.catalyst.expressions.MutableValue[] values) { throw new RuntimeException(); }
  public   SpecificMutableRow (scala.collection.Seq<org.apache.spark.sql.catalyst.types.DataType> dataTypes) { throw new RuntimeException(); }
  public   SpecificMutableRow () { throw new RuntimeException(); }
  public  int length () { throw new RuntimeException(); }
  public  void setNullAt (int i) { throw new RuntimeException(); }
  public  Object apply (int i) { throw new RuntimeException(); }
  public  boolean isNullAt (int i) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Row copy () { throw new RuntimeException(); }
  public  void update (int ordinal, Object value) { throw new RuntimeException(); }
  public  scala.collection.Iterator<java.lang.Object> iterator () { throw new RuntimeException(); }
  public  void setString (int ordinal, java.lang.String value) { throw new RuntimeException(); }
  public  java.lang.String getString (int ordinal) { throw new RuntimeException(); }
  public  void setInt (int ordinal, int value) { throw new RuntimeException(); }
  public  int getInt (int i) { throw new RuntimeException(); }
  public  void setFloat (int ordinal, float value) { throw new RuntimeException(); }
  public  float getFloat (int i) { throw new RuntimeException(); }
  public  void setBoolean (int ordinal, boolean value) { throw new RuntimeException(); }
  public  boolean getBoolean (int i) { throw new RuntimeException(); }
  public  void setDouble (int ordinal, double value) { throw new RuntimeException(); }
  public  double getDouble (int i) { throw new RuntimeException(); }
  public  void setShort (int ordinal, short value) { throw new RuntimeException(); }
  public  short getShort (int i) { throw new RuntimeException(); }
  public  void setLong (int ordinal, long value) { throw new RuntimeException(); }
  public  long getLong (int i) { throw new RuntimeException(); }
  public  void setByte (int ordinal, byte value) { throw new RuntimeException(); }
  public  byte getByte (int i) { throw new RuntimeException(); }
}
