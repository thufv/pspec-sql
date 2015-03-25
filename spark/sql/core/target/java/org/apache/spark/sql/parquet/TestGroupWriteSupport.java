package org.apache.spark.sql.parquet;
public  class TestGroupWriteSupport extends parquet.hadoop.api.WriteSupport<parquet.example.data.Group> {
  public   TestGroupWriteSupport (parquet.schema.MessageType schema) { throw new RuntimeException(); }
  public  parquet.example.data.GroupWriter groupWriter () { throw new RuntimeException(); }
  public  void prepareForWrite (parquet.io.api.RecordConsumer recordConsumer) { throw new RuntimeException(); }
  public  parquet.hadoop.api.WriteSupport.WriteContext init (org.apache.hadoop.conf.Configuration configuration) { throw new RuntimeException(); }
  public  void write (parquet.example.data.Group record) { throw new RuntimeException(); }
}
