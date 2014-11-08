package org.apache.spark.serializer;
/**
 * :: DeveloperApi ::
 * A serializer. Because some serialization libraries are not thread safe, this class is used to
 * create {@link org.apache.spark.serializer.SerializerInstance} objects that do the actual
 * serialization and are guaranteed to only be called from one thread at a time.
 * <p>
 * Implementations of this trait should implement:
 * <p>
 * 1. a zero-arg constructor or a constructor that accepts a {@link org.apache.spark.SparkConf}
 * as parameter. If both constructors are defined, the latter takes precedence.
 * <p>
 * 2. Java serialization interface.
 * <p>
 * Note that serializers are not required to be wire-compatible across different versions of Spark.
 * They are intended to be used to serialize/de-serialize data within a single Spark application.
 */
public abstract class Serializer {
  static public  org.apache.spark.serializer.Serializer getSerializer (org.apache.spark.serializer.Serializer serializer) { throw new RuntimeException(); }
  static public  org.apache.spark.serializer.Serializer getSerializer (scala.Option<org.apache.spark.serializer.Serializer> serializer) { throw new RuntimeException(); }
  public   Serializer () { throw new RuntimeException(); }
  /**
   * Default ClassLoader to use in deserialization. Implementations of {@link Serializer} should
   * make sure it is using this when set.
   */
  protected  scala.Option<java.lang.ClassLoader> defaultClassLoader () { throw new RuntimeException(); }
  /**
   * Sets a class loader for the serializer to use in deserialization.
   * <p>
   * @return this Serializer object
   */
  public  org.apache.spark.serializer.Serializer setDefaultClassLoader (java.lang.ClassLoader classLoader) { throw new RuntimeException(); }
  /** Creates a new {@link SerializerInstance}. */
  public abstract  org.apache.spark.serializer.SerializerInstance newInstance () ;
}
