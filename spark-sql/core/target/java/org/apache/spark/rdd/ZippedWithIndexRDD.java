package org.apache.spark.rdd;
/**
 * Represents a RDD zipped with its element indices. The ordering is first based on the partition
 * index and then the ordering of items within each partition. So the first item in the first
 * partition gets index 0, and the last item in the last partition receives the largest index.
 * <p>
 * @param prev parent RDD
 * @tparam T parent RDD item type
 */
private  class ZippedWithIndexRDD<T extends java.lang.Object> extends org.apache.spark.rdd.RDD<scala.Tuple2<T, java.lang.Object>> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   ZippedWithIndexRDD (org.apache.spark.rdd.RDD<T> prev, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition split) { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<T, java.lang.Object>> compute (org.apache.spark.Partition splitIn, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
}
