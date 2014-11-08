package org.apache.spark.sql.catalyst.types;
public abstract class NativeType extends org.apache.spark.sql.catalyst.types.DataType {
  static public  scala.collection.Seq<org.apache.spark.sql.catalyst.types.PrimitiveType> all () { throw new RuntimeException(); }
  static public  boolean unapply (org.apache.spark.sql.catalyst.types.DataType dt) { throw new RuntimeException(); }
  public   NativeType () { throw new RuntimeException(); }
  public abstract  scala.reflect.api.TypeTags.TypeTag<java.lang.Object> tag () ;
  public abstract  scala.math.Ordering<java.lang.Object> ordering () ;
  public  scala.reflect.ClassTag<java.lang.Object> classTag () { throw new RuntimeException(); }
}
