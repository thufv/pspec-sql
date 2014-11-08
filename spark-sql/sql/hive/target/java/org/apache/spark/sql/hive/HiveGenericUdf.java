package org.apache.spark.sql.hive;
private  class HiveGenericUdf extends org.apache.spark.sql.hive.HiveUdf implements org.apache.spark.sql.hive.HiveInspectors, scala.Product, scala.Serializable {
  public  class DeferredObjectAdapter implements org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject {
    public   DeferredObjectAdapter () { throw new RuntimeException(); }
    private  scala.Function0<java.lang.Object> func () { throw new RuntimeException(); }
    public  void set (scala.Function0<java.lang.Object> func) { throw new RuntimeException(); }
    public  void prepare (int i) { throw new RuntimeException(); }
    public  java.lang.Object get () { throw new RuntimeException(); }
  }
  public  java.lang.String functionClassName () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  // not preceding
  public   HiveGenericUdf (java.lang.String functionClassName, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children) { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector> argumentInspectors () { throw new RuntimeException(); }
  protected  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector returnInspector () { throw new RuntimeException(); }
  protected  boolean isUDFDeterministic () { throw new RuntimeException(); }
  public  boolean foldable () { throw new RuntimeException(); }
  protected  org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject[] deferedObjects () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
