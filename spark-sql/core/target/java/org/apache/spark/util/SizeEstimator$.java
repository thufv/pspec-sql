package org.apache.spark.util;
// no position
/**
 * Estimates the sizes of Java objects (number of bytes of memory they occupy), for use in
 * memory-aware caches.
 * <p>
 * Based on the following JavaWorld article:
 * http://www.javaworld.com/javaworld/javaqa/2003-12/02-qa-1226-sizeof.html
 */
private  class SizeEstimator$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SizeEstimator$ MODULE$ = null;
  public   SizeEstimator$ () { throw new RuntimeException(); }
  private  int BYTE_SIZE () { throw new RuntimeException(); }
  private  int BOOLEAN_SIZE () { throw new RuntimeException(); }
  private  int CHAR_SIZE () { throw new RuntimeException(); }
  private  int SHORT_SIZE () { throw new RuntimeException(); }
  private  int INT_SIZE () { throw new RuntimeException(); }
  private  int LONG_SIZE () { throw new RuntimeException(); }
  private  int FLOAT_SIZE () { throw new RuntimeException(); }
  private  int DOUBLE_SIZE () { throw new RuntimeException(); }
  private  int ALIGN_SIZE () { throw new RuntimeException(); }
  private  java.util.concurrent.ConcurrentHashMap<java.lang.Class<?>, org.apache.spark.util.SizeEstimator.ClassInfo> classInfos () { throw new RuntimeException(); }
  private  boolean is64bit () { throw new RuntimeException(); }
  private  boolean isCompressedOops () { throw new RuntimeException(); }
  private  int pointerSize () { throw new RuntimeException(); }
  private  int objectSize () { throw new RuntimeException(); }
  private  void initialize () { throw new RuntimeException(); }
  private  boolean getIsCompressedOops () { throw new RuntimeException(); }
  public  long estimate (java.lang.Object obj) { throw new RuntimeException(); }
  private  long estimate (java.lang.Object obj, java.util.IdentityHashMap<java.lang.Object, java.lang.Object> visited) { throw new RuntimeException(); }
  private  void visitSingleObject (java.lang.Object obj, org.apache.spark.util.SizeEstimator.SearchState state) { throw new RuntimeException(); }
  private  int ARRAY_SIZE_FOR_SAMPLING () { throw new RuntimeException(); }
  private  int ARRAY_SAMPLE_SIZE () { throw new RuntimeException(); }
  private  void visitArray (java.lang.Object array, java.lang.Class<?> cls, org.apache.spark.util.SizeEstimator.SearchState state) { throw new RuntimeException(); }
  private  long primitiveSize (java.lang.Class<?> cls) { throw new RuntimeException(); }
  /**
   * Get or compute the ClassInfo for a given class.
   */
  private  org.apache.spark.util.SizeEstimator.ClassInfo getClassInfo (java.lang.Class<?> cls) { throw new RuntimeException(); }
  private  long alignSize (long size) { throw new RuntimeException(); }
}
