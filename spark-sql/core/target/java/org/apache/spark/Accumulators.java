package org.apache.spark;
// no position
private  class Accumulators {
  static public  scala.collection.mutable.Map<java.lang.Object, org.apache.spark.Accumulable<?, ?>> originals () { throw new RuntimeException(); }
  static public  scala.collection.mutable.Map<java.lang.Thread, scala.collection.mutable.Map<java.lang.Object, org.apache.spark.Accumulable<?, ?>>> localAccums () { throw new RuntimeException(); }
  static public  long lastId () { throw new RuntimeException(); }
  static public  long newId () { throw new RuntimeException(); }
  static public  void register (org.apache.spark.Accumulable<?, ?> a, boolean original) { throw new RuntimeException(); }
  static public  void clear () { throw new RuntimeException(); }
  static public  scala.collection.mutable.Map<java.lang.Object, java.lang.Object> values () { throw new RuntimeException(); }
  static public  void add (scala.collection.mutable.Map<java.lang.Object, java.lang.Object> values) { throw new RuntimeException(); }
  static public  java.lang.String stringifyPartialValue (Object partialValue) { throw new RuntimeException(); }
  static public  java.lang.String stringifyValue (Object value) { throw new RuntimeException(); }
}
