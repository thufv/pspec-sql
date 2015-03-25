package org.apache.spark.sql.hive;
public abstract class HiveFunctionRegistry implements org.apache.spark.sql.catalyst.analysis.FunctionRegistry, org.apache.spark.sql.hive.HiveInspectors {
  public   HiveFunctionRegistry () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.ql.exec.FunctionInfo getFunctionInfo (java.lang.String name) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression lookupFunction (java.lang.String name, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children) { throw new RuntimeException(); }
}
