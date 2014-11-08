package org.apache.spark.sql.catalyst.types;
// no position
/**
 * Returns a {@link StructType} containing {@link StructField}s of the given names.
 * Those names which do not have matching fields will be ignored.
 */
public  class MapType$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final MapType$ MODULE$ = null;
  public   MapType$ () { throw new RuntimeException(); }
  /**
   * Construct a {@link MapType} object with the given key type and value type.
   * The <code>valueContainsNull</code> is true.
   */
  public  org.apache.spark.sql.catalyst.types.MapType apply (org.apache.spark.sql.catalyst.types.DataType keyType, org.apache.spark.sql.catalyst.types.DataType valueType) { throw new RuntimeException(); }
}
