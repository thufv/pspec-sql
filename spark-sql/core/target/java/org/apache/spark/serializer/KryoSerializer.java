package org.apache.spark.serializer;
/**
 * A Spark serializer that uses the {@link https://code.google.com/p/kryo/ Kryo serialization library}.
 * <p>
 * Note that this serializer is not guaranteed to be wire-compatible across different versions of
 * Spark. It is intended to be used to serialize/de-serialize data within a single
 * Spark application.
 */
public  class KryoSerializer extends org.apache.spark.serializer.Serializer implements org.apache.spark.Logging, scala.Serializable {
  static private  scala.collection.Seq<java.lang.Class<?>> toRegister () { throw new RuntimeException(); }
  public   KryoSerializer (org.apache.spark.SparkConf conf) { throw new RuntimeException(); }
  private  int bufferSize () { throw new RuntimeException(); }
  private  int maxBufferSize () { throw new RuntimeException(); }
  private  boolean referenceTracking () { throw new RuntimeException(); }
  private  boolean registrationRequired () { throw new RuntimeException(); }
  private  scala.Option<java.lang.String> registrator () { throw new RuntimeException(); }
  public  com.esotericsoftware.kryo.io.Output newKryoOutput () { throw new RuntimeException(); }
  public  com.esotericsoftware.kryo.Kryo newKryo () { throw new RuntimeException(); }
  public  org.apache.spark.serializer.SerializerInstance newInstance () { throw new RuntimeException(); }
}
