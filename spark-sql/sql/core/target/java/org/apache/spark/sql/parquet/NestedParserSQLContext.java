package org.apache.spark.sql.parquet;
public  class NestedParserSQLContext extends org.apache.spark.sql.SQLContext {
  public  org.apache.spark.SparkContext sparkContext () { throw new RuntimeException(); }
  // not preceding
  public   NestedParserSQLContext (org.apache.spark.SparkContext sparkContext) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.parquet.NestedSqlParser parser () { throw new RuntimeException(); }
}
