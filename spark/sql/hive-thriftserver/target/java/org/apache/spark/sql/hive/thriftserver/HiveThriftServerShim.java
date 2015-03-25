package org.apache.spark.sql.hive.thriftserver;
// no position
/**
 * A compatibility layer for interacting with Hive version 0.13.1.
 */
public  class HiveThriftServerShim {
  static public  java.lang.String version () { throw new RuntimeException(); }
  static public  void setServerUserName (org.apache.hadoop.security.UserGroupInformation sparkServiceUGI, org.apache.spark.sql.hive.thriftserver.SparkSQLCLIService sparkCliService) { throw new RuntimeException(); }
}
