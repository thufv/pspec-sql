package org.apache.spark.sql.hive.thriftserver;
public  class SparkSQLCLIDriver extends org.apache.hadoop.hive.cli.CliDriver implements org.apache.spark.Logging {
  static private  java.lang.String prompt () { throw new RuntimeException(); }
  static private  java.lang.String continuedPrompt () { throw new RuntimeException(); }
  static private  org.apache.thrift.transport.TSocket transport () { throw new RuntimeException(); }
  static private  int number () { throw new RuntimeException(); }
  /**
   * Install an interrupt callback to cancel all Spark jobs. In Hive's CliDriver#processLine(),
   * a signal handler will invoke this registered callback if a Ctrl+C signal is detected while
   * a command is being processed by the current thread.
   */
  static public  void installSignalHandler () { throw new RuntimeException(); }
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  public   SparkSQLCLIDriver () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.cli.CliSessionState sessionState () { throw new RuntimeException(); }
  private  org.apache.commons.logging.Log LOG () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.session.SessionState.LogHelper console () { throw new RuntimeException(); }
  private  org.apache.hadoop.conf.Configuration conf () { throw new RuntimeException(); }
  public  int processCmd (java.lang.String cmd) { throw new RuntimeException(); }
}
