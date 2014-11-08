package org.apache.spark.rdd;
/**
 * This RDD represents a RDD checkpoint file (similar to HadoopRDD).
 */
private  class CheckpointRDD<T extends java.lang.Object> extends org.apache.spark.rdd.RDD<T> {
  static public  java.lang.String splitIdToFile (int splitId) { throw new RuntimeException(); }
  static public <T extends java.lang.Object> void writeToFile (java.lang.String path, org.apache.spark.broadcast.Broadcast<org.apache.spark.SerializableWritable<org.apache.hadoop.conf.Configuration>> broadcastedConf, int blockSize, org.apache.spark.TaskContext ctx, scala.collection.Iterator<T> iterator, scala.reflect.ClassTag<T> evidence$2) { throw new RuntimeException(); }
  static public <T extends java.lang.Object> scala.collection.Iterator<T> readFromFile (org.apache.hadoop.fs.Path path, org.apache.spark.broadcast.Broadcast<org.apache.spark.SerializableWritable<org.apache.hadoop.conf.Configuration>> broadcastedConf, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  public  java.lang.String checkpointPath () { throw new RuntimeException(); }
  // not preceding
  public   CheckpointRDD (org.apache.spark.SparkContext sc, java.lang.String checkpointPath, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  org.apache.spark.broadcast.Broadcast<org.apache.spark.SerializableWritable<org.apache.hadoop.conf.Configuration>> broadcastedConf () { throw new RuntimeException(); }
  public  org.apache.hadoop.fs.FileSystem fs () { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition split) { throw new RuntimeException(); }
  public  scala.collection.Iterator<T> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  void checkpoint () { throw new RuntimeException(); }
}
