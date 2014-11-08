package org.apache.spark.sql.execution;
public abstract interface HashJoin {
  public abstract  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> leftKeys () ;
  public abstract  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> rightKeys () ;
  public abstract  org.apache.spark.sql.execution.BuildSide buildSide () ;
  public abstract  org.apache.spark.sql.execution.SparkPlan left () ;
  public abstract  org.apache.spark.sql.execution.SparkPlan right () ;
  // not preceding
  public  org.apache.spark.sql.execution.SparkPlan buildPlan () ;
  public  org.apache.spark.sql.execution.SparkPlan streamedPlan () ;
  // not preceding
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> buildKeys () ;
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> streamedKeys () ;
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () ;
  public  org.apache.spark.sql.catalyst.expressions.Projection buildSideKeyGenerator () ;
  public  scala.Function0<org.apache.spark.sql.catalyst.expressions.MutableProjection> streamSideKeyGenerator () ;
  public  scala.collection.Iterator<org.apache.spark.sql.catalyst.expressions.Row> joinIterators (scala.collection.Iterator<org.apache.spark.sql.catalyst.expressions.Row> buildIter, scala.collection.Iterator<org.apache.spark.sql.catalyst.expressions.Row> streamIter) ;
}
