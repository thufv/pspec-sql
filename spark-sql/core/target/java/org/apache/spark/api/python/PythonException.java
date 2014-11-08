package org.apache.spark.api.python;
/** Thrown for exceptions in user Python code. */
private  class PythonException extends java.lang.RuntimeException {
  public   PythonException (java.lang.String msg, java.lang.Exception cause) { throw new RuntimeException(); }
}
