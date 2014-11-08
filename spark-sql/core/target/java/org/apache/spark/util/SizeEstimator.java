package org.apache.spark.util;
// no position
/**
 * Estimates the sizes of Java objects (number of bytes of memory they occupy), for use in
 * memory-aware caches.
 * <p>
 * Based on the following JavaWorld article:
 * http://www.javaworld.com/javaworld/javaqa/2003-12/02-qa-1226-sizeof.html
 */
private  class SizeEstimator implements org.apache.spark.Logging {
  /**
   * The state of an ongoing size estimation. Contains a stack of objects to visit as well as an
   * IdentityHashMap of visited objects, and provides utility methods for enqueueing new objects
   * to visit.
   */
  static private  class SearchState {
    public  java.util.IdentityHashMap<java.lang.Object, java.lang.Object> visited () { throw new RuntimeException(); }
    // not preceding
    public   SearchState (java.util.IdentityHashMap<java.lang.Object, java.lang.Object> visited) { throw new RuntimeException(); }
    public  scala.collection.mutable.ArrayBuffer<java.lang.Object> stack () { throw new RuntimeException(); }
    public  long size () { throw new RuntimeException(); }
    public  void enqueue (java.lang.Object obj) { throw new RuntimeException(); }
    public  boolean isFinished () { throw new RuntimeException(); }
    public  java.lang.Object dequeue () { throw new RuntimeException(); }
  }
  /**
   * Cached information about each class. We remember two things: the "shell size" of the class
   * (size of all non-static fields plus the java.lang.Object size), and any fields that are
   * pointers to objects.
   */
  static private  class ClassInfo {
    public  long shellSize () { throw new RuntimeException(); }
    public  scala.collection.immutable.List<java.lang.reflect.Field> pointerFields () { throw new RuntimeException(); }
    // not preceding
    public   ClassInfo (long shellSize, scala.collection.immutable.List<java.lang.reflect.Field> pointerFields) { throw new RuntimeException(); }
  }
  static private  int BYTE_SIZE () { throw new RuntimeException(); }
  static private  int BOOLEAN_SIZE () { throw new RuntimeException(); }
  static private  int CHAR_SIZE () { throw new RuntimeException(); }
  static private  int SHORT_SIZE () { throw new RuntimeException(); }
  static private  int INT_SIZE () { throw new RuntimeException(); }
  static private  int LONG_SIZE () { throw new RuntimeException(); }
  static private  int FLOAT_SIZE () { throw new RuntimeException(); }
  static private  int DOUBLE_SIZE () { throw new RuntimeException(); }
  static private  int ALIGN_SIZE () { throw new RuntimeException(); }
  static private  java.util.concurrent.ConcurrentHashMap<java.lang.Class<?>, org.apache.spark.util.SizeEstimator.ClassInfo> classInfos () { throw new RuntimeException(); }
  static private  boolean is64bit () { throw new RuntimeException(); }
  static private  boolean isCompressedOops () { throw new RuntimeException(); }
  static private  int pointerSize () { throw new RuntimeException(); }
  static private  int objectSize () { throw new RuntimeException(); }
  static private  void initialize () { throw new RuntimeException(); }
  static private  boolean getIsCompressedOops () { throw new RuntimeException(); }
  static public  long estimate (java.lang.Object obj) { throw new RuntimeException(); }
  static private  long estimate (java.lang.Object obj, java.util.IdentityHashMap<java.lang.Object, java.lang.Object> visited) { throw new RuntimeException(); }
  static private  void visitSingleObject (java.lang.Object obj, org.apache.spark.util.SizeEstimator.SearchState state) { throw new RuntimeException(); }
  static private  int ARRAY_SIZE_FOR_SAMPLING () { throw new RuntimeException(); }
  static private  int ARRAY_SAMPLE_SIZE () { throw new RuntimeException(); }
  static private  void visitArray (java.lang.Object array, java.lang.Class<?> cls, org.apache.spark.util.SizeEstimator.SearchState state) { throw new RuntimeException(); }
  static private  long primitiveSize (java.lang.Class<?> cls) { throw new RuntimeException(); }
  /**
   * Get or compute the ClassInfo for a given class.
   */
  static private  org.apache.spark.util.SizeEstimator.ClassInfo getClassInfo (java.lang.Class<?> cls) { throw new RuntimeException(); }
  static private  long alignSize (long size) { throw new RuntimeException(); }
}
