package org.apache.spark.sql.hive;
// no position
public  class HiveContext$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final HiveContext$ MODULE$ = null;
  public   HiveContext$ () { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.types.NativeType> primitiveTypes () { throw new RuntimeException(); }
  protected  java.lang.String toHiveString (scala.Tuple2<java.lang.Object, org.apache.spark.sql.types.DataType> a) { throw new RuntimeException(); }
  /** Hive outputs fields of structs slightly differently than top level attributes. */
  protected  java.lang.String toHiveStructString (scala.Tuple2<java.lang.Object, org.apache.spark.sql.types.DataType> a) { throw new RuntimeException(); }
}
