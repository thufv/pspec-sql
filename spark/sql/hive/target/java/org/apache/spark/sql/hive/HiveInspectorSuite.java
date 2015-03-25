package org.apache.spark.sql.hive;
public  class HiveInspectorSuite extends org.scalatest.FunSuite implements org.apache.spark.sql.hive.HiveInspectors {
  public   HiveInspectorSuite () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.catalyst.expressions.Literal> data () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<java.lang.Object> row () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.types.DataType> dataTypes () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector toWritableInspector (org.apache.spark.sql.types.DataType dataType) { throw new RuntimeException(); }
  public  void checkDataType (scala.collection.Seq<org.apache.spark.sql.types.DataType> dt1, scala.collection.Seq<org.apache.spark.sql.types.DataType> dt2) { throw new RuntimeException(); }
  public  void checkValues (scala.collection.Seq<java.lang.Object> row1, scala.collection.Seq<java.lang.Object> row2) { throw new RuntimeException(); }
  public  void checkValues (scala.collection.Seq<java.lang.Object> row1, org.apache.spark.sql.Row row2) { throw new RuntimeException(); }
  public  void checkValue (Object v1, Object v2) { throw new RuntimeException(); }
}
