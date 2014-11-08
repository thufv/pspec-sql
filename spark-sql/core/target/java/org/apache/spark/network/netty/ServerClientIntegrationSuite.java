package org.apache.spark.network.netty;
/**
 * Test suite that makes sure the server and the client implementations share the same protocol.
 */
public  class ServerClientIntegrationSuite extends org.scalatest.FunSuite implements org.scalatest.BeforeAndAfterAll {
  public   ServerClientIntegrationSuite () { throw new RuntimeException(); }
  public  int bufSize () { throw new RuntimeException(); }
  public  java.nio.ByteBuffer buf () { throw new RuntimeException(); }
  public  java.io.File testFile () { throw new RuntimeException(); }
  public  org.apache.spark.network.netty.server.BlockServer server () { throw new RuntimeException(); }
  public  org.apache.spark.network.netty.client.BlockFetchingClientFactory clientFactory () { throw new RuntimeException(); }
  public  java.lang.String bufferBlockId () { throw new RuntimeException(); }
  public  java.lang.String fileBlockId () { throw new RuntimeException(); }
  public  byte[] fileContent () { throw new RuntimeException(); }
  public  void beforeAll () { throw new RuntimeException(); }
  public  void afterAll () { throw new RuntimeException(); }
  /** A ByteBuf for buffer_block */
  public  io.netty.buffer.ByteBuf byteBufferBlockReference () { throw new RuntimeException(); }
  /** A ByteBuf for file_block */
  public  io.netty.buffer.ByteBuf fileBlockReference () { throw new RuntimeException(); }
  public  scala.Tuple3<scala.collection.immutable.Set<java.lang.String>, scala.collection.immutable.Set<org.apache.spark.network.netty.client.ReferenceCountedBuffer>, scala.collection.immutable.Set<java.lang.String>> fetchBlocks (scala.collection.Seq<java.lang.String> blockIds) { throw new RuntimeException(); }
}
