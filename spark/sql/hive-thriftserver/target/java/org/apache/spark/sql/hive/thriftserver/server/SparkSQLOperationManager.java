package org.apache.spark.sql.hive.thriftserver.server;
/**
 * Executes queries using Spark SQL, and maintains a list of handles to active queries.
 */
public  class SparkSQLOperationManager extends org.apache.hive.service.cli.operation.OperationManager implements org.apache.spark.Logging {
  public   SparkSQLOperationManager (org.apache.spark.sql.hive.HiveContext hiveContext) { throw new RuntimeException(); }
  public  java.util.Map<org.apache.hive.service.cli.OperationHandle, org.apache.hive.service.cli.operation.Operation> handleToOperation () { throw new RuntimeException(); }
  public  scala.collection.mutable.Map<org.apache.hive.service.cli.SessionHandle, java.lang.String> sessionToActivePool () { throw new RuntimeException(); }
  public  org.apache.hive.service.cli.operation.ExecuteStatementOperation newExecuteStatementOperation (org.apache.hive.service.cli.session.HiveSession parentSession, java.lang.String statement, java.util.Map<java.lang.String, java.lang.String> confOverlay, boolean async) { throw new RuntimeException(); }
}
