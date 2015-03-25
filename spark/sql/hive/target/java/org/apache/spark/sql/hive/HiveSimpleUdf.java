package org.apache.spark.sql.hive;
public  class HiveSimpleUdf extends org.apache.spark.sql.catalyst.expressions.Expression implements org.apache.spark.sql.hive.HiveInspectors, org.apache.spark.Logging, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.hive.HiveFunctionWrapper funcWrapper () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  // not preceding
  public   HiveSimpleUdf (org.apache.spark.sql.hive.HiveFunctionWrapper funcWrapper, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children) { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.ql.exec.UDF function () { throw new RuntimeException(); }
  protected  java.lang.reflect.Method method () { throw new RuntimeException(); }
  protected  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector[] arguments () { throw new RuntimeException(); }
  protected  boolean isUDFDeterministic () { throw new RuntimeException(); }
  public  boolean foldable () { throw new RuntimeException(); }
  protected  org.apache.hadoop.hive.ql.udf.generic.GenericUDFUtils.ConversionHelper conversionHelper () { throw new RuntimeException(); }
  public  org.apache.spark.sql.types.DataType dataType () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector returnInspector () { throw new RuntimeException(); }
  protected  java.lang.Object[] cached () { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.Row input) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
