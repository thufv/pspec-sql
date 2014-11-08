package org.apache.spark.rdd;
private  class SampledRDD<T extends java.lang.Object> extends org.apache.spark.rdd.RDD<T> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   SampledRDD (org.apache.spark.rdd.RDD<T> prev, boolean withReplacement, double frac, int seed, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition split) { throw new RuntimeException(); }
  public  scala.collection.Iterator<T> compute (org.apache.spark.Partition splitIn, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
}
