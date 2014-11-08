package org.apache.spark.sql.parquet;
/**
 * We extend ParquetInputFormat in order to have more control over which
 * RecordFilter we want to use.
 */
private  class FilteringParquetRowInputFormat extends parquet.hadoop.ParquetInputFormat<org.apache.spark.sql.catalyst.expressions.Row> implements org.apache.spark.Logging {
  static private  com.google.common.cache.Cache<org.apache.hadoop.fs.FileStatus, parquet.hadoop.Footer> footerCache () { throw new RuntimeException(); }
  static private  com.google.common.cache.Cache<org.apache.hadoop.fs.FileStatus, org.apache.hadoop.fs.BlockLocation[]> blockLocationCache () { throw new RuntimeException(); }
  public   FilteringParquetRowInputFormat () { throw new RuntimeException(); }
  private  java.util.List<parquet.hadoop.Footer> footers () { throw new RuntimeException(); }
  private  scala.collection.immutable.Map<org.apache.hadoop.fs.Path, org.apache.hadoop.fs.FileStatus> fileStatuses () { throw new RuntimeException(); }
  public  org.apache.hadoop.mapreduce.RecordReader<java.lang.Void, org.apache.spark.sql.catalyst.expressions.Row> createRecordReader (org.apache.hadoop.mapreduce.InputSplit inputSplit, org.apache.hadoop.mapreduce.TaskAttemptContext taskAttemptContext) { throw new RuntimeException(); }
  public  java.util.List<parquet.hadoop.Footer> getFooters (org.apache.hadoop.mapreduce.JobContext jobContext) { throw new RuntimeException(); }
  public  java.util.List<parquet.hadoop.ParquetInputSplit> getSplits (org.apache.hadoop.conf.Configuration configuration, java.util.List<parquet.hadoop.Footer> footers) { throw new RuntimeException(); }
}
