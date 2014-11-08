package org.apache.spark;
// no position
/**
 * Holds state shared across task threads in some ThreadingSuite tests.
 */
public  class ThreadingSuiteState {
  static public  java.util.concurrent.atomic.AtomicInteger runningThreads () { throw new RuntimeException(); }
  static public  java.util.concurrent.atomic.AtomicBoolean failed () { throw new RuntimeException(); }
  static public  void clear () { throw new RuntimeException(); }
}
