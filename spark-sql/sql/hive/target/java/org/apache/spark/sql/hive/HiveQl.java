package org.apache.spark.sql.hive;
// no position
/** Provides a mapping from HiveQL statements to catalyst logical plans and expression trees. */
private  class HiveQl {
  // no position
  /** Extractor for matching Hive's AST Tokens. */
  static public  class Token$ {
    public   Token$ () { throw new RuntimeException(); }
    /** @return matches of the form (tokenName, children). */
    public  scala.Option<scala.Tuple2<java.lang.String, scala.collection.Seq<org.apache.hadoop.hive.ql.parse.ASTNode>>> unapply (Object t) { throw new RuntimeException(); }
  }
  /**
   * A set of implicit transformations that allow Hive ASTNodes to be rewritten by transformations
   * similar to {@link catalyst.trees.TreeNode}.
   * <p>
   * Note that this should be considered very experimental and is not indented as a replacement
   * for TreeNode.  Primarily it should be noted ASTNodes are not immutable and do not appear to
   * have clean copy semantics.  Therefore, users of this class should take care when
   * copying/modifying trees that might be used elsewhere.
   */
  static public  class TransformableNode {
    public   TransformableNode (org.apache.hadoop.hive.ql.parse.ASTNode n) { throw new RuntimeException(); }
    /**
     * Returns a copy of this node where <code>rule</code> has been recursively applied to it and all of its
     * children.  When <code>rule</code> does not apply to a given node it is left unchanged.
     * @param rule the function use to transform this nodes children
     */
    public  org.apache.hadoop.hive.ql.parse.ASTNode transform (scala.PartialFunction<org.apache.hadoop.hive.ql.parse.ASTNode, org.apache.hadoop.hive.ql.parse.ASTNode> rule) { throw new RuntimeException(); }
    /**
     * Returns a scala.Seq equivalent to [s] or Nil if [s] is null.
     */
    private <A extends java.lang.Object> scala.collection.Seq<A> nilIfEmpty (java.util.List<A> s) { throw new RuntimeException(); }
    /**
     * Returns this ASTNode with the text changed to <code>newText</code>.
     */
    public  org.apache.hadoop.hive.ql.parse.ASTNode withText (java.lang.String newText) { throw new RuntimeException(); }
    /**
     * Returns this ASTNode with the children changed to <code>newChildren</code>.
     */
    public  org.apache.hadoop.hive.ql.parse.ASTNode withChildren (scala.collection.Seq<org.apache.hadoop.hive.ql.parse.ASTNode> newChildren) { throw new RuntimeException(); }
    /**
     * Throws an error if this is not equal to other.
     * <p>
     * Right now this function only checks the name, type, text and children of the node
     * for equality.
     */
    public  void checkEquals (org.apache.hadoop.hive.ql.parse.ASTNode other) { throw new RuntimeException(); }
  }
  /**
   * Throws an error if this is not equal to other.
   * <p>
   * Right now this function only checks the name, type, text and children of the node
   * for equality.
   */
  static public  class ParseException extends java.lang.Exception {
    public   ParseException (java.lang.String sql, java.lang.Throwable cause) { throw new RuntimeException(); }
  }
  static public  class SemanticException extends java.lang.Exception {
    public   SemanticException (java.lang.String msg) { throw new RuntimeException(); }
  }
  static protected  scala.collection.Seq<java.lang.String> nativeCommands () { throw new RuntimeException(); }
  static protected  scala.collection.Seq<java.lang.String> noExplainCommands () { throw new RuntimeException(); }
  /**
   * Returns the AST for the given SQL string.
   */
  static public  org.apache.hadoop.hive.ql.parse.ASTNode getAst (java.lang.String sql) { throw new RuntimeException(); }
  /** Returns a LogicalPlan for a given HiveQL string. */
  static public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan parseSql (java.lang.String sql) { throw new RuntimeException(); }
  static public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> parseDdl (java.lang.String ddl) { throw new RuntimeException(); }
  static protected  scala.collection.Seq<scala.Option<org.apache.hadoop.hive.ql.lib.Node>> getClauses (scala.collection.Seq<java.lang.String> clauseNames, scala.collection.Seq<org.apache.hadoop.hive.ql.parse.ASTNode> nodeList) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.ql.lib.Node getClause (java.lang.String clauseName, scala.collection.Seq<org.apache.hadoop.hive.ql.lib.Node> nodeList) { throw new RuntimeException(); }
  static public  scala.Option<org.apache.hadoop.hive.ql.lib.Node> getClauseOption (java.lang.String clauseName, scala.collection.Seq<org.apache.hadoop.hive.ql.lib.Node> nodeList) { throw new RuntimeException(); }
  static protected  org.apache.spark.sql.catalyst.expressions.Attribute nodeToAttribute (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  static protected  org.apache.spark.sql.catalyst.types.DataType nodeToDataType (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  static protected  org.apache.spark.sql.catalyst.types.StructField nodeToStructField (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  static protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.NamedExpression> nameExpressions (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> exprs) { throw new RuntimeException(); }
  static protected  scala.Tuple2<scala.Option<java.lang.String>, java.lang.String> extractDbNameTableName (org.apache.hadoop.hive.ql.lib.Node tableNameParts) { throw new RuntimeException(); }
  static protected  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan nodeToPlan (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  static public  scala.util.matching.Regex allJoinTokens () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex laterViewToken () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan nodeToRelation (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.expressions.SortOrder nodeToSortOrder (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  static public  scala.util.matching.Regex destinationToken () { throw new RuntimeException(); }
  static protected  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan nodeToDest (org.apache.hadoop.hive.ql.lib.Node node, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan query, boolean overwrite) { throw new RuntimeException(); }
  static protected  scala.Option<org.apache.spark.sql.catalyst.expressions.Expression> selExprNodeToExpr (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  static protected  scala.util.matching.Regex escapedIdentifier () { throw new RuntimeException(); }
  /** Strips backticks from ident if present */
  static protected  java.lang.String cleanIdentifier (java.lang.String ident) { throw new RuntimeException(); }
  static public  scala.collection.Seq<java.lang.Object> numericAstTypes () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex COUNT () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex AVG () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex SUM () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex MAX () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex MIN () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex UPPER () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex LOWER () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex RAND () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex AND () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex OR () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex NOT () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex TRUE () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex FALSE () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex LIKE () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex RLIKE () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex REGEXP () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex IN () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex DIV () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex BETWEEN () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex WHEN () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex CASE () { throw new RuntimeException(); }
  static public  scala.util.matching.Regex SUBSTR () { throw new RuntimeException(); }
  static protected  org.apache.spark.sql.catalyst.expressions.Expression nodeToExpr (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  static public  scala.util.matching.Regex explode () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.expressions.Generator nodesToGenerator (scala.collection.Seq<org.apache.hadoop.hive.ql.lib.Node> nodes) { throw new RuntimeException(); }
  static public  scala.collection.mutable.StringBuilder dumpTree (org.apache.hadoop.hive.ql.lib.Node node, scala.collection.mutable.StringBuilder builder, int indent) { throw new RuntimeException(); }
}
