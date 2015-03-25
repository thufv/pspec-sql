package org.apache.spark.sql.hive.thriftserver;
public abstract class HiveThriftServer2Test extends org.scalatest.FunSuite implements org.scalatest.BeforeAndAfterAll, org.apache.spark.Logging {
  public   HiveThriftServer2Test () { throw new RuntimeException(); }
  public abstract  scala.Enumeration.Value mode () ;
  private  java.lang.String CLASS_NAME () { throw new RuntimeException(); }
  private  java.lang.String LOG_FILE_MARK () { throw new RuntimeException(); }
  private  java.lang.String startScript () { throw new RuntimeException(); }
  private  java.lang.String stopScript () { throw new RuntimeException(); }
  private  int listeningPort () { throw new RuntimeException(); }
  protected  int serverPort () { throw new RuntimeException(); }
  protected  java.lang.String user () { throw new RuntimeException(); }
  private  java.io.File warehousePath () { throw new RuntimeException(); }
  private  java.io.File metastorePath () { throw new RuntimeException(); }
  private  java.lang.String metastoreJdbcUri () { throw new RuntimeException(); }
  private  java.io.File pidDir () { throw new RuntimeException(); }
  private  java.io.File logPath () { throw new RuntimeException(); }
  private  scala.sys.process.Process logTailingProcess () { throw new RuntimeException(); }
  private  scala.collection.mutable.ArrayBuffer<java.lang.String> diagnosisBuffer () { throw new RuntimeException(); }
  private  scala.collection.Seq<java.lang.String> serverStartCommand (int port) { throw new RuntimeException(); }
  private  void startThriftServer (int port, int attempt) { throw new RuntimeException(); }
  private  void stopThriftServer () { throw new RuntimeException(); }
  private  void dumpLogs () { throw new RuntimeException(); }
  protected  void beforeAll () { throw new RuntimeException(); }
  protected  void afterAll () { throw new RuntimeException(); }
}
