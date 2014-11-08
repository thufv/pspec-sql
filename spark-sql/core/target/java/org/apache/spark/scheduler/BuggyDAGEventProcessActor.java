package org.apache.spark.scheduler;
public  class BuggyDAGEventProcessActor implements akka.actor.Actor {
  public   BuggyDAGEventProcessActor () { throw new RuntimeException(); }
  public  int state () { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, scala.runtime.BoxedUnit> receive () { throw new RuntimeException(); }
}
