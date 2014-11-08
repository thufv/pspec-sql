package org.apache.spark.util.logging;
/**
 * Defines the policy based on which {@link org.apache.spark.util.logging.RollingFileAppender} will
 * generate rolling files.
 */
private  interface RollingPolicy {
  /** Whether rollover should be initiated at this moment */
  public abstract  boolean shouldRollover (long bytesToBeWritten) ;
  /** Notify that rollover has occurred */
  public abstract  void rolledOver () ;
  /** Notify that bytes have been written */
  public abstract  void bytesWritten (long bytes) ;
  /** Get the desired name of the rollover file */
  public abstract  java.lang.String generateRolledOverFileSuffix () ;
}
