package org.apache.spark.sql.catalyst;
public  class ComplexData implements scala.Product, scala.Serializable {
  public  scala.collection.Seq<java.lang.Object> arrayField () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.Integer> arrayFieldContainsNull () { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<java.lang.Object, java.lang.Object> mapField () { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<java.lang.Object, java.lang.Long> mapFieldValueContainsNull () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.PrimitiveData structField () { throw new RuntimeException(); }
  // not preceding
  public   ComplexData (scala.collection.Seq<java.lang.Object> arrayField, scala.collection.Seq<java.lang.Integer> arrayFieldContainsNull, scala.collection.immutable.Map<java.lang.Object, java.lang.Object> mapField, scala.collection.immutable.Map<java.lang.Object, java.lang.Long> mapFieldValueContainsNull, org.apache.spark.sql.catalyst.PrimitiveData structField) { throw new RuntimeException(); }
}
