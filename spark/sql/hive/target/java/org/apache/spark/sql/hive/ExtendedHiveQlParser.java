package org.apache.spark.sql.hive;
/**
 * A parser that recognizes all HiveQL constructs together with Spark SQL specific extensions.
 */
public  class ExtendedHiveQlParser extends org.apache.spark.sql.catalyst.AbstractSparkSQLParser {
  public   ExtendedHiveQlParser () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.AbstractSparkSQLParser.Keyword ADD () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.AbstractSparkSQLParser.Keyword DFS () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.AbstractSparkSQLParser.Keyword FILE () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.AbstractSparkSQLParser.Keyword JAR () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> start () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> hiveQl () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> dfs () { throw new RuntimeException(); }
  private  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> addFile () { throw new RuntimeException(); }
  private  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> addJar () { throw new RuntimeException(); }
}
