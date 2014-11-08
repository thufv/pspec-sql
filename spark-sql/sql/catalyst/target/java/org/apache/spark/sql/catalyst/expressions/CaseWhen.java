package org.apache.spark.sql.catalyst.expressions;
/**
 * Case statements of the form "CASE WHEN a THEN b [WHEN c THEN d]* [ELSE e] END".
 * Refer to this link for the corresponding semantics:
 * https://cwiki.apache.org/confluence/display/Hive/LanguageManual+UDF#LanguageManualUDF-ConditionalFunctions
 * <p>
 * The other form of case statements "CASE a WHEN b THEN c [WHEN d THEN e]* [ELSE f] END" gets
 * translated to this form at parsing time.  Namely, such a statement gets translated to
 * "CASE WHEN a=b THEN c [WHEN a=d THEN e]* [ELSE f] END".
 * <p>
 * Note that <code>branches</code> are considered in consecutive pairs (cond, val), and the optional last
 * element is the value for the default catch-all case (if provided). Hence, <code>branches</code> consists of
 * at least two elements, and can have an odd or even length.
 */
public  class CaseWhen extends org.apache.spark.sql.catalyst.expressions.Expression implements scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> branches () { throw new RuntimeException(); }
  // not preceding
  public   CaseWhen (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> branches) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.expressions.Expression[] branchesArr () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> predicates () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> values () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.sql.catalyst.expressions.Expression> elseValue () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  boolean resolved () { throw new RuntimeException(); }
  /** Written in imperative fashion for performance considerations.  Same for CaseKeyWhen. */
  public  Object eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
