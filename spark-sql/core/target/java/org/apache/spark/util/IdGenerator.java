package org.apache.spark.util;
/**
 * A util used to get a unique generation ID. This is a wrapper around Java's
 * AtomicInteger. An example usage is in BlockManager, where each BlockManager
 * instance would start an Akka actor and we use this utility to assign the Akka
 * actors unique names.
 */
private  class IdGenerator {
  public   IdGenerator () { throw new RuntimeException(); }
  private  java.util.concurrent.atomic.AtomicInteger id () { throw new RuntimeException(); }
  public  int next () { throw new RuntimeException(); }
}
