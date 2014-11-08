package org.apache.spark.rdd;
private  class CartesianRDD<T extends java.lang.Object, U extends java.lang.Object> extends org.apache.spark.rdd.RDD<scala.Tuple2<T, U>> implements scala.Serializable {
  public  org.apache.spark.rdd.RDD<T> rdd1 () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<U> rdd2 () { throw new RuntimeException(); }
  // not preceding
  public   CartesianRDD (org.apache.spark.SparkContext sc, org.apache.spark.rdd.RDD<T> rdd1, org.apache.spark.rdd.RDD<U> rdd2, scala.reflect.ClassTag<T> evidence$1, scala.reflect.ClassTag<U> evidence$2) { throw new RuntimeException(); }
  public  int numPartitionsInRdd2 () { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition split) { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<T, U>> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.Dependency<?>> getDependencies () { throw new RuntimeException(); }
  public  void clearDependencies () { throw new RuntimeException(); }
}
