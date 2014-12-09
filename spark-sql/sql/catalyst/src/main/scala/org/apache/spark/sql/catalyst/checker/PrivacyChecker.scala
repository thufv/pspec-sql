package org.apache.spark.sql.catalyst.checker

import scala.collection.Set
import org.apache.spark.Logging
import scala.collection.JavaConverters._
import edu.thu.ss.spec.meta.MetaRegistry
import edu.thu.ss.spec.lang.pojo.ExpandedRule
import edu.thu.ss.spec.lang.pojo.Policy
import edu.thu.ss.spec.lang.parser.PolicyParser
import edu.thu.ss.spec.meta.xml.XMLMetaRegistryParser
import org.apache.spark.sql.catalyst.analysis.Catalog
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan

/**
 * interface for privacy checker
 */
trait PrivacyChecker extends Logging {

  def check(projections: Set[Label], conditions: Set[Label], policies: Set[Policy]): Unit;
}