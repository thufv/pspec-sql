package org.apache.spark.sql.hive.api.java;
/**
 * The entry point for executing Spark SQL queries from a Java program.
 */
public  class JavaHiveContext extends org.apache.spark.sql.api.java.JavaSQLContext {
  public   JavaHiveContext (org.apache.spark.api.java.JavaSparkContext sparkContext) { throw new RuntimeException(); }
  public  org.apache.spark.sql.hive.HiveContext sqlContext () { throw new RuntimeException(); }
  public  org.apache.spark.sql.api.java.JavaSchemaRDD sql (java.lang.String sqlText) { throw new RuntimeException(); }
  /**
   * DEPRECATED: Use sql(...) Instead
   */
  public  org.apache.spark.sql.api.java.JavaSchemaRDD hql (java.lang.String hqlQuery) { throw new RuntimeException(); }
}
