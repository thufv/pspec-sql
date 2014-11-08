package org.apache.spark.executor;
/**
 * :: DeveloperApi ::
 * Metrics about reading input data.
 */
public  class InputMetrics implements scala.Product, scala.Serializable {
  public  scala.Enumeration.Value readMethod () { throw new RuntimeException(); }
  // not preceding
  public   InputMetrics (scala.Enumeration.Value readMethod) { throw new RuntimeException(); }
  /**
   * Total bytes read.
   */
  public  long bytesRead () { throw new RuntimeException(); }
}
