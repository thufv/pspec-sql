package org.apache.spark;
/**
 * Exception type thrown by HttpServer when it is in the wrong state for an operation.
 */
private  class ServerStateException extends java.lang.Exception {
  public   ServerStateException (java.lang.String message) { throw new RuntimeException(); }
}
