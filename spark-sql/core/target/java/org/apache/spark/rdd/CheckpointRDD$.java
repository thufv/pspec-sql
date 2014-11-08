package org.apache.spark.rdd;
// no position
private  class CheckpointRDD$ implements org.apache.spark.Logging, scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final CheckpointRDD$ MODULE$ = null;
  public   CheckpointRDD$ () { throw new RuntimeException(); }
  public  java.lang.String splitIdToFile (int splitId) { throw new RuntimeException(); }
  public <T extends java.lang.Object> void writeToFile (java.lang.String path, org.apache.spark.broadcast.Broadcast<org.apache.spark.SerializableWritable<org.apache.hadoop.conf.Configuration>> broadcastedConf, int blockSize, org.apache.spark.TaskContext ctx, scala.collection.Iterator<T> iterator, scala.reflect.ClassTag<T> evidence$2) { throw new RuntimeException(); }
  public <T extends java.lang.Object> scala.collection.Iterator<T> readFromFile (org.apache.hadoop.fs.Path path, org.apache.spark.broadcast.Broadcast<org.apache.spark.SerializableWritable<org.apache.hadoop.conf.Configuration>> broadcastedConf, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  void main (java.lang.String[] args) { throw new RuntimeException(); }
}
