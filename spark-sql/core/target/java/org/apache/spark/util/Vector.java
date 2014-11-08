package org.apache.spark.util;
public  class Vector implements scala.Serializable {
  // no position
  static public  class VectorAccumParam$ implements org.apache.spark.AccumulatorParam<org.apache.spark.util.Vector> {
    public   VectorAccumParam$ () { throw new RuntimeException(); }
    public  org.apache.spark.util.Vector addInPlace (org.apache.spark.util.Vector t1, org.apache.spark.util.Vector t2) { throw new RuntimeException(); }
    public  org.apache.spark.util.Vector zero (org.apache.spark.util.Vector initialValue) { throw new RuntimeException(); }
  }
  static public  class Multiplier {
    public   Multiplier (double num) { throw new RuntimeException(); }
  }
  static public  org.apache.spark.util.Vector zeros (int length) { throw new RuntimeException(); }
  static public  org.apache.spark.util.Vector ones (int length) { throw new RuntimeException(); }
  /**
   * Creates this {@link org.apache.spark.util.Vector} of given length containing random numbers
   * between 0.0 and 1.0. Optional scala.util.Random number generator can be provided.
   */
  static public  org.apache.spark.util.Vector random (int length, scala.util.Random random) { throw new RuntimeException(); }
  static public  org.apache.spark.util.Vector.Multiplier doubleToMultiplier (double num) { throw new RuntimeException(); }
  public  double[] elements () { throw new RuntimeException(); }
  // not preceding
  public   Vector (double[] elements) { throw new RuntimeException(); }
  public  int length () { throw new RuntimeException(); }
  public  double apply (int index) { throw new RuntimeException(); }
  public  org.apache.spark.util.Vector add (org.apache.spark.util.Vector other) { throw new RuntimeException(); }
  public  org.apache.spark.util.Vector subtract (org.apache.spark.util.Vector other) { throw new RuntimeException(); }
  public  double dot (org.apache.spark.util.Vector other) { throw new RuntimeException(); }
  /**
   * return (this + plus) dot other, but without creating any intermediate storage
   * @param plus
   * @param other
   * @return
   */
  public  double plusDot (org.apache.spark.util.Vector plus, org.apache.spark.util.Vector other) { throw new RuntimeException(); }
  public  org.apache.spark.util.Vector addInPlace (org.apache.spark.util.Vector other) { throw new RuntimeException(); }
  public  org.apache.spark.util.Vector multiply (double d) { throw new RuntimeException(); }
  public  org.apache.spark.util.Vector divide (double d) { throw new RuntimeException(); }
  public  double sum () { throw new RuntimeException(); }
  public  double squaredDist (org.apache.spark.util.Vector other) { throw new RuntimeException(); }
  public  double dist (org.apache.spark.util.Vector other) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
