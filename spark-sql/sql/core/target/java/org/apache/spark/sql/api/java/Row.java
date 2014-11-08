package org.apache.spark.sql.api.java;
/**
 * A result row from a SparkSQL query.
 */
public  class Row implements scala.Serializable {
  /**
   * Creates a Row with the given values.
   */
  static public  org.apache.spark.sql.api.java.Row create (java.lang.Object... values) { throw new RuntimeException(); }
  static private  Object toJavaValue (Object value) { throw new RuntimeException(); }
  static private  Object toScalaValue (Object value) { throw new RuntimeException(); }
  /**
   * Creates a Row with the given values.
   */
  static public  org.apache.spark.sql.api.java.Row create (scala.collection.Seq<java.lang.Object> values) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Row row () { throw new RuntimeException(); }
  // not preceding
  public   Row (org.apache.spark.sql.catalyst.expressions.Row row) { throw new RuntimeException(); }
  /** Returns the number of columns present in this Row. */
  public  int length () { throw new RuntimeException(); }
  /** Returns the value of column `i`. */
  public  Object get (int i) { throw new RuntimeException(); }
  /** Returns true if value at column `i` is NULL. */
  public  boolean isNullAt (int i) { throw new RuntimeException(); }
  /**
   * Returns the value of column <code>i</code> as an int.  This function will throw an exception if the value
   * is at <code>i</code> is not an integer, or if it is null.
   */
  public  int getInt (int i) { throw new RuntimeException(); }
  /**
   * Returns the value of column <code>i</code> as a long.  This function will throw an exception if the value
   * is at <code>i</code> is not a long, or if it is null.
   */
  public  long getLong (int i) { throw new RuntimeException(); }
  /**
   * Returns the value of column <code>i</code> as a double.  This function will throw an exception if the
   * value is at <code>i</code> is not a double, or if it is null.
   */
  public  double getDouble (int i) { throw new RuntimeException(); }
  /**
   * Returns the value of column <code>i</code> as a bool.  This function will throw an exception if the value
   * is at <code>i</code> is not a boolean, or if it is null.
   */
  public  boolean getBoolean (int i) { throw new RuntimeException(); }
  /**
   * Returns the value of column <code>i</code> as a short.  This function will throw an exception if the value
   * is at <code>i</code> is not a short, or if it is null.
   */
  public  short getShort (int i) { throw new RuntimeException(); }
  /**
   * Returns the value of column <code>i</code> as a byte.  This function will throw an exception if the value
   * is at <code>i</code> is not a byte, or if it is null.
   */
  public  byte getByte (int i) { throw new RuntimeException(); }
  /**
   * Returns the value of column <code>i</code> as a float.  This function will throw an exception if the value
   * is at <code>i</code> is not a float, or if it is null.
   */
  public  float getFloat (int i) { throw new RuntimeException(); }
  /**
   * Returns the value of column <code>i</code> as a String.  This function will throw an exception if the
   * value is at <code>i</code> is not a String.
   */
  public  java.lang.String getString (int i) { throw new RuntimeException(); }
  public  boolean canEqual (Object other) { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
}
