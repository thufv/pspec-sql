package org.apache.spark.sql.catalyst.analysis;
public abstract interface OverrideFunctionRegistry extends org.apache.spark.sql.catalyst.analysis.FunctionRegistry {
  public  scala.collection.mutable.HashMap<java.lang.String, scala.Function1<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, org.apache.spark.sql.catalyst.expressions.Expression>> functionBuilders () ;
  public  void registerFunction (java.lang.String name, scala.Function1<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, org.apache.spark.sql.catalyst.expressions.Expression> builder) ;
  public  org.apache.spark.sql.catalyst.expressions.Expression lookupFunction (java.lang.String name, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children) ;
}
