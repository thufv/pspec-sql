package org.apache.spark.sql.catalyst.analysis;
public  class SimpleFunctionRegistry implements org.apache.spark.sql.catalyst.analysis.FunctionRegistry {
  public   SimpleFunctionRegistry () { throw new RuntimeException(); }
  public  scala.collection.mutable.HashMap<java.lang.String, scala.Function1<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, org.apache.spark.sql.catalyst.expressions.Expression>> functionBuilders () { throw new RuntimeException(); }
  public  void registerFunction (java.lang.String name, scala.Function1<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, org.apache.spark.sql.catalyst.expressions.Expression> builder) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression lookupFunction (java.lang.String name, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children) { throw new RuntimeException(); }
}
