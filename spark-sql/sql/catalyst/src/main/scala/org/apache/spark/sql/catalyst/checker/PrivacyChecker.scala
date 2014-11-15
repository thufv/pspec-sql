package org.apache.spark.sql.catalyst.checker

import scala.collection.mutable.Set
import org.apache.spark.Logging
import scala.collection.JavaConverters._
import edu.thu.ss.spec.meta.MetaRegistry
import edu.thu.ss.spec.lang.pojo.ExpandedRule
import edu.thu.ss.spec.lang.pojo.Policy
import edu.thu.ss.spec.lang.parser.PolicyParser

trait PrivacyChecker extends Logging {

	protected var policy: Policy = null;
	protected var rules: Seq[ExpandedRule] = null;
	def init(path: String): Unit = {
		val parser = new PolicyParser();
		try {
			policy = parser.parse(path, false);
			rules = policy.getExpandedRules().asScala;
		//	MetaRegistry.get.init(policy);
			logWarning(s"Privacy Checker successfully initialized with privacy policy: $path");
		} catch {
			case e: Exception => logError("PrivacyChecker disabled.", e);
		}
	}

	def inited = policy != null;

	def check(projections: Set[Label], tests: Set[Label]): Unit;
}