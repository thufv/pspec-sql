package org.apache.spark.sql.parquet;
/**
 * A <code>parquet.hadoop.api.WriteSupport</code> for Row ojects.
 */
public  class RowWriteSupport extends parquet.hadoop.api.WriteSupport<org.apache.spark.sql.Row> implements org.apache.spark.Logging {
  static public  java.lang.String SPARK_ROW_SCHEMA () { throw new RuntimeException(); }
  static public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> getSchema (org.apache.hadoop.conf.Configuration configuration) { throw new RuntimeException(); }
  static public  void setSchema (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> schema, org.apache.hadoop.conf.Configuration configuration) { throw new RuntimeException(); }
  public   RowWriteSupport () { throw new RuntimeException(); }
  public  parquet.io.api.RecordConsumer writer () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Attribute[] attributes () { throw new RuntimeException(); }
  public  parquet.hadoop.api.WriteSupport.WriteContext init (org.apache.hadoop.conf.Configuration configuration) { throw new RuntimeException(); }
  public  void prepareForWrite (parquet.io.api.RecordConsumer recordConsumer) { throw new RuntimeException(); }
  public  void write (org.apache.spark.sql.Row record) { throw new RuntimeException(); }
  public  void writeValue (org.apache.spark.sql.types.DataType schema, Object value) { throw new RuntimeException(); }
  public  void writePrimitive (org.apache.spark.sql.types.DataType schema, Object value) { throw new RuntimeException(); }
  public  void writeStruct (org.apache.spark.sql.types.StructType schema, org.apache.spark.sql.Row struct) { throw new RuntimeException(); }
  public  void writeArray (org.apache.spark.sql.types.ArrayType schema, scala.collection.Seq<java.lang.Object> array) { throw new RuntimeException(); }
  public  void writeMap (org.apache.spark.sql.types.MapType schema, scala.collection.immutable.Map<?, java.lang.Object> map) { throw new RuntimeException(); }
  private  byte[] scratchBytes () { throw new RuntimeException(); }
  public  void writeDecimal (org.apache.spark.sql.types.Decimal decimal, int precision) { throw new RuntimeException(); }
  public  void writeTimestamp (java.sql.Timestamp ts) { throw new RuntimeException(); }
}
