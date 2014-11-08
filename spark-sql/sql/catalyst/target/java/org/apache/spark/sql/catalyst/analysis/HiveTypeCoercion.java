package org.apache.spark.sql.catalyst.analysis;
/**
 * A collection of {@link Rule Rules} that can be used to coerce differing types that
 * participate in operations into compatible ones.  Most of these rules are based on Hive semantics,
 * but they do not introduce any dependencies on the hive codebase.  For this reason they remain in
 * Catalyst until we have a more standard set of coercions.
 */
public abstract interface HiveTypeCoercion {
  // no position
  /**
   * Applies any changes to {@link AttributeReference} data types that are made by other rules to
   * instances higher in the query tree.
   */
  public  class PropagateTypes$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    public   PropagateTypes$ () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // no position
  /**
   * Converts string "NaN"s that are in binary operators with a NaN-able types (Float / Double) to
   * the appropriate numeric equivalent.
   */
  public  class ConvertNaNs$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    public   ConvertNaNs$ () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.expressions.Literal stringNaN () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // no position
  /**
   * Widens numeric types and converts strings to numbers when appropriate.
   * <p>
   * Loosely based on rules from "Hadoop: The Definitive Guide" 2nd edition, by Tom White
   * <p>
   * The implicit conversion rules can be summarized as follows:
   *   - Any integral numeric type can be implicitly converted to a wider type.
   *   - All the integral numeric types, FLOAT, and (perhaps surprisingly) STRING can be implicitly
   *     converted to DOUBLE.
   *   - TINYINT, SMALLINT, and INT can all be converted to FLOAT.
   *   - BOOLEAN types cannot be converted to any other type.
   * <p>
   * Additionally, all types when UNION-ed with strings will be promoted to strings.
   * Other string conversions are handled by PromoteStrings.
   * <p>
   * Widening types might result in loss of precision in the following cases:
   * - IntegerType to FloatType
   * - LongType to FloatType
   * - LongType to DoubleType
   */
  public  class WidenTypes$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> implements org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.TypeWidening {
    public   WidenTypes$ () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // no position
  /**
   * Promotes strings that appear in arithmetic expressions.
   */
  public  class PromoteStrings$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    public   PromoteStrings$ () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // no position
  /**
   * Changes Boolean values to Bytes so that expressions like true < false can be Evaluated.
   */
  public  class BooleanComparisons$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    public   BooleanComparisons$ () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Literal> trueValues () { throw new RuntimeException(); }
    public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Literal> falseValues () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // no position
  /**
   * Casts to/from {@link BooleanType} are transformed into comparisons since
   * the JVM does not consider Booleans to be numeric types.
   */
  public  class BooleanCasts$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    public   BooleanCasts$ () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // no position
  /**
   * When encountering a cast from a string representing a valid fractional number to an integral
   * type the jvm will throw a <code>java.lang.NumberFormatException</code>.  Hive, in contrast, returns the
   * truncated version of this number.
   */
  public  class StringToIntegralCasts$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    public   StringToIntegralCasts$ () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // no position
  /**
   * This ensure that the types for various functions are as expected.
   */
  public  class FunctionArgumentConversion$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    public   FunctionArgumentConversion$ () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // no position
  /**
   * Hive only performs integral division with the DIV operator. The arguments to / are always
   * converted to fractional types.
   */
  public  class Division$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> {
    public   Division$ () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  // no position
  /**
   * Coerces the type of different branches of a CASE WHEN statement to a common type.
   */
  public  class CaseWhenCoercion$ extends org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> implements org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.TypeWidening {
    public   CaseWhenCoercion$ () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  }
  public abstract interface TypeWidening {
    public  scala.Option<org.apache.spark.sql.catalyst.types.DataType> findTightestCommonType (org.apache.spark.sql.catalyst.types.DataType t1, org.apache.spark.sql.catalyst.types.DataType t2) ;
  }
  static public  scala.collection.Seq<scala.Product> numericPrecedence () { throw new RuntimeException(); }
  static public  scala.collection.Seq<scala.Product> booleanPrecedence () { throw new RuntimeException(); }
  static public  scala.collection.Seq<scala.collection.Seq<org.apache.spark.sql.catalyst.types.DataType>> allPromotions () { throw new RuntimeException(); }
  public  scala.collection.immutable.List<org.apache.spark.sql.catalyst.rules.Rule<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan>> typeCoercionRules () ;
  /**
   * Accessor for nested Scala object
   */
  public  org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.PropagateTypes$ PropagateTypes () ;
  /**
   * Accessor for nested Scala object
   */
  public  org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.ConvertNaNs$ ConvertNaNs () ;
  /**
   * Accessor for nested Scala object
   */
  public  org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.WidenTypes$ WidenTypes () ;
  /**
   * Accessor for nested Scala object
   */
  public  org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.PromoteStrings$ PromoteStrings () ;
  /**
   * Accessor for nested Scala object
   */
  public  org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.BooleanComparisons$ BooleanComparisons () ;
  /**
   * Accessor for nested Scala object
   */
  public  org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.BooleanCasts$ BooleanCasts () ;
  /**
   * Accessor for nested Scala object
   */
  public  org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.StringToIntegralCasts$ StringToIntegralCasts () ;
  /**
   * Accessor for nested Scala object
   */
  public  org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.FunctionArgumentConversion$ FunctionArgumentConversion () ;
  /**
   * Accessor for nested Scala object
   */
  public  org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.Division$ Division () ;
  /**
   * Accessor for nested Scala object
   */
  public  org.apache.spark.sql.catalyst.analysis.HiveTypeCoercion.CaseWhenCoercion$ CaseWhenCoercion () ;
}
