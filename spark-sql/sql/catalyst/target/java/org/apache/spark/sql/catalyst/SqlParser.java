package org.apache.spark.sql.catalyst;
/**
 * A very simple SQL parser.  Based loosely on:
 * https://github.com/stephentu/scala-sql-parser/blob/master/src/main/scala/parser.scala
 * <p>
 * Limitations:
 *  - Only supports a very limited subset of SQL.
 * <p>
 * This is currently included mostly for illustrative purposes.  Users wanting more complete support
 * for a SQL like language should checkout the HiveQL support in the sql/hive sub-project.
 */
public  class SqlParser extends scala.util.parsing.combinator.syntactical.StandardTokenParsers implements scala.util.parsing.combinator.PackratParsers {
  public   SqlParser () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan apply (java.lang.String input) { throw new RuntimeException(); }
  protected  class Keyword implements scala.Product, scala.Serializable {
    public  java.lang.String str () { throw new RuntimeException(); }
    // not preceding
    public   Keyword (java.lang.String str) { throw new RuntimeException(); }
  }
  // no position
  protected  class Keyword extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.sql.catalyst.SqlParser.Keyword> implements scala.Serializable {
    public   Keyword () { throw new RuntimeException(); }
  }
  protected  scala.util.parsing.combinator.Parsers.Parser<java.lang.String> asParser (org.apache.spark.sql.catalyst.SqlParser.Keyword k) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword ALL () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword AND () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword AS () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword ASC () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword APPROXIMATE () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword AVG () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword BY () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword CACHE () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword CAST () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword COUNT () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword DESC () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword DISTINCT () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword FALSE () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword FIRST () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword FROM () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword FULL () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword GROUP () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword HAVING () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword IF () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword IN () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword INNER () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword INSERT () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword INTO () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword IS () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword JOIN () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword LEFT () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword LIMIT () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword MAX () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword MIN () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword NOT () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword NULL () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword ON () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword OR () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword OVERWRITE () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword LIKE () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword RLIKE () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword UPPER () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword LOWER () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword REGEXP () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword ORDER () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword OUTER () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword RIGHT () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword SELECT () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword SEMI () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword STRING () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword SUM () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword TABLE () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword TRUE () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword UNCACHE () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword UNION () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword WHERE () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword INTERSECT () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword EXCEPT () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword SUBSTR () { throw new RuntimeException(); }
  protected  org.apache.spark.sql.catalyst.SqlParser.Keyword SUBSTRING () { throw new RuntimeException(); }
  protected  java.lang.String[] reservedWords () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.SqlLexical lexical () { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.NamedExpression> assignAliases (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> exprs) { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> query () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> select () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> insert () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> cache () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>> projections () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> projection () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> from () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> inTo () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> relations () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> relation () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> relationFactor () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.logical.LogicalPlan> joinedRelation () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> joinConditions () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.plans.JoinType> joinType () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> filter () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder>> orderBy () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.SortOrder>> ordering () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.SortOrder> singleOrder () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.SortDirection> direction () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>> grouping () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> having () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> limit () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> expression () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> orExpression () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> andExpression () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> comparisonExpression () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> termExpression () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> productExpression () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> function () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Expression> cast () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.expressions.Literal> literal () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<java.lang.String> floatLit () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.PackratParsers.PackratParser<org.apache.spark.sql.catalyst.expressions.Expression> baseExpression () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> dataType () { throw new RuntimeException(); }
}
