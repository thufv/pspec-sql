package org.apache.spark;
/**
 * A partition of an RDD.
 */
public abstract interface Partition extends scala.Serializable {
  /**
   * Get the split's index within its parent RDD
   */
  public abstract  int index () ;
  public  int hashCode () ;
}
