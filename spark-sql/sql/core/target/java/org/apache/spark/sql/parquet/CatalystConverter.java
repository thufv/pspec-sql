package org.apache.spark.sql.parquet;
private abstract class CatalystConverter extends parquet.io.api.GroupConverter {
  static public  java.lang.String ARRAY_CONTAINS_NULL_BAG_SCHEMA_NAME () { throw new RuntimeException(); }
  static public  java.lang.String ARRAY_ELEMENTS_SCHEMA_NAME () { throw new RuntimeException(); }
  static public  java.lang.String MAP_KEY_SCHEMA_NAME () { throw new RuntimeException(); }
  static public  java.lang.String MAP_VALUE_SCHEMA_NAME () { throw new RuntimeException(); }
  static public  java.lang.String MAP_SCHEMA_NAME () { throw new RuntimeException(); }
  static protected  parquet.io.api.Converter createConverter (org.apache.spark.sql.catalyst.types.StructField field, int fieldIndex, org.apache.spark.sql.parquet.CatalystConverter parent) { throw new RuntimeException(); }
  static protected  org.apache.spark.sql.parquet.CatalystConverter createRootConverter (parquet.schema.MessageType parquetSchema, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes) { throw new RuntimeException(); }
  public   CatalystConverter () { throw new RuntimeException(); }
  /**
   * The number of fields this group has
   */
  protected abstract  int size () ;
  /**
   * The index of this converter in the parent
   */
  protected abstract  int index () ;
  /**
   * The parent converter
   */
  protected abstract  org.apache.spark.sql.parquet.CatalystConverter parent () ;
  /**
   * Called by child converters to update their value in its parent (this).
   * Note that if possible the more specific update methods below should be used
   * to avoid auto-boxing of native JVM types.
   * <p>
   * @param fieldIndex
   * @param value
   */
  protected abstract  void updateField (int fieldIndex, Object value) ;
  protected  void updateBoolean (int fieldIndex, boolean value) { throw new RuntimeException(); }
  protected  void updateInt (int fieldIndex, int value) { throw new RuntimeException(); }
  protected  void updateLong (int fieldIndex, long value) { throw new RuntimeException(); }
  protected  void updateShort (int fieldIndex, short value) { throw new RuntimeException(); }
  protected  void updateByte (int fieldIndex, byte value) { throw new RuntimeException(); }
  protected  void updateDouble (int fieldIndex, double value) { throw new RuntimeException(); }
  protected  void updateFloat (int fieldIndex, float value) { throw new RuntimeException(); }
  protected  void updateBinary (int fieldIndex, parquet.io.api.Binary value) { throw new RuntimeException(); }
  protected  void updateString (int fieldIndex, parquet.io.api.Binary value) { throw new RuntimeException(); }
  protected  boolean isRootConverter () { throw new RuntimeException(); }
  protected abstract  void clearBuffer () ;
  /**
   * Should only be called in the root (group) converter!
   * <p>
   * @return
   */
  public  org.apache.spark.sql.catalyst.expressions.Row getCurrentRecord () { throw new RuntimeException(); }
}
