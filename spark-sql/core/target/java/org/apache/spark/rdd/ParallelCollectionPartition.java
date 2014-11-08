package org.apache.spark.rdd;
private  class ParallelCollectionPartition<T extends java.lang.Object> implements org.apache.spark.Partition, scala.Serializable {
  public  long rddId () { throw new RuntimeException(); }
  public  int slice () { throw new RuntimeException(); }
  public  scala.collection.Seq<T> values () { throw new RuntimeException(); }
  // not preceding
  public   ParallelCollectionPartition (long rddId, int slice, scala.collection.Seq<T> values, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  scala.collection.Iterator<T> iterator () { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
  public  int index () { throw new RuntimeException(); }
  private  void writeObject (java.io.ObjectOutputStream out) { throw new RuntimeException(); }
  private  void readObject (java.io.ObjectInputStream in) { throw new RuntimeException(); }
}
