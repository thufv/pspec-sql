package org.apache.spark.sql.parquet;
/**
 * A <code>parquet.io.api.RecordMaterializer</code> for Rows.
 * <p>
 *@param root The root group converter for the record.
 */
private  class RowRecordMaterializer extends parquet.io.api.RecordMaterializer<org.apache.spark.sql.catalyst.expressions.Row> {
  public   RowRecordMaterializer (org.apache.spark.sql.parquet.CatalystConverter root) { throw new RuntimeException(); }
  public   RowRecordMaterializer (parquet.schema.MessageType parquetSchema, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Row getCurrentRecord () { throw new RuntimeException(); }
  public  parquet.io.api.GroupConverter getRootConverter () { throw new RuntimeException(); }
}
