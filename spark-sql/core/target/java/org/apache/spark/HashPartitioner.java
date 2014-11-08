package org.apache.spark;
/**
 * A {@link org.apache.spark.Partitioner} that implements hash-based partitioning using
 * Java's <code>Object.hashCode</code>.
 * <p>
 * Java arrays have hashCodes that are based on the arrays' identities rather than their contents,
 * so attempting to partition an RDD[Array[_} or RDD[(Array[_], _)] using a HashPartitioner will
 * produce an unexpected or incorrect result.
 */
public  class HashPartitioner extends org.apache.spark.Partitioner {
  public   HashPartitioner (int partitions) { throw new RuntimeException(); }
  public  int numPartitions () { throw new RuntimeException(); }
  public  int getPartition (Object key) { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
}
