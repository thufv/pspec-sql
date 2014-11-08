package org.apache.spark.sql.parquet;
/**
 * A <code>parquet.io.api.GroupConverter</code> that is able to convert a Parquet record
 * to a {@link org.apache.spark.sql.catalyst.expressions.Row} object. Note that his
 * converter is optimized for rows of primitive types (non-nested records).
 */
private  class CatalystPrimitiveRowConverter extends org.apache.spark.sql.parquet.CatalystConverter {
  protected  org.apache.spark.sql.catalyst.types.StructField[] schema () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.expressions.MutableRow current () { throw new RuntimeException(); }
  // not preceding
  public   CatalystPrimitiveRowConverter (org.apache.spark.sql.catalyst.types.StructField[] schema, org.apache.spark.sql.catalyst.expressions.MutableRow current) { throw new RuntimeException(); }
  public   CatalystPrimitiveRowConverter (org.apache.spark.sql.catalyst.expressions.Attribute[] attributes) { throw new RuntimeException(); }
  protected  parquet.io.api.Converter[] converters () { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  public  int index () { throw new RuntimeException(); }
  public  scala.Null parent () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Row getCurrentRecord () { throw new RuntimeException(); }
  public  parquet.io.api.Converter getConverter (int fieldIndex) { throw new RuntimeException(); }
  protected  void updateField (int fieldIndex, Object value) { throw new RuntimeException(); }
  protected  void clearBuffer () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void end () { throw new RuntimeException(); }
  protected  void updateBoolean (int fieldIndex, boolean value) { throw new RuntimeException(); }
  protected  void updateInt (int fieldIndex, int value) { throw new RuntimeException(); }
  protected  void updateLong (int fieldIndex, long value) { throw new RuntimeException(); }
  protected  void updateShort (int fieldIndex, short value) { throw new RuntimeException(); }
  protected  void updateByte (int fieldIndex, byte value) { throw new RuntimeException(); }
  protected  void updateDouble (int fieldIndex, double value) { throw new RuntimeException(); }
  protected  void updateFloat (int fieldIndex, float value) { throw new RuntimeException(); }
  protected  void updateBinary (int fieldIndex, parquet.io.api.Binary value) { throw new RuntimeException(); }
  protected  void updateString (int fieldIndex, parquet.io.api.Binary value) { throw new RuntimeException(); }
}
