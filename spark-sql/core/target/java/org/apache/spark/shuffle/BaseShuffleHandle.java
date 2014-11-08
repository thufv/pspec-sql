package org.apache.spark.shuffle;
/**
 * A basic ShuffleHandle implementation that just captures registerShuffle's parameters.
 */
private  class BaseShuffleHandle<K extends java.lang.Object, V extends java.lang.Object, C extends java.lang.Object> extends org.apache.spark.shuffle.ShuffleHandle {
  public  int numMaps () { throw new RuntimeException(); }
  public  org.apache.spark.ShuffleDependency<K, V, C> dependency () { throw new RuntimeException(); }
  // not preceding
  public   BaseShuffleHandle (int shuffleId, int numMaps, org.apache.spark.ShuffleDependency<K, V, C> dependency) { throw new RuntimeException(); }
}
