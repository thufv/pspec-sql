package org.apache.spark.executor;
// no position
/**
 * :: DeveloperApi ::
 * Method by which input data was read.  Network means that the data was read over the network
 * from a remote block manager (which may have stored the data on-disk or in-memory).
 */
public  class DataReadMethod$ extends scala.Enumeration implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final DataReadMethod$ MODULE$ = null;
  public   DataReadMethod$ () { throw new RuntimeException(); }
  public  scala.Enumeration.Value Memory () { throw new RuntimeException(); }
  public  scala.Enumeration.Value Disk () { throw new RuntimeException(); }
  public  scala.Enumeration.Value Hadoop () { throw new RuntimeException(); }
  public  scala.Enumeration.Value Network () { throw new RuntimeException(); }
}
