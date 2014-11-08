package org.apache.spark.util.logging;
// no position
/**
 * Companion object to {@link org.apache.spark.util.logging.RollingFileAppender}. Defines
 * names of configurations that configure rolling file appenders.
 */
private  class RollingFileAppender$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final RollingFileAppender$ MODULE$ = null;
  public   RollingFileAppender$ () { throw new RuntimeException(); }
  public  java.lang.String STRATEGY_PROPERTY () { throw new RuntimeException(); }
  public  java.lang.String STRATEGY_DEFAULT () { throw new RuntimeException(); }
  public  java.lang.String INTERVAL_PROPERTY () { throw new RuntimeException(); }
  public  java.lang.String INTERVAL_DEFAULT () { throw new RuntimeException(); }
  public  java.lang.String SIZE_PROPERTY () { throw new RuntimeException(); }
  public  java.lang.String SIZE_DEFAULT () { throw new RuntimeException(); }
  public  java.lang.String RETAINED_FILES_PROPERTY () { throw new RuntimeException(); }
  public  int DEFAULT_BUFFER_SIZE () { throw new RuntimeException(); }
  /**
   * Get the sorted list of rolled over files. This assumes that the all the rolled
   * over file names are prefixed with the <code>activeFileName</code>, and the active file
   * name has the latest logs. So it sorts all the rolled over logs (that are
   * prefixed with <code>activeFileName</code>) and appends the active file
   */
  public  scala.collection.Seq<java.io.File> getSortedRolledOverFiles (java.lang.String directory, java.lang.String activeFileName) { throw new RuntimeException(); }
}
