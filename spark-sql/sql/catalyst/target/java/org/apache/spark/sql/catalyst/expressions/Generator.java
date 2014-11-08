package org.apache.spark.sql.catalyst.expressions;
/**
 * An expression that produces zero or more rows given a single input row.
 * <p>
 * Generators produce multiple output rows instead of a single value like other expressions,
 * and thus they must have a schema to associate with the rows that are output.
 * <p>
 * However, unlike row producing relational operators, which are either leaves or determine their
 * output schema functionally from their input, generators can contain other expressions that
 * might result in their modification by rules.  This structure means that they might be copied
 * multiple times after first determining their output schema. If a new output schema is created for
 * each copy references up the tree might be rendered invalid. As a result generators must
 * instead define a function <code>makeOutput</code> which is called only once when the schema is first
 * requested.  The attributes produced by this function will be automatically copied anytime rules
 * result in changes to the Generator or its children.
 */
public abstract class Generator extends org.apache.spark.sql.catalyst.expressions.Expression {
  public   Generator () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.ArrayType dataType () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  /**
   * Should be overridden by specific generators.  Called only once for each instance to ensure
   * that rule application does not change the output schema of a generator.
   */
  protected abstract  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> makeOutput () ;
  private  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> _output () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  /** Should be implemented by child classes to perform specific Generators. */
  public abstract  scala.collection.TraversableOnce<org.apache.spark.sql.catalyst.expressions.Row> eval (org.apache.spark.sql.catalyst.expressions.Row input) ;
  /** Overridden `makeCopy` also copies the attributes that are produced by this generator. */
  public  org.apache.spark.sql.catalyst.expressions.Generator makeCopy (java.lang.Object[] newArgs) { throw new RuntimeException(); }
}
