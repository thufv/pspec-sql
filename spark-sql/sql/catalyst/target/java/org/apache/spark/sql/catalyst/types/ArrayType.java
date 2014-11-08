package org.apache.spark.sql.catalyst.types;
/**
 * The data type for collections of multiple values.
 * Internally these are represented as columns that contain a <code></code>scala.collection.Seq<code></code>.
 * <p>
 * @param elementType The data type of values.
 * @param containsNull Indicates if values have <code>null</code> values
 */
public  class ArrayType extends org.apache.spark.sql.catalyst.types.DataType implements scala.Product, scala.Serializable {
  /** Construct a {@link ArrayType} object with the given element type. The `containsNull` is true. */
  static public  org.apache.spark.sql.catalyst.types.ArrayType apply (org.apache.spark.sql.catalyst.types.DataType elementType) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType elementType () { throw new RuntimeException(); }
  public  boolean containsNull () { throw new RuntimeException(); }
  // not preceding
  public   ArrayType (org.apache.spark.sql.catalyst.types.DataType elementType, boolean containsNull) { throw new RuntimeException(); }
  private  void buildFormattedString (java.lang.String prefix, scala.collection.mutable.StringBuilder builder) { throw new RuntimeException(); }
  public  java.lang.String simpleString () { throw new RuntimeException(); }
}
