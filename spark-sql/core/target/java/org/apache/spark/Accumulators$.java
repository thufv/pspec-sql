package org.apache.spark;
// no position
private  class Accumulators$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final Accumulators$ MODULE$ = null;
  public   Accumulators$ () { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<java.lang.Object, org.apache.spark.Accumulable<?, ?>> originals () { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<java.lang.Thread, scala.collection.mutable.Map<java.lang.Object, org.apache.spark.Accumulable<?, ?>>> localAccums () { throw new RuntimeException(); }
  public  long lastId () { throw new RuntimeException(); }
  public  long newId () { throw new RuntimeException(); }
  public  void register (org.apache.spark.Accumulable<?, ?> a, boolean original) { throw new RuntimeException(); }
  public  void clear () { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<java.lang.Object, java.lang.Object> values () { throw new RuntimeException(); }
  public  void add (scala.collection.mutable.Map<java.lang.Object, java.lang.Object> values) { throw new RuntimeException(); }
  public  java.lang.String stringifyPartialValue (Object partialValue) { throw new RuntimeException(); }
  public  java.lang.String stringifyValue (Object value) { throw new RuntimeException(); }
}
