package org.apache.spark;
/** Pair RDD that has large serialized size. */
public  class FatPairRDD extends org.apache.spark.rdd.RDD<scala.Tuple2<java.lang.Object, java.lang.Object>> {
  public   FatPairRDD (org.apache.spark.rdd.RDD<java.lang.Object> parent, org.apache.spark.Partitioner _partitioner) { throw new RuntimeException(); }
  public  byte[] bigData () { throw new RuntimeException(); }
  protected  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.Some<org.apache.spark.Partitioner> partitioner () { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<java.lang.Object, java.lang.Object>> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
}
