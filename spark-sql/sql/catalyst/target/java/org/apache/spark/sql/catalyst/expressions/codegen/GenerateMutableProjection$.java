package org.apache.spark.sql.catalyst.expressions.codegen;
// no position
/**
 * Generates byte code that produces a {@link MutableRow} object that can update itself based on a new
 * input {@link Row} for a fixed set of {@link Expression Expressions}.
 */
public  class GenerateMutableProjection$ extends org.apache.spark.sql.catalyst.expressions.codegen.CodeGenerator<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, scala.Function0<org.apache.spark.sql.catalyst.expressions.MutableProjection>> {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final GenerateMutableProjection$ MODULE$ = null;
  public   GenerateMutableProjection$ () { throw new RuntimeException(); }
  public  scala.reflect.api.Names.TermName mutableRowName () { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> canonicalize (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> in) { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> bind (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> in, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
  protected  scala.Function0<org.apache.spark.sql.catalyst.expressions.MutableProjection> create (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> expressions) { throw new RuntimeException(); }
}
