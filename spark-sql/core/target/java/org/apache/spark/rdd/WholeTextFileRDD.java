package org.apache.spark.rdd;
/**
 * Analogous to {@link org.apache.spark.rdd.MapPartitionsRDD}, but passes in an InputSplit to
 * the given function rather than the index of the partition.
 */
private  class WholeTextFileRDD extends org.apache.spark.rdd.NewHadoopRDD<java.lang.String, java.lang.String> {
  public   WholeTextFileRDD (org.apache.spark.SparkContext sc, java.lang.Class<? extends org.apache.spark.input.WholeTextFileInputFormat> inputFormatClass, java.lang.Class<java.lang.String> keyClass, java.lang.Class<java.lang.String> valueClass, org.apache.hadoop.conf.Configuration conf, int minPartitions) { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
}
