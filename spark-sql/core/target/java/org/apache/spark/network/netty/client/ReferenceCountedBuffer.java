package org.apache.spark.network.netty.client;
/**
 * A buffer abstraction based on Netty's ByteBuf so we don't expose Netty.
 * This is a Scala value class.
 * <p>
 * The buffer's life cycle is NOT managed by the JVM, and thus requiring explicit declaration of
 * reference by the retain method and release method.
 */
private  class ReferenceCountedBuffer extends scala.AnyVal {
  public  io.netty.buffer.ByteBuf underlying () { throw new RuntimeException(); }
  // not preceding
  public   ReferenceCountedBuffer (io.netty.buffer.ByteBuf underlying) { throw new RuntimeException(); }
  /** Return the nio ByteBuffer view of the underlying buffer. */
  public  java.nio.ByteBuffer byteBuffer () { throw new RuntimeException(); }
  /** Creates a new input stream that starts from the current position of the buffer. */
  public  java.io.InputStream inputStream () { throw new RuntimeException(); }
  /** Increment the reference counter by one. */
  public  void retain () { throw new RuntimeException(); }
  /** Decrement the reference counter by one and release the buffer if the ref count is 0. */
  public  void release () { throw new RuntimeException(); }
}
