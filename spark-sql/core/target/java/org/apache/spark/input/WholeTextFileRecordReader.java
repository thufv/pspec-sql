package org.apache.spark.input;
/**
 * A {@link org.apache.hadoop.mapreduce.RecordReader RecordReader} for reading a single whole text file
 * out in a key-value pair, where the key is the file path and the value is the entire content of
 * the file.
 */
private  class WholeTextFileRecordReader extends org.apache.hadoop.mapreduce.RecordReader<java.lang.String, java.lang.String> {
  public   WholeTextFileRecordReader (org.apache.hadoop.mapreduce.lib.input.CombineFileSplit split, org.apache.hadoop.mapreduce.TaskAttemptContext context, java.lang.Integer index) { throw new RuntimeException(); }
  private  org.apache.hadoop.fs.Path path () { throw new RuntimeException(); }
  private  org.apache.hadoop.fs.FileSystem fs () { throw new RuntimeException(); }
  private  boolean processed () { throw new RuntimeException(); }
  private  java.lang.String key () { throw new RuntimeException(); }
  private  java.lang.String value () { throw new RuntimeException(); }
  public  void initialize (org.apache.hadoop.mapreduce.InputSplit split, org.apache.hadoop.mapreduce.TaskAttemptContext context) { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
  public  float getProgress () { throw new RuntimeException(); }
  public  java.lang.String getCurrentKey () { throw new RuntimeException(); }
  public  java.lang.String getCurrentValue () { throw new RuntimeException(); }
  public  boolean nextKeyValue () { throw new RuntimeException(); }
}
