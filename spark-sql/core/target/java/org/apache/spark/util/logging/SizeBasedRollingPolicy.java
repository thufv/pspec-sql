package org.apache.spark.util.logging;
/**
 * Defines a {@link org.apache.spark.util.logging.RollingPolicy} by which files will be rolled
 * over after reaching a particular size.
 */
private  class SizeBasedRollingPolicy implements org.apache.spark.util.logging.RollingPolicy, org.apache.spark.Logging {
  static public  int MINIMUM_SIZE_BYTES () { throw new RuntimeException(); }
  public  long rolloverSizeBytes () { throw new RuntimeException(); }
  // not preceding
  public   SizeBasedRollingPolicy (long rolloverSizeBytes, boolean checkSizeConstraint) { throw new RuntimeException(); }
  private  long bytesWrittenSinceRollover () { throw new RuntimeException(); }
  public  java.text.SimpleDateFormat formatter () { throw new RuntimeException(); }
  /** Should rollover if the next set of bytes is going to exceed the size limit */
  public  boolean shouldRollover (long bytesToBeWritten) { throw new RuntimeException(); }
  /** Rollover has occurred, so reset the counter */
  public  void rolledOver () { throw new RuntimeException(); }
  /** Increment the bytes that have been written in the current file */
  public  void bytesWritten (long bytes) { throw new RuntimeException(); }
  /** Get the desired name of the rollover file */
  public  java.lang.String generateRolledOverFileSuffix () { throw new RuntimeException(); }
}
