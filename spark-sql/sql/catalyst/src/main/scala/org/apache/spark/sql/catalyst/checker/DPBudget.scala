package org.apache.spark.sql.catalyst.checker

import org.apache.spark.Logging

class DPBudget(var budget: Double) extends Logging {

  var tmpBudget: Double = budget;

  def commit() {
    logInfo(s"commit privacy budget $tmpBudget");
    budget = tmpBudget;
  }

  def rollback() {
    logInfo(s"rollback privacy budget to $budget");
    tmpBudget = budget;
  }

  def consume(epsilon: Double) {
    if (tmpBudget - epsilon < 0) {
      throw new PrivacyException(s"No enough privacy budget for the query. Privacy budget left: $budget");
    }
    tmpBudget -= epsilon;
  }
}