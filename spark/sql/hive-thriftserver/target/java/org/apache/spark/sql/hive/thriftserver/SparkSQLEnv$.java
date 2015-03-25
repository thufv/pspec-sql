package org.apache.spark.sql.hive.thriftserver;
// no position
/** A singleton object for the master program. The slaves should not access this. */
public  class SparkSQLEnv$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SparkSQLEnv$ MODULE$ = null;
  public   SparkSQLEnv$ () { throw new RuntimeException(); }
  public  org.apache.spark.sql.hive.HiveContext hiveContext () { throw new RuntimeException(); }
  public  org.apache.spark.SparkContext sparkContext () { throw new RuntimeException(); }
  public  void init () { throw new RuntimeException(); }
  /** Cleans up and shuts down the Spark SQL environments. */
  public  void stop () { throw new RuntimeException(); }
}
