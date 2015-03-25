package org.apache.spark.sql.hive.thriftserver;
// no position
public  class SparkSQLCLIDriver$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final SparkSQLCLIDriver$ MODULE$ = null;
  public   SparkSQLCLIDriver$ () { throw new RuntimeException(); }
  private  java.lang.String prompt () { throw new RuntimeException(); }
  private  java.lang.String continuedPrompt () { throw new RuntimeException(); }
  private  org.apache.thrift.transport.TSocket transport () { throw new RuntimeException(); }
  private  int number () { throw new RuntimeException(); }
  /**
   * Install an interrupt callback to cancel all Spark jobs. In Hive's CliDriver#processLine(),
   * a signal handler will invoke this registered callback if a Ctrl+C signal is detected while
   * a command is being processed by the current thread.
   */
  public  void installSignalHandler () { throw new RuntimeException(); }
  public  void main (java.lang.String[] args) { throw new RuntimeException(); }
}
