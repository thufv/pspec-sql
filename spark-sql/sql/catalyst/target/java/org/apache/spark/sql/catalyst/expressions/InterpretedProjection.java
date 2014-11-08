package org.apache.spark.sql.catalyst.expressions;
/**
 * A {@link Projection} that is calculated by calling the <code>eval</code> of each of the specified expressions.
 * @param expressions a sequence of expressions that determine the value of each column of the
 *                    output row.
 */
public  class InterpretedProjection extends org.apache.spark.sql.catalyst.expressions.Projection {
  public   InterpretedProjection (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions) { throw new RuntimeException(); }
  public   InterpretedProjection (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.expressions.Expression[] exprArray () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Row apply (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
