package org.apache.spark.sql.hive.parquet;
/**
 * A placeholder that allows SparkSQL users to create metastore tables that are stored as
 * parquet files.  It is only intended to pass the checks that the serde is valid and exists
 * when a CREATE TABLE is run.  The actual work of decoding will be done by ParquetTableScan
 * when "spark.sql.hive.convertMetastoreParquet" is set to true.
 */
public  class FakeParquetSerDe implements org.apache.hadoop.hive.serde2.SerDe {
  public   FakeParquetSerDe () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getObjectInspector () { throw new RuntimeException(); }
  public  java.lang.Object deserialize (org.apache.hadoop.io.Writable p1) { throw new RuntimeException(); }
  public  void initialize (org.apache.hadoop.conf.Configuration p1, java.util.Properties p2) { throw new RuntimeException(); }
  public  java.lang.Class<? extends org.apache.hadoop.io.Writable> getSerializedClass () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.serde2.SerDeStats getSerDeStats () { throw new RuntimeException(); }
  public  org.apache.hadoop.io.Writable serialize (Object p1, org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector p2) { throw new RuntimeException(); }
  private  scala.Nothing throwError () { throw new RuntimeException(); }
}
