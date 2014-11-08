package org.apache.spark.sql.parquet;
/**
 * A <code>parquet.io.api.GroupConverter</code> that converts a single-element groups that
 * match the characteristics of an array (see
 * {@link org.apache.spark.sql.parquet.ParquetTypesConverter}) into an
 * {@link org.apache.spark.sql.catalyst.types.ArrayType}.
 * <p>
 * @param elementType The type of the array elements (complex or primitive)
 * @param index The position of this (array) field inside its parent converter
 * @param parent The parent converter
 * @param buffer A data buffer
 */
private  class CatalystArrayConverter extends org.apache.spark.sql.parquet.CatalystConverter {
  static public  int INITIAL_ARRAY_SIZE () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType elementType () { throw new RuntimeException(); }
  public  int index () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.parquet.CatalystConverter parent () { throw new RuntimeException(); }
  protected  scala.collection.mutable.Buffer<java.lang.Object> buffer () { throw new RuntimeException(); }
  // not preceding
  public   CatalystArrayConverter (org.apache.spark.sql.catalyst.types.DataType elementType, int index, org.apache.spark.sql.parquet.CatalystConverter parent, scala.collection.mutable.Buffer<java.lang.Object> buffer) { throw new RuntimeException(); }
  public   CatalystArrayConverter (org.apache.spark.sql.catalyst.types.DataType elementType, int index, org.apache.spark.sql.parquet.CatalystConverter parent) { throw new RuntimeException(); }
  protected  parquet.io.api.Converter converter () { throw new RuntimeException(); }
  public  parquet.io.api.Converter getConverter (int fieldIndex) { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  protected  void updateField (int fieldIndex, Object value) { throw new RuntimeException(); }
  protected  void clearBuffer () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void end () { throw new RuntimeException(); }
}
