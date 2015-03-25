package org.apache.spark.sql.parquet;
/**
 * This converter is for multi-element groups of primitive or complex types
 * that have repetition level optional or required (so struct fields).
 * <p>
 * @param schema The corresponding Catalyst schema in the form of a list of
 *               attributes.
 * @param index
 * @param parent
 */
public  class CatalystStructConverter extends org.apache.spark.sql.parquet.CatalystGroupConverter {
  protected  org.apache.spark.sql.types.StructField[] schema () { throw new RuntimeException(); }
  protected  int index () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.parquet.CatalystConverter parent () { throw new RuntimeException(); }
  // not preceding
  public   CatalystStructConverter (org.apache.spark.sql.types.StructField[] schema, int index, org.apache.spark.sql.parquet.CatalystConverter parent) { throw new RuntimeException(); }
  protected  void clearBuffer () { throw new RuntimeException(); }
  public  void end () { throw new RuntimeException(); }
}
