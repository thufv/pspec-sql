package org.apache.spark.api.python;
// no position
private  class SpecialLengths {
  static public  int END_OF_DATA_SECTION () { throw new RuntimeException(); }
  static public  int PYTHON_EXCEPTION_THROWN () { throw new RuntimeException(); }
  static public  int TIMING_DATA () { throw new RuntimeException(); }
}
