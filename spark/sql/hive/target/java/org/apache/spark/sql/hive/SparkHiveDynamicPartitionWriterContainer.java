package org.apache.spark.sql.hive;
public  class SparkHiveDynamicPartitionWriterContainer extends org.apache.spark.sql.hive.SparkHiveWriterContainer {
  static public  java.lang.String SUCCESSFUL_JOB_OUTPUT_DIR_MARKER () { throw new RuntimeException(); }
  public   SparkHiveDynamicPartitionWriterContainer (org.apache.hadoop.mapred.JobConf jobConf, org.apache.spark.sql.hive.ShimFileSinkDesc fileSinkConf, java.lang.String[] dynamicPartColNames) { throw new RuntimeException(); }
  private  java.lang.String defaultPartName () { throw new RuntimeException(); }
  private  scala.collection.mutable.HashMap<java.lang.String, org.apache.hadoop.hive.ql.exec.FileSinkOperator.RecordWriter> writers () { throw new RuntimeException(); }
  protected  void initWriters () { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
  public  void commitJob () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.ql.exec.FileSinkOperator.RecordWriter getLocalFileWriter (org.apache.spark.sql.Row row) { throw new RuntimeException(); }
}
