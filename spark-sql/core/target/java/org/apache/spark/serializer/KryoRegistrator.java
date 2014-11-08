package org.apache.spark.serializer;
/**
 * Interface implemented by clients to register their classes with Kryo when using Kryo
 * serialization.
 */
public  interface KryoRegistrator {
  public abstract  void registerClasses (com.esotericsoftware.kryo.Kryo kryo) ;
}
