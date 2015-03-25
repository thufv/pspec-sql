package org.apache.spark.sql.hive;
public  class DeferredObjectAdapter implements org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject, org.apache.spark.sql.hive.HiveInspectors {
  public   DeferredObjectAdapter (org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector oi) { throw new RuntimeException(); }
  private  scala.Function0<java.lang.Object> func () { throw new RuntimeException(); }
  public  void set (scala.Function0<java.lang.Object> func) { throw new RuntimeException(); }
  public  void prepare (int i) { throw new RuntimeException(); }
  public  java.lang.Object get () { throw new RuntimeException(); }
}
