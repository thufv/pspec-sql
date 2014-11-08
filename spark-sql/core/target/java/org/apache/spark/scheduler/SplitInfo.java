package org.apache.spark.scheduler;
public  class SplitInfo {
  static public  scala.collection.Seq<org.apache.spark.scheduler.SplitInfo> toSplitInfo (java.lang.Class<?> inputFormatClazz, java.lang.String path, org.apache.hadoop.mapred.InputSplit mapredSplit) { throw new RuntimeException(); }
  static public  scala.collection.Seq<org.apache.spark.scheduler.SplitInfo> toSplitInfo (java.lang.Class<?> inputFormatClazz, java.lang.String path, org.apache.hadoop.mapreduce.InputSplit mapreduceSplit) { throw new RuntimeException(); }
  public  Object inputFormatClazz () { throw new RuntimeException(); }
  public  java.lang.String hostLocation () { throw new RuntimeException(); }
  public  java.lang.String path () { throw new RuntimeException(); }
  public  long length () { throw new RuntimeException(); }
  public  Object underlyingSplit () { throw new RuntimeException(); }
  // not preceding
  public   SplitInfo (java.lang.Class<?> inputFormatClazz, java.lang.String hostLocation, java.lang.String path, long length, Object underlyingSplit) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
}
