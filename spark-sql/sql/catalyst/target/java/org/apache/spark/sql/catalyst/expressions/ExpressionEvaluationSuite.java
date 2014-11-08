package org.apache.spark.sql.catalyst.expressions;
public  class ExpressionEvaluationSuite extends org.scalatest.FunSuite {
  public   ExpressionEvaluationSuite () { throw new RuntimeException(); }
  /**
   * Checks for three-valued-logic.  Based on:
   * http://en.wikipedia.org/wiki/Null_(SQL)#Comparisons_with_NULL_and_the_three-valued_logic_.283VL.29
   * I.e. in flat cpo "False -> Unknown -> True", OR is lowest upper bound, AND is greatest lower bound.
   * p       q       p OR q  p AND q  p = q
   * True    True    True    True     True
   * True    False   True    False    False
   * True    Unknown True    Unknown  Unknown
   * False   True    True    False    False
   * False   False   False   False    True
   * False   Unknown Unknown False    Unknown
   * Unknown True    True    Unknown  Unknown
   * Unknown False   Unknown False    Unknown
   * Unknown Unknown Unknown Unknown  Unknown
   * <p>
   * p       NOT p
   * True    False
   * False   True
   * Unknown Unknown
   */
  public  scala.collection.immutable.List<scala.Tuple2<java.lang.Object, java.lang.Object>> notTrueTable () { throw new RuntimeException(); }
  public  void booleanLogicTest (java.lang.String name, scala.Function2<org.apache.spark.sql.catalyst.expressions.Expression, org.apache.spark.sql.catalyst.expressions.Expression, org.apache.spark.sql.catalyst.expressions.Expression> op, scala.collection.Seq<scala.Tuple3<java.lang.Object, java.lang.Object, java.lang.Object>> truthTable) { throw new RuntimeException(); }
  public  Object evaluate (org.apache.spark.sql.catalyst.expressions.Expression expression, org.apache.spark.sql.catalyst.expressions.Row inputRow) { throw new RuntimeException(); }
  public  void checkEvaluation (org.apache.spark.sql.catalyst.expressions.Expression expression, Object expected, org.apache.spark.sql.catalyst.expressions.Row inputRow) { throw new RuntimeException(); }
}
