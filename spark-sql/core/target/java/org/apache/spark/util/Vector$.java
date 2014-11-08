package org.apache.spark.util;
// no position
public  class Vector$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final Vector$ MODULE$ = null;
  public   Vector$ () { throw new RuntimeException(); }
  public  org.apache.spark.util.Vector apply (double[] elements) { throw new RuntimeException(); }
  public  org.apache.spark.util.Vector apply (scala.collection.Seq<java.lang.Object> elements) { throw new RuntimeException(); }
  public  org.apache.spark.util.Vector apply (int length, scala.Function1<java.lang.Object, java.lang.Object> initializer) { throw new RuntimeException(); }
  public  org.apache.spark.util.Vector zeros (int length) { throw new RuntimeException(); }
  public  org.apache.spark.util.Vector ones (int length) { throw new RuntimeException(); }
  /**
   * Creates this {@link org.apache.spark.util.Vector} of given length containing random numbers
   * between 0.0 and 1.0. Optional scala.util.Random number generator can be provided.
   */
  public  org.apache.spark.util.Vector random (int length, scala.util.Random random) { throw new RuntimeException(); }
  public  org.apache.spark.util.Vector.Multiplier doubleToMultiplier (double num) { throw new RuntimeException(); }
}
