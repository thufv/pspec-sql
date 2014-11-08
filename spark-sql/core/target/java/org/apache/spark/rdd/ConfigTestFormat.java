package org.apache.spark.rdd;
public  class ConfigTestFormat extends org.apache.spark.rdd.FakeFormat implements org.apache.hadoop.conf.Configurable {
  public   ConfigTestFormat () { throw new RuntimeException(); }
  public  boolean setConfCalled () { throw new RuntimeException(); }
  public  void setConf (org.apache.hadoop.conf.Configuration p1) { throw new RuntimeException(); }
  public  org.apache.hadoop.conf.Configuration getConf () { throw new RuntimeException(); }
  public  org.apache.hadoop.mapreduce.RecordWriter<java.lang.Integer, java.lang.Integer> getRecordWriter (org.apache.hadoop.mapreduce.TaskAttemptContext p1) { throw new RuntimeException(); }
}
