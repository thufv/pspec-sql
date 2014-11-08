package org.apache.spark.rdd;
/** a sampler that outputs its seed */
public  class MockSampler implements org.apache.spark.util.random.RandomSampler<java.lang.Object, java.lang.Object> {
  public   MockSampler () { throw new RuntimeException(); }
  private  long s () { throw new RuntimeException(); }
  public  void setSeed (long seed) { throw new RuntimeException(); }
  public  scala.collection.Iterator<java.lang.Object> sample (scala.collection.Iterator<java.lang.Object> items) { throw new RuntimeException(); }
  public  org.apache.spark.rdd.MockSampler clone () { throw new RuntimeException(); }
}
