package org.apache.spark.shuffle.sort;
private  class SortShuffleWriter<K extends java.lang.Object, V extends java.lang.Object, C extends java.lang.Object> implements org.apache.spark.shuffle.ShuffleWriter<K, V>, org.apache.spark.Logging {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   SortShuffleWriter (org.apache.spark.shuffle.BaseShuffleHandle<K, V, C> handle, int mapId, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  private  org.apache.spark.ShuffleDependency<K, V, C> dep () { throw new RuntimeException(); }
  private  int numPartitions () { throw new RuntimeException(); }
  private  org.apache.spark.storage.BlockManager blockManager () { throw new RuntimeException(); }
  private  org.apache.spark.serializer.Serializer ser () { throw new RuntimeException(); }
  private  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  private  int fileBufferSize () { throw new RuntimeException(); }
  private  Object sorter () { throw new RuntimeException(); }
  private  java.io.File outputFile () { throw new RuntimeException(); }
  private  java.io.File indexFile () { throw new RuntimeException(); }
  private  boolean stopping () { throw new RuntimeException(); }
  private  org.apache.spark.scheduler.MapStatus mapStatus () { throw new RuntimeException(); }
  private  org.apache.spark.executor.ShuffleWriteMetrics writeMetrics () { throw new RuntimeException(); }
  /** Write a bunch of records to this task's output */
  public  void write (scala.collection.Iterator<scala.Product2<K, V>> records) { throw new RuntimeException(); }
  /** Close this writer, passing along whether the map completed */
  public  scala.Option<org.apache.spark.scheduler.MapStatus> stop (boolean success) { throw new RuntimeException(); }
}
