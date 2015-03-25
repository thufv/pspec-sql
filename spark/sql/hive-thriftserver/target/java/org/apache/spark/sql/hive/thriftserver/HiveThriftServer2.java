package org.apache.spark.sql.hive.thriftserver;
public  class HiveThriftServer2 extends org.apache.hive.service.server.HiveServer2 implements org.apache.spark.sql.hive.thriftserver.ReflectedCompositeService {
  /**
   * A inner sparkListener called in sc.stop to clean up the HiveThriftServer2
   */
  static public  class HiveThriftServer2Listener implements org.apache.spark.scheduler.SparkListener {
    public  org.apache.hive.service.server.HiveServer2 server () { throw new RuntimeException(); }
    // not preceding
    public   HiveThriftServer2Listener (org.apache.hive.service.server.HiveServer2 server) { throw new RuntimeException(); }
    public  void onApplicationEnd (org.apache.spark.scheduler.SparkListenerApplicationEnd applicationEnd) { throw new RuntimeException(); }
  }
  static public  org.apache.commons.logging.Log LOG () { throw new RuntimeException(); }
  /**
   * :: DeveloperApi ::
   * Starts a new thrift server with the given context.
   */
  static public  void startWithContext (org.apache.spark.sql.hive.HiveContext sqlContext) { throw new RuntimeException(); }
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  public   HiveThriftServer2 (org.apache.spark.sql.hive.HiveContext hiveContext) { throw new RuntimeException(); }
  public  void init (org.apache.hadoop.hive.conf.HiveConf hiveConf) { throw new RuntimeException(); }
  private  boolean isHTTPTransportMode (org.apache.hadoop.hive.conf.HiveConf hiveConf) { throw new RuntimeException(); }
}
