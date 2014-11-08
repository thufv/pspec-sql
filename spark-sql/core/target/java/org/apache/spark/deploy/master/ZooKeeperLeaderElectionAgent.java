package org.apache.spark.deploy.master;
private  class ZooKeeperLeaderElectionAgent implements org.apache.spark.deploy.master.LeaderElectionAgent, org.apache.curator.framework.recipes.leader.LeaderLatchListener, org.apache.spark.Logging {
  public  akka.actor.ActorRef masterActor () { throw new RuntimeException(); }
  // not preceding
  public   ZooKeeperLeaderElectionAgent (akka.actor.ActorRef masterActor, java.lang.String masterUrl, org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  public  java.lang.String WORKING_DIR () { throw new RuntimeException(); }
  private  org.apache.curator.framework.CuratorFramework zk () { throw new RuntimeException(); }
  private  org.apache.curator.framework.recipes.leader.LeaderLatch leaderLatch () { throw new RuntimeException(); }
  private  scala.Enumeration.Value status () { throw new RuntimeException(); }
  public  void preStart () { throw new RuntimeException(); }
  public  void preRestart (java.lang.Throwable reason, scala.Option<java.lang.Object> message) { throw new RuntimeException(); }
  public  void postStop () { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receive () { throw new RuntimeException(); }
  public  void isLeader () { throw new RuntimeException(); }
  public  void notLeader () { throw new RuntimeException(); }
  public  void updateLeadershipStatus (boolean isLeader) { throw new RuntimeException(); }
  // no position
  private  class LeadershipStatus extends scala.Enumeration {
    public   LeadershipStatus () { throw new RuntimeException(); }
    public  scala.Enumeration.Value LEADER () { throw new RuntimeException(); }
    public  scala.Enumeration.Value NOT_LEADER () { throw new RuntimeException(); }
  }
  // not preceding
  private  org.apache.spark.deploy.master.ZooKeeperLeaderElectionAgent.LeadershipStatus$ LeadershipStatus () { throw new RuntimeException(); }
}
