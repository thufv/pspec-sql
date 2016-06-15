package org.apache.spark.sql.catalyst.checker.dp

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.collection.mutable.HashMap
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.checker.PrivacyException
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.AggregateExpression
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import scala.collection.mutable.HashSet
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan

trait DPBudgetManager {
  def consume(epsilon: Double): Boolean;

}

class GlobalBudgetManager(private var budget: Double) extends DPBudgetManager with Logging {

  def consume(epsilon: Double): Boolean = {
    if (budget > epsilon) {
      budget = budget - epsilon;
      return true;
    } else {
      return false;
    }
  }

}
