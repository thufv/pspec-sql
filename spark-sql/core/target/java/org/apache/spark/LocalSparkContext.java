package org.apache.spark;
/** Manages a local `sc` {@link SparkContext} variable, correctly stopping it after each test. */
public abstract interface LocalSparkContext extends org.scalatest.BeforeAndAfterEach, org.scalatest.BeforeAndAfterAll {
  static public  void stop (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  /** Runs `f` by passing in `sc` and ensures that `sc` is stopped. */
  static public <T extends java.lang.Object> T withSpark (org.apache.spark.SparkContext sc, scala.Function1<org.apache.spark.SparkContext, T> f) { throw new RuntimeException(); }
  public  org.apache.spark.SparkContext sc () ;
  public  void beforeAll () ;
  public  void afterEach () ;
  public  void resetSparkContext () ;
}
