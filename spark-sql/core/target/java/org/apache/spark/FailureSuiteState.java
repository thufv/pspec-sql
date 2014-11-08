package org.apache.spark;
// no position
public  class FailureSuiteState {
  static public  int tasksRun () { throw new RuntimeException(); }
  static public  int tasksFailed () { throw new RuntimeException(); }
  static public  void clear () { throw new RuntimeException(); }
}
