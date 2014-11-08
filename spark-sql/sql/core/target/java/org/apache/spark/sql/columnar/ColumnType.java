package org.apache.spark.sql.columnar;
/**
 * An abstract class that represents type of a column. Used to append/extract Java objects into/from
 * the underlying {@link ByteBuffer} of a column.
 * <p>
 * @param typeId A unique ID representing the type.
 * @param defaultSize Default size in bytes for one element of type T (e.g. 4 for <code>Int</code>).
 * @tparam T Scala data type for the column.
 * @tparam JvmType Underlying Java type to represent the elements.
 */
private abstract class ColumnType<T extends org.apache.spark.sql.catalyst.types.DataType, JvmType extends java.lang.Object> {
  static public  org.apache.spark.sql.columnar.ColumnType<?, ?> apply (org.apache.spark.sql.catalyst.types.DataType dataType) { throw new RuntimeException(); }
  public  int typeId () { throw new RuntimeException(); }
  public  int defaultSize () { throw new RuntimeException(); }
  // not preceding
  public   ColumnType (int typeId, int defaultSize) { throw new RuntimeException(); }
  /**
   * Extracts a value out of the buffer at the buffer's current position.
   */
  public abstract  JvmType extract (java.nio.ByteBuffer buffer) ;
  /**
   * Appends the given value v of type T into the given ByteBuffer.
   */
  public abstract  void append (JvmType v, java.nio.ByteBuffer buffer) ;
  /**
   * Returns the size of the value. This is used to calculate the size of variable length types
   * such as byte arrays and strings.
   */
  public  int actualSize (JvmType v) { throw new RuntimeException(); }
  /**
   * Returns <code>row(ordinal)</code>. Subclasses should override this method to avoid boxing/unboxing costs
   * whenever possible.
   */
  public abstract  JvmType getField (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) ;
  /**
   * Sets <code>row(ordinal)</code> to <code>field</code>. Subclasses should override this method to avoid boxing/unboxing
   * costs whenever possible.
   */
  public abstract  void setField (org.apache.spark.sql.catalyst.expressions.MutableRow row, int ordinal, JvmType value) ;
  /**
   * Creates a duplicated copy of the value.
   */
  public  JvmType clone (JvmType v) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
