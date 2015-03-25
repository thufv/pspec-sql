package org.apache.spark.sql.hive.thriftserver;
public  class SparkExecuteStatementOperation extends org.apache.hive.service.cli.operation.ExecuteStatementOperation implements org.apache.spark.Logging {
  public   SparkExecuteStatementOperation (org.apache.hive.service.cli.session.HiveSession parentSession, java.lang.String statement, java.util.Map<java.lang.String, java.lang.String> confOverlay, boolean runInBackground, org.apache.spark.sql.hive.HiveContext hiveContext, scala.collection.mutable.Map<org.apache.hive.service.cli.SessionHandle, java.lang.String> sessionToActivePool) { throw new RuntimeException(); }
  private  org.apache.spark.sql.DataFrame result () { throw new RuntimeException(); }
  private  scala.collection.Iterator<org.apache.spark.sql.Row> iter () { throw new RuntimeException(); }
  private  org.apache.spark.sql.types.DataType[] dataTypes () { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
  public  void addNonNullColumnValue (org.apache.spark.sql.Row from, scala.collection.mutable.ArrayBuffer<java.lang.Object> to, int ordinal) { throw new RuntimeException(); }
  public  org.apache.hive.service.cli.RowSet getNextRowSet (org.apache.hive.service.cli.FetchOrientation order, long maxRowsL) { throw new RuntimeException(); }
  public  org.apache.hive.service.cli.TableSchema getResultSetSchema () { throw new RuntimeException(); }
  public  void run () { throw new RuntimeException(); }
}
