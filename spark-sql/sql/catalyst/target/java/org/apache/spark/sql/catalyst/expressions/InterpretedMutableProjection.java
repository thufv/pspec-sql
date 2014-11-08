package org.apache.spark.sql.catalyst.expressions;
/**
 * A {@link MutableProjection} that is calculated by calling <code>eval</code> on each of the specified
 * expressions.
 * @param expressions a sequence of expressions that determine the value of each column of the
 *                    output row.
 */
public  class InterpretedMutableProjection extends org.apache.spark.sql.catalyst.expressions.MutableProjection implements scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions () { throw new RuntimeException(); }
  // not preceding
  public   InterpretedMutableProjection (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions) { throw new RuntimeException(); }
  public   InterpretedMutableProjection (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Row currentValue () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.MutableProjection target (org.apache.spark.sql.catalyst.expressions.MutableRow row) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Row apply (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
