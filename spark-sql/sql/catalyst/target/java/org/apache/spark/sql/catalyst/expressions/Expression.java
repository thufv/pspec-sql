package org.apache.spark.sql.catalyst.expressions;
public abstract class Expression extends org.apache.spark.sql.catalyst.trees.TreeNode<org.apache.spark.sql.catalyst.expressions.Expression> {
  public   Expression () { throw new RuntimeException(); }
  /**
   * Returns true when an expression is a candidate for static evaluation before the query is
   * executed.
   * <p>
   * The following conditions are used to determine suitability for constant folding:
   *  - A {@link Coalesce} is foldable if all of its children are foldable
   *  - A {@link BinaryExpression} is foldable if its both left and right child are foldable
   *  - A {@link Not}, {@link IsNull}, or {@link IsNotNull} is foldable if its child is foldable
   *  - A {@link Literal} is foldable
   *  - A {@link Cast} or {@link UnaryMinus} is foldable if its child is foldable
   */
  public  boolean foldable () { throw new RuntimeException(); }
  public abstract  boolean nullable () ;
  public  org.apache.spark.sql.catalyst.expressions.AttributeSet references () { throw new RuntimeException(); }
  /** Returns the result of evaluating this expression on a given input Row */
  public abstract  org.apache.spark.sql.catalyst.expressions.Expression.EvaluatedType eval (org.apache.spark.sql.catalyst.expressions.Row input) ;
  /**
   * Returns <code>true</code> if this expression and all its children have been resolved to a specific schema
   * and <code>false</code> if it still contains any unresolved placeholders. Implementations of expressions
   * should override this if the resolution of this type of expression involves more than just
   * the resolution of its children.
   */
  public  boolean resolved () { throw new RuntimeException(); }
  /**
   * Returns the {@link DataType} of the result of evaluating this expression.  It is
   * invalid to query the dataType of an unresolved expression (i.e., when <code>resolved</code> == false).
   */
  public abstract  org.apache.spark.sql.catalyst.types.DataType dataType () ;
  /**
   * Returns true if  all the children of this expression have been resolved to a specific schema
   * and false if any still contains any unresolved placeholders.
   */
  public  boolean childrenResolved () { throw new RuntimeException(); }
  /**
   * A set of helper functions that return the correct descendant of <code>scala.math.Numeric[T]</code> type
   * and do any casting necessary of child evaluation.
   */
  public  Object n1 (org.apache.spark.sql.catalyst.expressions.Expression e, org.apache.spark.sql.catalyst.expressions.Row i, scala.Function2<scala.math.Numeric<java.lang.Object>, java.lang.Object, java.lang.Object> f) { throw new RuntimeException(); }
  /**
   * Evaluation helper function for 2 Numeric children expressions. Those expressions are supposed
   * to be in the same data type, and also the return type.
   * Either one of the expressions result is null, the evaluation result should be null.
   */
  protected final  Object n2 (org.apache.spark.sql.catalyst.expressions.Row i, org.apache.spark.sql.catalyst.expressions.Expression e1, org.apache.spark.sql.catalyst.expressions.Expression e2, scala.Function3<scala.math.Numeric<java.lang.Object>, java.lang.Object, java.lang.Object, java.lang.Object> f) { throw new RuntimeException(); }
  /**
   * Evaluation helper function for 2 Fractional children expressions. Those expressions are
   * supposed to be in the same data type, and also the return type.
   * Either one of the expressions result is null, the evaluation result should be null.
   */
  protected final  Object f2 (org.apache.spark.sql.catalyst.expressions.Row i, org.apache.spark.sql.catalyst.expressions.Expression e1, org.apache.spark.sql.catalyst.expressions.Expression e2, scala.Function3<scala.math.Fractional<java.lang.Object>, java.lang.Object, java.lang.Object, java.lang.Object> f) { throw new RuntimeException(); }
  /**
   * Evaluation helper function for 2 Integral children expressions. Those expressions are
   * supposed to be in the same data type, and also the return type.
   * Either one of the expressions result is null, the evaluation result should be null.
   */
  protected final  Object i2 (org.apache.spark.sql.catalyst.expressions.Row i, org.apache.spark.sql.catalyst.expressions.Expression e1, org.apache.spark.sql.catalyst.expressions.Expression e2, scala.Function3<scala.math.Integral<java.lang.Object>, java.lang.Object, java.lang.Object, java.lang.Object> f) { throw new RuntimeException(); }
  /**
   * Evaluation helper function for 2 Comparable children expressions. Those expressions are
   * supposed to be in the same data type, and the return type should be Integer:
   * Negative value: 1st argument less than 2nd argument
   * Zero:  1st argument equals 2nd argument
   * Positive value: 1st argument greater than 2nd argument
   * <p>
   * Either one of the expressions result is null, the evaluation result should be null.
   */
  protected final  Object c2 (org.apache.spark.sql.catalyst.expressions.Row i, org.apache.spark.sql.catalyst.expressions.Expression e1, org.apache.spark.sql.catalyst.expressions.Expression e2, scala.Function3<scala.math.Ordering<java.lang.Object>, java.lang.Object, java.lang.Object, java.lang.Object> f) { throw new RuntimeException(); }
}
