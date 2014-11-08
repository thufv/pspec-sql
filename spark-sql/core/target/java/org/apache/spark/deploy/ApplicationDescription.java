package org.apache.spark.deploy;
private  class ApplicationDescription implements scala.Serializable {
  public  java.lang.String name () { throw new RuntimeException(); }
  public  scala.Option<java.lang.Object> maxCores () { throw new RuntimeException(); }
  public  int memoryPerSlave () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.Command command () { throw new RuntimeException(); }
  public  java.lang.String appUiUrl () { throw new RuntimeException(); }
  public  scala.Option<java.lang.String> eventLogDir () { throw new RuntimeException(); }
  // not preceding
  public   ApplicationDescription (java.lang.String name, scala.Option<java.lang.Object> maxCores, int memoryPerSlave, org.apache.spark.deploy.Command command, java.lang.String appUiUrl, scala.Option<java.lang.String> eventLogDir) { throw new RuntimeException(); }
  public  java.lang.String user () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
