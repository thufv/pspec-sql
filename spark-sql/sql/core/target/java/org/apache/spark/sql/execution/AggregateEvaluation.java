package org.apache.spark.sql.execution;
public  class AggregateEvaluation implements scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> schema () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> initialValues () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> update () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression result () { throw new RuntimeException(); }
  // not preceding
  public   AggregateEvaluation (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> schema, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> initialValues, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> update, org.apache.spark.sql.catalyst.expressions.Expression result) { throw new RuntimeException(); }
}
