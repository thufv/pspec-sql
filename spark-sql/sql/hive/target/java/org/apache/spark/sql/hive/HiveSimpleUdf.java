package org.apache.spark.sql.hive;
private  class HiveSimpleUdf extends org.apache.spark.sql.hive.HiveUdf implements org.apache.spark.sql.hive.HiveInspectors, scala.Product, scala.Serializable {
  public  java.lang.String functionClassName () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  // not preceding
  public   HiveSimpleUdf (java.lang.String functionClassName, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children) { throw new RuntimeException(); }
  protected  java.lang.reflect.Method method () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  protected  scala.Function1<java.lang.Object, java.lang.Object>[] wrappers () { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
