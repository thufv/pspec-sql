package org.apache.spark.sql.catalyst.checker

import scala.collection.mutable.Set

trait PrivacyChecker {

	def check(projections: Set[Label], tests: Set[Label]): Unit;
}