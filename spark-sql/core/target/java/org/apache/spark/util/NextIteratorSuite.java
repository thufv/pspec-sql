package org.apache.spark.util;
public  class NextIteratorSuite extends org.scalatest.FunSuite implements org.scalatest.Matchers {
  public   NextIteratorSuite () { throw new RuntimeException(); }
  public  class StubIterator extends org.apache.spark.util.NextIterator<java.lang.Object> {
    public   StubIterator (scala.collection.mutable.Buffer<java.lang.Object> ints) { throw new RuntimeException(); }
    public  int closeCalled () { throw new RuntimeException(); }
    public  int getNext () { throw new RuntimeException(); }
    public  void close () { throw new RuntimeException(); }
  }
}
