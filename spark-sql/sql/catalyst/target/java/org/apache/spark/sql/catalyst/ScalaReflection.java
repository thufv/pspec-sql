package org.apache.spark.sql.catalyst;
// no position
/**
 * Provides experimental support for generating catalyst schemas for scala objects.
 */
public  class ScalaReflection {
  static public  class Schema implements scala.Product, scala.Serializable {
    public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
    public  boolean nullable () { throw new RuntimeException(); }
    // not preceding
    public   Schema (org.apache.spark.sql.catalyst.types.DataType dataType, boolean nullable) { throw new RuntimeException(); }
  }
  // no position
  static public  class Schema$ extends scala.runtime.AbstractFunction2<org.apache.spark.sql.catalyst.types.DataType, java.lang.Object, org.apache.spark.sql.catalyst.ScalaReflection.Schema> implements scala.Serializable {
    public   Schema$ () { throw new RuntimeException(); }
  }
  static public  class CaseClassRelation<A extends scala.Product> {
    // not preceding
    // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Ident(scala), scala.Product))))
    public   CaseClassRelation (scala.collection.Seq<A> data, scala.reflect.api.TypeTags.TypeTag<A> evidence$3) { throw new RuntimeException(); }
    /**
     * Implicitly added to Sequences of case class objects.  Returns a catalyst logical relation
     * for the the data in the sequence.
     */
    public  org.apache.spark.sql.catalyst.plans.logical.LocalRelation asRelation () { throw new RuntimeException(); }
  }
  /** Converts Scala objects to catalyst rows / types */
  static public  Object convertToCatalyst (Object a) { throw new RuntimeException(); }
  /** Returns a Sequence of attributes for the given case class type. */
  static public <T extends java.lang.Object> scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributesFor (scala.reflect.api.TypeTags.TypeTag<T> evidence$1) { throw new RuntimeException(); }
  /** Returns a catalyst DataType and its nullability for the given Scala Type using reflection. */
  static public <T extends java.lang.Object> org.apache.spark.sql.catalyst.ScalaReflection.Schema schemaFor (scala.reflect.api.TypeTags.TypeTag<T> evidence$2) { throw new RuntimeException(); }
  /** Returns a catalyst DataType and its nullability for the given Scala Type using reflection. */
  static public  org.apache.spark.sql.catalyst.ScalaReflection.Schema schemaFor (scala.reflect.api.Types.Type tpe) { throw new RuntimeException(); }
  static public  scala.PartialFunction<java.lang.Object, org.apache.spark.sql.catalyst.types.DataType> typeOfObject () { throw new RuntimeException(); }
}
