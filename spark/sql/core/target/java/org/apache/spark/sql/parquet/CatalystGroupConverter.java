package org.apache.spark.sql.parquet;
/**
 * A <code>parquet.io.api.GroupConverter</code> that is able to convert a Parquet record
 * to a {@link org.apache.spark.sql.catalyst.expressions.Row} object.
 * <p>
 * @param schema The corresponding Catalyst schema in the form of a list of attributes.
 */
public  class CatalystGroupConverter extends org.apache.spark.sql.parquet.CatalystConverter {
  protected  org.apache.spark.sql.types.StructField[] schema () { throw new RuntimeException(); }
  protected  int index () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.parquet.CatalystConverter parent () { throw new RuntimeException(); }
  protected  scala.collection.mutable.ArrayBuffer<java.lang.Object> current () { throw new RuntimeException(); }
  protected  scala.collection.mutable.ArrayBuffer<org.apache.spark.sql.Row> buffer () { throw new RuntimeException(); }
  // not preceding
  public   CatalystGroupConverter (org.apache.spark.sql.types.StructField[] schema, int index, org.apache.spark.sql.parquet.CatalystConverter parent, scala.collection.mutable.ArrayBuffer<java.lang.Object> current, scala.collection.mutable.ArrayBuffer<org.apache.spark.sql.Row> buffer) { throw new RuntimeException(); }
  public   CatalystGroupConverter (org.apache.spark.sql.types.StructField[] schema, int index, org.apache.spark.sql.parquet.CatalystConverter parent) { throw new RuntimeException(); }
  /**
   * This constructor is used for the root converter only!
   */
  public   CatalystGroupConverter (org.apache.spark.sql.catalyst.expressions.Attribute[] attributes) { throw new RuntimeException(); }
  protected  parquet.io.api.Converter[] converters () { throw new RuntimeException(); }
  public  int size () { throw new RuntimeException(); }
  public  org.apache.spark.sql.Row getCurrentRecord () { throw new RuntimeException(); }
  public  parquet.io.api.Converter getConverter (int fieldIndex) { throw new RuntimeException(); }
  protected  void updateField (int fieldIndex, Object value) { throw new RuntimeException(); }
  protected  void clearBuffer () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void end () { throw new RuntimeException(); }
}
