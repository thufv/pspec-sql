package org.apache.spark.sql.parquet;
/**
 * TODO: this will be able to append to directories it created itself, not necessarily
 * to imported ones.
 */
private  class AppendingParquetOutputFormat extends parquet.hadoop.ParquetOutputFormat<org.apache.spark.sql.catalyst.expressions.Row> {
  public   AppendingParquetOutputFormat (int offset) { throw new RuntimeException(); }
  public  void checkOutputSpecs (org.apache.hadoop.mapreduce.JobContext job) { throw new RuntimeException(); }
  public  org.apache.hadoop.fs.Path getDefaultWorkFile (org.apache.hadoop.mapreduce.TaskAttemptContext context, java.lang.String extension) { throw new RuntimeException(); }
}
