package org.apache.spark.input;
/**
 * A {@link org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat CombineFileInputFormat} for
 * reading whole text files. Each file is read as key-value pair, where the key is the file path and
 * the value is the entire content of file.
 */
private  class WholeTextFileInputFormat extends org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat<java.lang.String, java.lang.String> {
  public   WholeTextFileInputFormat () { throw new RuntimeException(); }
  protected  boolean isSplitable (org.apache.hadoop.mapreduce.JobContext context, org.apache.hadoop.fs.Path file) { throw new RuntimeException(); }
  public  org.apache.hadoop.mapreduce.RecordReader<java.lang.String, java.lang.String> createRecordReader (org.apache.hadoop.mapreduce.InputSplit split, org.apache.hadoop.mapreduce.TaskAttemptContext context) { throw new RuntimeException(); }
  /**
   * Allow minPartitions set by end-user in order to keep compatibility with old Hadoop API.
   */
  public  void setMaxSplitSize (org.apache.hadoop.mapreduce.JobContext context, int minPartitions) { throw new RuntimeException(); }
}
