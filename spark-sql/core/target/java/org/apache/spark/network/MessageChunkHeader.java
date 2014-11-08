package org.apache.spark.network;
private  class MessageChunkHeader {
  static public  int HEADER_SIZE () { throw new RuntimeException(); }
  static public  org.apache.spark.network.MessageChunkHeader create (java.nio.ByteBuffer buffer) { throw new RuntimeException(); }
  public  long typ () { throw new RuntimeException(); }
  public  int id () { throw new RuntimeException(); }
  public  int totalSize () { throw new RuntimeException(); }
  public  int chunkSize () { throw new RuntimeException(); }
  public  int other () { throw new RuntimeException(); }
  public  boolean hasError () { throw new RuntimeException(); }
  public  int securityNeg () { throw new RuntimeException(); }
  public  java.net.InetSocketAddress address () { throw new RuntimeException(); }
  // not preceding
  public   MessageChunkHeader (long typ, int id, int totalSize, int chunkSize, int other, boolean hasError, int securityNeg, java.net.InetSocketAddress address) { throw new RuntimeException(); }
  public  java.nio.ByteBuffer buffer () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
