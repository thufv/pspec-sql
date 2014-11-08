package org.apache.spark.sql.catalyst.types;
/**
 * The data type for Maps. Keys in a map are not allowed to have <code>null</code> values.
 * @param keyType The data type of map keys.
 * @param valueType The data type of map values.
 * @param valueContainsNull Indicates if map values have <code>null</code> values.
 */
public  class MapType extends org.apache.spark.sql.catalyst.types.DataType implements scala.Product, scala.Serializable {
  /**
   * Construct a {@link MapType} object with the given key type and value type.
   * The <code>valueContainsNull</code> is true.
   */
  static public  org.apache.spark.sql.catalyst.types.MapType apply (org.apache.spark.sql.catalyst.types.DataType keyType, org.apache.spark.sql.catalyst.types.DataType valueType) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType keyType () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType valueType () { throw new RuntimeException(); }
  public  boolean valueContainsNull () { throw new RuntimeException(); }
  // not preceding
  public   MapType (org.apache.spark.sql.catalyst.types.DataType keyType, org.apache.spark.sql.catalyst.types.DataType valueType, boolean valueContainsNull) { throw new RuntimeException(); }
  private  void buildFormattedString (java.lang.String prefix, scala.collection.mutable.StringBuilder builder) { throw new RuntimeException(); }
  public  java.lang.String simpleString () { throw new RuntimeException(); }
}
