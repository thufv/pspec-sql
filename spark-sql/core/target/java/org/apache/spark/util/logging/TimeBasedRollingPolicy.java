package org.apache.spark.util.logging;
/**
 * Defines a {@link org.apache.spark.util.logging.RollingPolicy} by which files will be rolled
 * over at a fixed interval.
 */
private  class TimeBasedRollingPolicy implements org.apache.spark.util.logging.RollingPolicy, org.apache.spark.Logging {
  static public  long MINIMUM_INTERVAL_SECONDS () { throw new RuntimeException(); }
  public  long rolloverIntervalMillis () { throw new RuntimeException(); }
  // not preceding
  public   TimeBasedRollingPolicy (long rolloverIntervalMillis, java.lang.String rollingFileSuffixPattern, boolean checkIntervalConstraint) { throw new RuntimeException(); }
  private  long nextRolloverTime () { throw new RuntimeException(); }
  private  java.text.SimpleDateFormat formatter () { throw new RuntimeException(); }
  /** Should rollover if current time has exceeded next rollover time */
  public  boolean shouldRollover (long bytesToBeWritten) { throw new RuntimeException(); }
  /** Rollover has occurred, so find the next time to rollover */
  public  void rolledOver () { throw new RuntimeException(); }
  public  void bytesWritten (long bytes) { throw new RuntimeException(); }
  private  long calculateNextRolloverTime () { throw new RuntimeException(); }
  public  java.lang.String generateRolledOverFileSuffix () { throw new RuntimeException(); }
}
