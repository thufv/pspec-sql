package org.apache.spark.sql.hive.thriftserver;
public  class SparkSQLSessionManager extends org.apache.hive.service.cli.session.SessionManager implements org.apache.spark.sql.hive.thriftserver.ReflectedCompositeService {
  public   SparkSQLSessionManager (org.apache.spark.sql.hive.HiveContext hiveContext) { throw new RuntimeException(); }
  private  org.apache.spark.sql.hive.thriftserver.server.SparkSQLOperationManager sparkSqlOperationManager () { throw new RuntimeException(); }
  public  void init (org.apache.hadoop.hive.conf.HiveConf hiveConf) { throw new RuntimeException(); }
  public  void closeSession (org.apache.hive.service.cli.SessionHandle sessionHandle) { throw new RuntimeException(); }
}
