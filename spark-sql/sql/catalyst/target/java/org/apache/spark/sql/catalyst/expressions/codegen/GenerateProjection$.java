package org.apache.spark.sql.catalyst.expressions.codegen;
// no position
/**
 * Generates bytecode that produces a new {@link Row} object based on a fixed set of input
 * {@link Expression Expressions} and a given input {@link Row}.  The returned {@link Row} object is custom
 * generated based on the output types of the {@link Expression} to avoid boxing of primitive values.
 */
public  class GenerateProjection$ extends org.apache.spark.sql.catalyst.expressions.codegen.CodeGenerator<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, org.apache.spark.sql.catalyst.expressions.Projection> {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final GenerateProjection$ MODULE$ = null;
  public   GenerateProjection$ () { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> canonicalize (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> in) { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> bind (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> in, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.expressions.Projection create (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions) { throw new RuntimeException(); }
}
