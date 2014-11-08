package org.apache.spark.deploy.master;
// no position
private  class Master$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final Master$ MODULE$ = null;
  public   Master$ () { throw new RuntimeException(); }
  public  java.lang.String systemName () { throw new RuntimeException(); }
  private  java.lang.String actorName () { throw new RuntimeException(); }
  public  scala.util.matching.Regex sparkUrlRegex () { throw new RuntimeException(); }
  public  void main (java.lang.String[] argStrings) { throw new RuntimeException(); }
  /** Returns an `akka.tcp://...` URL for the Master actor given a sparkUrl `spark://host:ip`. */
  public  java.lang.String toAkkaUrl (java.lang.String sparkUrl) { throw new RuntimeException(); }
  // not preceding
  public  scala.Tuple3<akka.actor.ActorSystem, java.lang.Object, java.lang.Object> startSystemAndActor (java.lang.String host, int port, int webUiPort, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
}
