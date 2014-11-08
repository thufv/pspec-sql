package org.apache.spark;
/**
 * :: DeveloperApi ::
 * An iterator that wraps around an existing iterator to provide task killing functionality.
 * It works by checking the interrupted flag in {@link TaskContext}.
 */
public  class InterruptibleIterator<T extends java.lang.Object> implements scala.collection.Iterator<T> {
  public  org.apache.spark.TaskContext context () { throw new RuntimeException(); }
  public  scala.collection.Iterator<T> delegate () { throw new RuntimeException(); }
  // not preceding
  public   InterruptibleIterator (org.apache.spark.TaskContext context, scala.collection.Iterator<T> delegate) { throw new RuntimeException(); }
  public  boolean hasNext () { throw new RuntimeException(); }
  public  T next () { throw new RuntimeException(); }
}
