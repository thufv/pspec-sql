package org.apache.spark.sql.catalyst.planning;
// no position
/**
 * A pattern that matches any number of project or filter operations on top of another relational
 * operator.  All filter operators are collected and their conditions are broken up and returned
 * together with the top project operator.
 * {@link org.apache.spark.sql.catalyst.expressions.Alias Aliases} are in-lined/substituted if
 * necessary.
 */
public  class PhysicalOperation implements org.apache.spark.sql.catalyst.expressions.PredicateHelper {
  static public  scala.Option<scala.Tuple3<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.NamedExpression>, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan>> unapply (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  /**
   * Collects projects and filters, in-lining/substituting aliases if necessary.  Here are two
   * examples for alias in-lining/substitution.  Before:
   * <pre><code>
   *   SELECT c1 FROM (SELECT key AS c1 FROM t1) t2 WHERE c1 &gt; 10
   *   SELECT c1 AS c2 FROM (SELECT key AS c1 FROM t1) t2 WHERE c1 &gt; 10
   * </code></pre>
   * After:
   * <pre><code>
   *   SELECT key AS c1 FROM t1 WHERE key &gt; 10
   *   SELECT key AS c2 FROM t1 WHERE key &gt; 10
   * </code></pre>
   */
  static public  scala.Tuple4<scala.Option<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.NamedExpression>>, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression>, org.apache.spark.sql.catalyst.plans.logical.LogicalPlan, scala.collection.immutable.Map<org.apache.spark.sql.catalyst.expressions.Attribute, org.apache.spark.sql.catalyst.expressions.Expression>> collectProjectsAndFilters (org.apache.spark.sql.catalyst.plans.logical.LogicalPlan plan) { throw new RuntimeException(); }
  static public  scala.collection.immutable.Map<org.apache.spark.sql.catalyst.expressions.Attribute, org.apache.spark.sql.catalyst.expressions.Expression> collectAliases (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> fields) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.expressions.Expression substitute (scala.collection.immutable.Map<org.apache.spark.sql.catalyst.expressions.Attribute, org.apache.spark.sql.catalyst.expressions.Expression> aliases, org.apache.spark.sql.catalyst.expressions.Expression expr) { throw new RuntimeException(); }
}
