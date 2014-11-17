package org.apache.spark.sql.catalyst.checker

import scala.collection.mutable.Set
import org.apache.spark.Logging
import scala.collection.JavaConverters._
import edu.thu.ss.spec.meta.MetaRegistry
import edu.thu.ss.spec.lang.pojo.ExpandedRule
import edu.thu.ss.spec.lang.pojo.Policy
import edu.thu.ss.spec.lang.parser.PolicyParser
import edu.thu.ss.spec.meta.xml.XMLMetaRegistryParser
import edu.thu.ss.spec.meta.MetaRegistryManager
import org.apache.spark.sql.catalyst.analysis.Catalog

trait PrivacyChecker extends Logging {

	protected var policy: Policy = null;
	protected var rules: Seq[ExpandedRule] = null;
	def init(catalog: Catalog, policyPath: String, metaPath: String): Unit = {
		val parser = new PolicyParser();
		try {
			policy = parser.parse(policyPath, false);
			rules = policy.getExpandedRules().asScala;

			val metaParser = new XMLMetaRegistryParser(policy);
			val meta = metaParser.parse(metaPath);

			MetaRegistryManager.set(meta);
			logWarning(s"Privacy Checker successfully initialized with privacy policy: $policyPath and meta: $metaPath");
		} catch {
			case e: Exception => logError("PrivacyChecker disabled.", e);
		}
	}

	def inited = policy != null;

}