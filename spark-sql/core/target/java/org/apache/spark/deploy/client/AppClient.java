package org.apache.spark.deploy.client;
/**
 * Interface allowing applications to speak with a Spark deploy cluster. Takes a master URL,
 * an app description, and a listener for cluster events, and calls back the listener when various
 * events occur.
 * <p>
 * @param masterUrls Each url should look like spark://host:port.
 */
private  class AppClient implements org.apache.spark.Logging {
  public   AppClient (akka.actor.ActorSystem actorSystem, java.lang.String[] masterUrls, org.apache.spark.deploy.ApplicationDescription appDescription, org.apache.spark.deploy.client.AppClientListener listener, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  scala.concurrent.duration.FiniteDuration REGISTRATION_TIMEOUT () { throw new RuntimeException(); }
  public  int REGISTRATION_RETRIES () { throw new RuntimeException(); }
  public  akka.actor.Address masterAddress () { throw new RuntimeException(); }
  public  akka.actor.ActorRef actor () { throw new RuntimeException(); }
  public  java.lang.String appId () { throw new RuntimeException(); }
  public  boolean registered () { throw new RuntimeException(); }
  public  java.lang.String activeMasterUrl () { throw new RuntimeException(); }
  public  class ClientActor implements akka.actor.Actor, org.apache.spark.util.ActorLogReceive, org.apache.spark.Logging {
    public   ClientActor () { throw new RuntimeException(); }
    public  akka.actor.ActorSelection master () { throw new RuntimeException(); }
    public  boolean alreadyDisconnected () { throw new RuntimeException(); }
    public  boolean alreadyDead () { throw new RuntimeException(); }
    public  scala.Option<akka.actor.Cancellable> registrationRetryTimer () { throw new RuntimeException(); }
    public  void preStart () { throw new RuntimeException(); }
    public  void tryRegisterAllMasters () { throw new RuntimeException(); }
    public  void registerWithMaster () { throw new RuntimeException(); }
    public  void changeMaster (java.lang.String url) { throw new RuntimeException(); }
    // not preceding
    private  boolean isPossibleMaster (akka.actor.Address remoteUrl) { throw new RuntimeException(); }
    public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receiveWithLogging () { throw new RuntimeException(); }
    /**
     * Notify the listener that we disconnected, if we hadn't already done so before.
     */
    public  void markDisconnected () { throw new RuntimeException(); }
    public  void markDead (java.lang.String reason) { throw new RuntimeException(); }
    public  void postStop () { throw new RuntimeException(); }
  }
  public  void start () { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
}
