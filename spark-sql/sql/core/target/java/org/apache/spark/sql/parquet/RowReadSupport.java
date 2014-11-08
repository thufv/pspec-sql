package org.apache.spark.sql.parquet;
/**
 * A <code>parquet.hadoop.api.ReadSupport</code> for Row objects.
 */
private  class RowReadSupport extends parquet.hadoop.api.ReadSupport<org.apache.spark.sql.catalyst.expressions.Row> implements org.apache.spark.Logging {
  static public  java.lang.String SPARK_ROW_REQUESTED_SCHEMA () { throw new RuntimeException(); }
  static public  java.lang.String SPARK_METADATA_KEY () { throw new RuntimeException(); }
  static private  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> getRequestedSchema (org.apache.hadoop.conf.Configuration configuration) { throw new RuntimeException(); }
  public   RowReadSupport () { throw new RuntimeException(); }
  public  parquet.io.api.RecordMaterializer<org.apache.spark.sql.catalyst.expressions.Row> prepareForRead (org.apache.hadoop.conf.Configuration conf, java.util.Map<java.lang.String, java.lang.String> stringMap, parquet.schema.MessageType fileSchema, parquet.hadoop.api.ReadSupport.ReadContext readContext) { throw new RuntimeException(); }
  public  parquet.hadoop.api.ReadSupport.ReadContext init (org.apache.hadoop.conf.Configuration configuration, java.util.Map<java.lang.String, java.lang.String> keyValueMetaData, parquet.schema.MessageType fileSchema) { throw new RuntimeException(); }
}
