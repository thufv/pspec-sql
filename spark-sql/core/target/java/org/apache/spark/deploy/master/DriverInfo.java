package org.apache.spark.deploy.master;
private  class DriverInfo implements scala.Serializable {
  public  long startTime () { throw new RuntimeException(); }
  public  java.lang.String id () { throw new RuntimeException(); }
  public  org.apache.spark.deploy.DriverDescription desc () { throw new RuntimeException(); }
  public  java.util.Date submitDate () { throw new RuntimeException(); }
  // not preceding
  public   DriverInfo (long startTime, java.lang.String id, org.apache.spark.deploy.DriverDescription desc, java.util.Date submitDate) { throw new RuntimeException(); }
  public  scala.Enumeration.Value state () { throw new RuntimeException(); }
  public  scala.Option<java.lang.Exception> exception () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.deploy.master.WorkerInfo> worker () { throw new RuntimeException(); }
  private  void readObject (java.io.ObjectInputStream in) { throw new RuntimeException(); }
  private  void init () { throw new RuntimeException(); }
}
