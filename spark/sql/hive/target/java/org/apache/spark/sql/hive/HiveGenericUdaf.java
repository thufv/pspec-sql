package org.apache.spark.sql.hive;
public  class HiveGenericUdaf extends org.apache.spark.sql.catalyst.expressions.AggregateExpression implements org.apache.spark.sql.hive.HiveInspectors, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.hive.HiveFunctionWrapper funcWrapper () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  // not preceding
  public   HiveGenericUdaf (org.apache.spark.sql.hive.HiveFunctionWrapper funcWrapper, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children) { throw new RuntimeException(); }
  protected  org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver resolver () { throw new RuntimeException(); }
  protected  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector objectInspector () { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector> inspectors () { throw new RuntimeException(); }
  public  org.apache.spark.sql.types.DataType dataType () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  org.apache.spark.sql.hive.HiveUdafFunction newInstance () { throw new RuntimeException(); }
}
