package org.apache.spark.sql.hive.thriftserver;
// no position
/** A singleton object for the master program. The slaves should not access this. */
public  class SparkSQLEnv implements org.apache.spark.Logging {
  static public  org.apache.spark.sql.hive.HiveContext hiveContext () { throw new RuntimeException(); }
  static public  org.apache.spark.SparkContext sparkContext () { throw new RuntimeException(); }
  static public  void init () { throw new RuntimeException(); }
  /** Cleans up and shuts down the Spark SQL environments. */
  static public  void stop () { throw new RuntimeException(); }
}
