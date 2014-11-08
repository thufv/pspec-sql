package org.apache.spark.network.netty.client;
/**
 * A simple iterator that lazily initializes the underlying iterator.
 * <p>
 * The use case is that sometimes we might have many iterators open at the same time, and each of
 * the iterator might initialize its own buffer (e.g. decompression buffer, deserialization buffer).
 * This could lead to too many buffers open. If this iterator is used, we lazily initialize those
 * buffers.
 */
private  class LazyInitIterator implements scala.collection.Iterator<java.lang.Object> {
  public   LazyInitIterator (scala.Function0<scala.collection.Iterator<java.lang.Object>> createIterator) { throw new RuntimeException(); }
  public  scala.collection.Iterator<java.lang.Object> proxy () { throw new RuntimeException(); }
  public  boolean hasNext () { throw new RuntimeException(); }
  public  Object next () { throw new RuntimeException(); }
  public  void close () { throw new RuntimeException(); }
}
