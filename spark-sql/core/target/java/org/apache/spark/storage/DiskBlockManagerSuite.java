package org.apache.spark.storage;
public  class DiskBlockManagerSuite extends org.scalatest.FunSuite implements org.scalatest.BeforeAndAfterEach, org.scalatest.BeforeAndAfterAll {
  public   DiskBlockManagerSuite () { throw new RuntimeException(); }
  private  org.apache.spark.SparkConf testConf () { throw new RuntimeException(); }
  private  java.io.File rootDir0 () { throw new RuntimeException(); }
  private  java.io.File rootDir1 () { throw new RuntimeException(); }
  private  java.lang.String rootDirs () { throw new RuntimeException(); }
  private  org.apache.spark.shuffle.hash.HashShuffleManager shuffleManager () { throw new RuntimeException(); }
  public  org.apache.spark.storage.ShuffleBlockManager shuffleBlockManager () { throw new RuntimeException(); }
  public  org.apache.spark.storage.DiskBlockManager diskBlockManager () { throw new RuntimeException(); }
  public  void beforeAll () { throw new RuntimeException(); }
  public  void afterAll () { throw new RuntimeException(); }
  public  void beforeEach () { throw new RuntimeException(); }
  public  void afterEach () { throw new RuntimeException(); }
  private  void checkSegments (org.apache.spark.storage.FileSegment segment1, org.apache.spark.storage.FileSegment segment2) { throw new RuntimeException(); }
  public  void assertSegmentEquals (org.apache.spark.storage.BlockId blockId, java.lang.String filename, int offset, int length) { throw new RuntimeException(); }
  public  void writeToFile (java.io.File file, int numBytes) { throw new RuntimeException(); }
}
