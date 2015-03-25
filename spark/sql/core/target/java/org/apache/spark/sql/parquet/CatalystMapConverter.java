package org.apache.spark.sql.parquet;
/**
 * A <code>parquet.io.api.GroupConverter</code> that converts two-element groups that
 * match the characteristics of a map (see
 * {@link org.apache.spark.sql.parquet.ParquetTypesConverter}) into an
 * {@link org.apache.spark.sql.types.MapType}.
 * <p>
 * @param schema
 * @param index
 * @param parent
 */
public  class CatalystMapConverter extends org.apache.spark.sql.parquet.CatalystConverter {
  protected  org.apache.spark.sql.types.StructField[] schema () { throw new RuntimeException(); }
  protected  int index () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.parquet.CatalystConverter parent () { throw new RuntimeException(); }
  // not preceding
  public   CatalystMapConverter (org.apache.spark.sql.types.StructField[] schema, int index, org.apache.spark.sql.parquet.CatalystConverter parent) { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.Object, java.lang.Object> map () { throw new RuntimeException(); }
  private  org.apache.spark.sql.parquet.CatalystConverter keyValueConverter () { throw new RuntimeException(); }
  protected  int size () { throw new RuntimeException(); }
  protected  void clearBuffer () { throw new RuntimeException(); }
  public  void start () { throw new RuntimeException(); }
  public  void end () { throw new RuntimeException(); }
  public  parquet.io.api.Converter getConverter (int fieldIndex) { throw new RuntimeException(); }
  protected  void updateField (int fieldIndex, Object value) { throw new RuntimeException(); }
}
