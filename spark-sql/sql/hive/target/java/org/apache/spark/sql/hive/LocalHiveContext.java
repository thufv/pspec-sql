package org.apache.spark.sql.hive;
/**
 * DEPRECATED: Use HiveContext instead.
 */
public  class LocalHiveContext extends org.apache.spark.sql.hive.HiveContext {
  public   LocalHiveContext (org.apache.spark.SparkContext sc) { throw new RuntimeException(); }
  public  java.lang.String metastorePath () { throw new RuntimeException(); }
  public  java.lang.String warehousePath () { throw new RuntimeException(); }
  /** Sets up the system initially or after a RESET command */
  protected  void configure () { throw new RuntimeException(); }
}
