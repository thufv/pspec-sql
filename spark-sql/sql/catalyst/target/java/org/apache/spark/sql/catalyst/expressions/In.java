package org.apache.spark.sql.catalyst.expressions;
/**
 * Evaluates to <code>true</code> if <code>list</code> contains <code>value</code>.
 */
public  class In extends org.apache.spark.sql.catalyst.expressions.Expression implements org.apache.spark.sql.catalyst.expressions.Predicate, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.catalyst.expressions.Expression value () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> list () { throw new RuntimeException(); }
  // not preceding
  public   In (org.apache.spark.sql.catalyst.expressions.Expression value, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> list) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
