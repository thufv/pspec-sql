package org.apache.spark.sql.hive.thriftserver;
public abstract class AbstractSparkSQLDriver extends org.apache.hadoop.hive.ql.Driver implements org.apache.spark.Logging {
  public  org.apache.spark.sql.hive.HiveContext context () { throw new RuntimeException(); }
  // not preceding
  public   AbstractSparkSQLDriver (org.apache.spark.sql.hive.HiveContext context) { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.metastore.api.Schema tableSchema () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> hiveResponse () { throw new RuntimeException(); }
  public  void init () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.metastore.api.Schema getResultSetSchema (org.apache.spark.sql.hive.HiveContext.QueryExecution query) { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.ql.processors.CommandProcessorResponse run (java.lang.String command) { throw new RuntimeException(); }
  public  int close () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.metastore.api.Schema getSchema () { throw new RuntimeException(); }
  public  void destroy () { throw new RuntimeException(); }
}
