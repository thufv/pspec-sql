package org.apache.spark.sql.catalyst.expressions.codegen;
/**
 * A base class for generators of byte code to perform expression evaluation.  Includes a set of
 * helpers for referring to Catalyst types and building trees that perform evaluation of individual
 * expressions.
 */
public abstract class CodeGenerator<InType extends java.lang.Object, OutType extends java.lang.Object> implements org.apache.spark.Logging {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Ident(scala), newTypeName("AnyRef")))))
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Ident(scala), newTypeName("AnyRef")))))
  public   CodeGenerator () { throw new RuntimeException(); }
  protected  scala.tools.reflect.ToolBox<scala.reflect.api.JavaUniverse> toolBox () { throw new RuntimeException(); }
  protected  scala.reflect.api.Types.Type rowType () { throw new RuntimeException(); }
  protected  scala.reflect.api.Types.Type mutableRowType () { throw new RuntimeException(); }
  protected  scala.reflect.api.Types.Type genericRowType () { throw new RuntimeException(); }
  protected  scala.reflect.api.Types.Type genericMutableRowType () { throw new RuntimeException(); }
  protected  scala.reflect.api.Types.Type projectionType () { throw new RuntimeException(); }
  protected  scala.reflect.api.Types.Type mutableProjectionType () { throw new RuntimeException(); }
  private  java.util.concurrent.atomic.AtomicInteger curId () { throw new RuntimeException(); }
  private  java.lang.String javaSeparator () { throw new RuntimeException(); }
  /**
   * Can be flipped on manually in the console to add (expensive) expression evaluation trace code.
   */
  public  boolean debugLogging () { throw new RuntimeException(); }
  /**
   * Generates a class for a given input expression.  Called when there is not cached code
   * already available.
   */
  protected abstract  OutType create (InType in) ;
  /**
   * Canonicalizes an input expression. Used to avoid double caching expressions that differ only
   * cosmetically.
   */
  protected abstract  InType canonicalize (InType in) ;
  /** Binds an input expression to a given input schema */
  protected abstract  InType bind (InType in, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) ;
  /**
   * A cache of generated classes.
   * <p>
   * From the Guava Docs: A Cache is similar to ConcurrentMap, but not quite the same. The most
   * fundamental difference is that a ConcurrentMap persists all elements that are added to it until
   * they are explicitly removed. A Cache on the other hand is generally configured to evict entries
   * automatically, in order to constrain its memory footprint.  Note that this cache does not use
   * weak keys/values and thus does not respond to memory pressure.
   */
  protected  com.google.common.cache.LoadingCache<InType, OutType> cache () { throw new RuntimeException(); }
  /** Generates the requested evaluator binding the given expression(s) to the inputSchema. */
  public  OutType apply (InType expressions, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> inputSchema) { throw new RuntimeException(); }
  /** Generates the requested evaluator given already bound expression(s). */
  public  OutType apply (InType expressions) { throw new RuntimeException(); }
  /**
   * Returns a term name that is unique within this instance of a <code>CodeGenerator</code>.
   * <p>
   * (Since we aren't in a macro context we do not seem to have access to the built in <code>freshName</code>
   * function.)
   */
  protected  scala.reflect.api.Names.TermName freshName (java.lang.String prefix) { throw new RuntimeException(); }
  /**
   * Scala ASTs for evaluating an {@link Expression} given a {@link Row} of input.
   * <p>
   * @param code The sequence of statements required to evaluate the expression.
   * @param nullTerm A term that holds a boolean value representing whether the expression evaluated
   *                 to null.
   * @param primitiveTerm A term for a possible primitive value of the result of the evaluation. Not
   *                      valid if <code>nullTerm</code> is set to <code>false</code>.
   * @param objectTerm A possibly boxed version of the result of evaluating this expression.
   */
  protected  class EvaluatedExpression implements scala.Product, scala.Serializable {
    public  scala.collection.Seq<scala.reflect.api.Trees.TreeApi> code () { throw new RuntimeException(); }
    public  scala.reflect.api.Names.TermName nullTerm () { throw new RuntimeException(); }
    public  scala.reflect.api.Names.TermName primitiveTerm () { throw new RuntimeException(); }
    public  scala.reflect.api.Names.TermName objectTerm () { throw new RuntimeException(); }
    // not preceding
    public   EvaluatedExpression (scala.collection.Seq<scala.reflect.api.Trees.TreeApi> code, scala.reflect.api.Names.TermName nullTerm, scala.reflect.api.Names.TermName primitiveTerm, scala.reflect.api.Names.TermName objectTerm) { throw new RuntimeException(); }
  }
  // no position
  protected  class EvaluatedExpression extends scala.runtime.AbstractFunction4<scala.collection.Seq<scala.reflect.api.Trees.TreeApi>, scala.reflect.api.Names.NameApi, scala.reflect.api.Names.NameApi, scala.reflect.api.Names.NameApi, org.apache.spark.sql.catalyst.expressions.codegen.CodeGenerator<InType, OutType>.EvaluatedExpression> implements scala.Serializable {
    public   EvaluatedExpression () { throw new RuntimeException(); }
  }
  /**
   * Given an expression tree returns an {@link EvaluatedExpression}, which contains Scala trees that
   * can be used to determine the result of evaluating the expression on an input row.
   */
  public  org.apache.spark.sql.catalyst.expressions.codegen.CodeGenerator<InType, OutType>.EvaluatedExpression expressionEvaluator (org.apache.spark.sql.catalyst.expressions.Expression e) { throw new RuntimeException(); }
  protected  scala.reflect.api.Trees.Tree getColumn (scala.reflect.api.Names.TermName inputRow, org.apache.spark.sql.catalyst.types.DataType dataType, int ordinal) { throw new RuntimeException(); }
  protected  scala.reflect.api.Trees.Tree setColumn (scala.reflect.api.Names.TermName destinationRow, org.apache.spark.sql.catalyst.types.DataType dataType, int ordinal, scala.reflect.api.Names.TermName value) { throw new RuntimeException(); }
  protected  scala.reflect.api.Names.TermName accessorForType (org.apache.spark.sql.catalyst.types.DataType dt) { throw new RuntimeException(); }
  protected  scala.reflect.api.Names.TermName mutatorForType (org.apache.spark.sql.catalyst.types.DataType dt) { throw new RuntimeException(); }
  protected  scala.reflect.api.Types.Type hashSetForType (org.apache.spark.sql.catalyst.types.DataType dt) { throw new RuntimeException(); }
  protected  java.lang.String primitiveForType (org.apache.spark.sql.catalyst.types.DataType dt) { throw new RuntimeException(); }
  protected  scala.reflect.api.Trees.Literal defaultPrimitive (org.apache.spark.sql.catalyst.types.DataType dt) { throw new RuntimeException(); }
  protected  scala.reflect.api.TypeTags.TypeTag<? super java.lang.Object> termForType (org.apache.spark.sql.catalyst.types.DataType dt) { throw new RuntimeException(); }
}
