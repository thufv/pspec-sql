package org.apache.spark.storage;
/**
 * References a particular segment of a file (potentially the entire file), based off an offset and
 * a length.
 */
private  class TachyonFileSegment {
  public  tachyon.client.TachyonFile file () { throw new RuntimeException(); }
  public  long offset () { throw new RuntimeException(); }
  public  long length () { throw new RuntimeException(); }
  // not preceding
  public   TachyonFileSegment (tachyon.client.TachyonFile file, long offset, long length) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
