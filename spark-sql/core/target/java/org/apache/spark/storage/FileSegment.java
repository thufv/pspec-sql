package org.apache.spark.storage;
/**
 * References a particular segment of a file (potentially the entire file),
 * based off an offset and a length.
 */
private  class FileSegment {
  public  java.io.File file () { throw new RuntimeException(); }
  public  long offset () { throw new RuntimeException(); }
  public  long length () { throw new RuntimeException(); }
  // not preceding
  public   FileSegment (java.io.File file, long offset, long length) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
