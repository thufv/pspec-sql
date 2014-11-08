package org.apache.spark.util;
/**
 * :: DeveloperApi ::
 * A tuple of 2 elements. This can be used as an alternative to Scala's Tuple2 when we want to
 * minimize object allocation.
 * <p>
 * @param  _1   Element 1 of this MutablePair
 * @param  _2   Element 2 of this MutablePair
 */
public  class MutablePair<T1 extends java.lang.Object, T2 extends java.lang.Object> implements scala.Product2<T1, T2>, scala.Product, scala.Serializable {
  public  T1 _1 () { throw new RuntimeException(); }
  public  T2 _2 () { throw new RuntimeException(); }
  // not preceding
  public   MutablePair (T1 _1, T2 _2) { throw new RuntimeException(); }
  /** No-arg constructor for serialization */
  public   MutablePair () { throw new RuntimeException(); }
  /** Updates this pair with new values and returns itself */
  public  org.apache.spark.util.MutablePair<T1, T2> update (T1 n1, T2 n2) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  boolean canEqual (Object that) { throw new RuntimeException(); }
}
