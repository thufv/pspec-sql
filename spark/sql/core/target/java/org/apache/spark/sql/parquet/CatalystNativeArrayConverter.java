package org.apache.spark.sql.parquet;
/**
 * A <code>parquet.io.api.GroupConverter</code> that converts a single-element groups that
 * match the characteristics of an array (see
 * {@link org.apache.spark.sql.parquet.ParquetTypesConverter}) into an
 * {@link org.apache.spark.sql.types.ArrayType}.
 * <p>
 * @param elementType The type of the array elements (native)
 * @param index The position of this (array) field inside its parent converter
 * @param parent The parent converter
 * @param capacity The (initial) capacity of the buffer
 */
public  class CatalystNativeArrayConverter extends org.apache.spark.sql.parquet.CatalystConverter {
  public  org.apache.spark.sql.types.NativeType elementType () { throw new RuntimeException(); }
  public  int index () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.parquet.CatalystConverter parent () { throw new RuntimeException(); }
  protected  int capacity () { throw new RuntimeException(); }
  // not preceding
  public   CatalystNativeArrayConverter (org.apache.spark.sql.types.NativeType elementType, int index, org.apache.spark.sql.parquet.CatalystConverter parent, int capacity) { throw new RuntimeException(); }
  private  java.lang.Object buffer () { throw new RuntimeException(); }
  private  int elements () { throw new RuntimeException(); }
  protected  parquet.io.api.Converter converter () { throw new RuntimeException(); }
  public  parquet.io.api.Converter getConverter (int fieldIndex) { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  protected  void updateField (int fieldIndex, Object value) { throw new RuntimeException(); }
  protected  void updateBoolean (int fieldIndex, boolean value) { throw new RuntimeException(); }
  protected  void updateInt (int fieldIndex, int value) { throw new RuntimeException(); }
  protected  void updateShort (int fieldIndex, short value) { throw new RuntimeException(); }
  protected  void updateByte (int fieldIndex, byte value) { throw new RuntimeException(); }
  protected  void updateLong (int fieldIndex, long value) { throw new RuntimeException(); }
  protected  void updateDouble (int fieldIndex, double value) { throw new RuntimeException(); }
  protected  void updateFloat (int fieldIndex, float value) { throw new RuntimeException(); }
  protected  void updateBinary (int fieldIndex, parquet.io.api.Binary value) { throw new RuntimeException(); }
  protected  void updateString (int fieldIndex, java.lang.String value) { throw new RuntimeException(); }
  protected  void clearBuffer () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void end () { throw new RuntimeException(); }
  private  void checkGrowBuffer () { throw new RuntimeException(); }
}
