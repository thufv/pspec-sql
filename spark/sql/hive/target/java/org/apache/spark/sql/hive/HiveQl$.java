package org.apache.spark.sql.hive;
// no position
/** Provides a mapping from HiveQL statements to catalyst logical plans and expression trees. */
public  class HiveQl$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final HiveQl$ MODULE$ = null;
  public   HiveQl$ () { throw new RuntimeException(); }
  protected  scala.collection.Seq<java.lang.String> nativeCommands () { throw new RuntimeException(); }
  protected  scala.collection.Seq<java.lang.String> noExplainCommands () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.SparkSQLParser hqlParser () { throw new RuntimeException(); }
  /**
   * Returns the AST for the given SQL string.
   */
  public  org.apache.hadoop.hive.ql.parse.ASTNode getAst (java.lang.String sql) { throw new RuntimeException(); }
  /** Returns a LogicalPlan for a given HiveQL string. */
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan parseSql (java.lang.String sql) { throw new RuntimeException(); }
  public  scala.util.matching.Regex errorRegEx () { throw new RuntimeException(); }
  /** Creates LogicalPlan for a given HiveQL string. */
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan createPlan (java.lang.String sql) { throw new RuntimeException(); }
  // not preceding
  public  org.apache.spark.sql.catalyst.plans.logical.Subquery createPlanForView (org.apache.hadoop.hive.ql.metadata.Table view, scala.Option<java.lang.String> alias) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> parseDdl (java.lang.String ddl) { throw new RuntimeException(); }
  protected  scala.collection.Seq<scala.Option<org.apache.hadoop.hive.ql.lib.Node>> getClauses (scala.collection.Seq<java.lang.String> clauseNames, scala.collection.Seq<org.apache.hadoop.hive.ql.parse.ASTNode> nodeList) { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.ql.lib.Node getClause (java.lang.String clauseName, scala.collection.Seq<org.apache.hadoop.hive.ql.lib.Node> nodeList) { throw new RuntimeException(); }
  public  scala.Option<org.apache.hadoop.hive.ql.lib.Node> getClauseOption (java.lang.String clauseName, scala.collection.Seq<org.apache.hadoop.hive.ql.lib.Node> nodeList) { throw new RuntimeException(); }
  // not preceding
  protected  org.apache.spark.sql.catalyst.expressions.Attribute nodeToAttribute (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.types.DataType nodeToDataType (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.types.StructField nodeToStructField (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.NamedExpression> nameExpressions (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> exprs) { throw new RuntimeException(); }
  protected  scala.Tuple2<scala.Option<java.lang.String>, java.lang.String> extractDbNameTableName (org.apache.hadoop.hive.ql.lib.Node tableNameParts) { throw new RuntimeException(); }
  // not preceding
  protected  scala.collection.Seq<java.lang.String> extractTableIdent (org.apache.hadoop.hive.ql.lib.Node tableNameParts) { throw new RuntimeException(); }
  protected  scala.Tuple2<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, scala.collection.Seq<java.lang.Object>> extractGroupingSet (scala.collection.Seq<org.apache.hadoop.hive.ql.parse.ASTNode> children) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan nodeToPlan (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  public  scala.util.matching.Regex allJoinTokens () { throw new RuntimeException(); }
  public  scala.util.matching.Regex laterViewToken () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan nodeToRelation (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.SortOrder nodeToSortOrder (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  public  scala.util.matching.Regex destinationToken () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan nodeToDest (org.apache.hadoop.hive.ql.lib.Node node, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan query, boolean overwrite) { throw new RuntimeException(); }
  protected  scala.Option<org.apache.spark.sql.catalyst.expressions.Expression> selExprNodeToExpr (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  protected  scala.util.matching.Regex escapedIdentifier () { throw new RuntimeException(); }
  /** Strips backticks from ident if present */
  protected  java.lang.String cleanIdentifier (java.lang.String ident) { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.Object> numericAstTypes () { throw new RuntimeException(); }
  public  scala.util.matching.Regex ARRAY () { throw new RuntimeException(); }
  public  scala.util.matching.Regex COALESCE () { throw new RuntimeException(); }
  public  scala.util.matching.Regex COUNT () { throw new RuntimeException(); }
  public  scala.util.matching.Regex AVG () { throw new RuntimeException(); }
  public  scala.util.matching.Regex SUM () { throw new RuntimeException(); }
  public  scala.util.matching.Regex MAX () { throw new RuntimeException(); }
  public  scala.util.matching.Regex MIN () { throw new RuntimeException(); }
  public  scala.util.matching.Regex UPPER () { throw new RuntimeException(); }
  public  scala.util.matching.Regex LOWER () { throw new RuntimeException(); }
  public  scala.util.matching.Regex RAND () { throw new RuntimeException(); }
  public  scala.util.matching.Regex AND () { throw new RuntimeException(); }
  public  scala.util.matching.Regex OR () { throw new RuntimeException(); }
  public  scala.util.matching.Regex NOT () { throw new RuntimeException(); }
  public  scala.util.matching.Regex TRUE () { throw new RuntimeException(); }
  public  scala.util.matching.Regex FALSE () { throw new RuntimeException(); }
  public  scala.util.matching.Regex LIKE () { throw new RuntimeException(); }
  public  scala.util.matching.Regex RLIKE () { throw new RuntimeException(); }
  public  scala.util.matching.Regex REGEXP () { throw new RuntimeException(); }
  public  scala.util.matching.Regex IN () { throw new RuntimeException(); }
  public  scala.util.matching.Regex DIV () { throw new RuntimeException(); }
  public  scala.util.matching.Regex BETWEEN () { throw new RuntimeException(); }
  public  scala.util.matching.Regex WHEN () { throw new RuntimeException(); }
  public  scala.util.matching.Regex CASE () { throw new RuntimeException(); }
  public  scala.util.matching.Regex SUBSTR () { throw new RuntimeException(); }
  public  scala.util.matching.Regex SQRT () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.expressions.Expression nodeToExpr (org.apache.hadoop.hive.ql.lib.Node node) { throw new RuntimeException(); }
  public  scala.util.matching.Regex explode () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Generator nodesToGenerator (scala.collection.Seq<org.apache.hadoop.hive.ql.lib.Node> nodes) { throw new RuntimeException(); }
  public  scala.collection.mutable.StringBuilder dumpTree (org.apache.hadoop.hive.ql.lib.Node node, scala.collection.mutable.StringBuilder builder, int indent) { throw new RuntimeException(); }
}
