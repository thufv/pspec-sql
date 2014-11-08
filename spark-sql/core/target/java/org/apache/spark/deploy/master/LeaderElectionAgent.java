package org.apache.spark.deploy.master;
/**
 * A LeaderElectionAgent keeps track of whether the current Master is the leader, meaning it
 * is the only Master serving requests.
 * In addition to the API provided, the LeaderElectionAgent will use of the following messages
 * to inform the Master of leader changes:
 * {@link org.apache.spark.deploy.master.MasterMessages.ElectedLeader ElectedLeader}
 * {@link org.apache.spark.deploy.master.MasterMessages.RevokedLeadership RevokedLeadership}
 */
private  interface LeaderElectionAgent extends akka.actor.Actor {
  public abstract  akka.actor.ActorRef masterActor () ;
}
