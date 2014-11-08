package org.apache.spark.shuffle.hash;
private  class HashShuffleReader<K extends java.lang.Object, C extends java.lang.Object> implements org.apache.spark.shuffle.ShuffleReader<K, C> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   HashShuffleReader (org.apache.spark.shuffle.BaseShuffleHandle<K, ?, C> handle, int startPartition, int endPartition, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  private  Object dep () { throw new RuntimeException(); }
  /** Read the combined key-values for this reduce task */
  public  scala.collection.Iterator<scala.Product2<K, C>> read () { throw new RuntimeException(); }
  /** Close this reader */
  public  void stop () { throw new RuntimeException(); }
}
