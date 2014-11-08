package org.apache.spark;
/**
 * Exception thrown when execution of some user code in the driver process fails, e.g.
 * accumulator update fails or failure in takeOrdered (user supplies an Ordering implementation
 * that can be misbehaving.
 */
private  class SparkDriverExecutionException extends org.apache.spark.SparkException {
  public   SparkDriverExecutionException (java.lang.Throwable cause) { throw new RuntimeException(); }
}
