package org.apache.spark.partial;
/**
 * :: Experimental ::
 * A Double value with error bars and associated confidence.
 */
public  class BoundedDouble {
  public  double mean () { throw new RuntimeException(); }
  public  double confidence () { throw new RuntimeException(); }
  public  double low () { throw new RuntimeException(); }
  public  double high () { throw new RuntimeException(); }
  // not preceding
  public   BoundedDouble (double mean, double confidence, double low, double high) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
