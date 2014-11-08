package org.apache.spark.sql.hive;
private  class HiveUdafFunction extends org.apache.spark.sql.catalyst.expressions.AggregateFunction implements org.apache.spark.sql.hive.HiveInspectors, org.apache.spark.sql.hive.HiveFunctionFactory, scala.Product, scala.Serializable {
  public  java.lang.String functionClassName () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> exprs () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.AggregateExpression base () { throw new RuntimeException(); }
  // not preceding
  public   HiveUdafFunction (java.lang.String functionClassName, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> exprs, org.apache.spark.sql.catalyst.expressions.AggregateExpression base) { throw new RuntimeException(); }
  public   HiveUdafFunction () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver resolver () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector[] inspectors () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator function () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector returnInspector () { throw new RuntimeException(); }
  private  org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator.AbstractAggregationBuffer buffer () { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.InterpretedProjection inputProjection () { throw new RuntimeException(); }
  public  void update (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
