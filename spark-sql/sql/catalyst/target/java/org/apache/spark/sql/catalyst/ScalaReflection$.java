package org.apache.spark.sql.catalyst;
// no position
/**
 * Provides experimental support for generating catalyst schemas for scala objects.
 */
public  class ScalaReflection$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ScalaReflection$ MODULE$ = null;
  public   ScalaReflection$ () { throw new RuntimeException(); }
  /** Converts Scala objects to catalyst rows / types */
  public  Object convertToCatalyst (Object a) { throw new RuntimeException(); }
  /** Returns a Sequence of attributes for the given case class type. */
  public <T extends java.lang.Object> scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributesFor (scala.reflect.api.TypeTags.TypeTag<T> evidence$1) { throw new RuntimeException(); }
  /** Returns a catalyst DataType and its nullability for the given Scala Type using reflection. */
  public <T extends java.lang.Object> org.apache.spark.sql.catalyst.ScalaReflection.Schema schemaFor (scala.reflect.api.TypeTags.TypeTag<T> evidence$2) { throw new RuntimeException(); }
  /** Returns a catalyst DataType and its nullability for the given Scala Type using reflection. */
  public  org.apache.spark.sql.catalyst.ScalaReflection.Schema schemaFor (scala.reflect.api.Types.Type tpe) { throw new RuntimeException(); }
  public  scala.PartialFunction<java.lang.Object, org.apache.spark.sql.catalyst.types.DataType> typeOfObject () { throw new RuntimeException(); }
}
