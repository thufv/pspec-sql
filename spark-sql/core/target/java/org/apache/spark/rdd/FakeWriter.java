package org.apache.spark.rdd;
public  class FakeWriter extends org.apache.hadoop.mapreduce.RecordWriter<java.lang.Integer, java.lang.Integer> {
  public   FakeWriter () { throw new RuntimeException(); }
  public  void close (org.apache.hadoop.mapreduce.TaskAttemptContext p1) { throw new RuntimeException(); }
  public  void write (java.lang.Integer p1, java.lang.Integer p2) { throw new RuntimeException(); }
}
