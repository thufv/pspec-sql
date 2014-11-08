package org.apache.spark;
// no position
public  class LocalSparkContext$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final LocalSparkContext$ MODULE$ = null;
  public   LocalSparkContext$ () { throw new RuntimeException(); }
  public  void stop (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  /** Runs `f` by passing in `sc` and ensures that `sc` is stopped. */
  public <T extends java.lang.Object> T withSpark (org.apache.spark.SparkContext sc, scala.Function1<org.apache.spark.SparkContext, T> f) { throw new RuntimeException(); }
}
