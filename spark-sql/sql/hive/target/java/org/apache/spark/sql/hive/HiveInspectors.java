package org.apache.spark.sql.hive;
private abstract interface HiveInspectors {
  public  org.apache.spark.sql.catalyst.types.DataType javaClassToDataType (java.lang.Class<?> clz) ;
  /** Converts hive types to native catalyst types. */
  public  Object unwrap (Object a) ;
  public  Object unwrapData (Object data, org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector oi) ;
  /** Converts native catalyst types to the types expected by Hive */
  public  java.lang.Object wrap (Object a) ;
  public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector toInspector (org.apache.spark.sql.catalyst.types.DataType dataType) ;
  public  org.apache.spark.sql.catalyst.types.DataType inspectorToDataType (org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector inspector) ;
  public  class typeInfoConversions {
    public   typeInfoConversions (org.apache.spark.sql.catalyst.types.DataType dt) { throw new RuntimeException(); }
    public  org.apache.hadoop.hive.serde2.typeinfo.TypeInfo toTypeInfo () { throw new RuntimeException(); }
  }
}
