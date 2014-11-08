package org.apache.spark.api.python;
/**
 * A {@link org.apache.spark.Partitioner} that performs handling of long-valued keys, for use by the
 * Python API.
 * <p>
 * Stores the unique id() of the Python-side partitioning function so that it is incorporated into
 * equality comparisons.  Correctness requires that the id is a unique identifier for the
 * lifetime of the program (i.e. that it is not re-used as the id of a different partitioning
 * function).  This can be ensured by using the Python id() function and maintaining a reference
 * to the Python partitioning function so that its id() is not reused.
 */
private  class PythonPartitioner extends org.apache.spark.Partitioner {
  public  int numPartitions () { throw new RuntimeException(); }
  public  long pyPartitionFunctionId () { throw new RuntimeException(); }
  // not preceding
  public   PythonPartitioner (int numPartitions, long pyPartitionFunctionId) { throw new RuntimeException(); }
  public  int getPartition (Object key) { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
}
