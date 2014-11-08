package org.apache.spark.deploy.master;
/** Single-node implementation of LeaderElectionAgent -- we're initially and always the leader. */
private  class MonarchyLeaderAgent implements org.apache.spark.deploy.master.LeaderElectionAgent {
  public  akka.actor.ActorRef masterActor () { throw new RuntimeException(); }
  // not preceding
  public   MonarchyLeaderAgent (akka.actor.ActorRef masterActor) { throw new RuntimeException(); }
  public  void preStart () { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receive () { throw new RuntimeException(); }
}
