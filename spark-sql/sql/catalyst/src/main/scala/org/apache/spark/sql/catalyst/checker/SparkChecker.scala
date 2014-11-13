package org.apache.spark.sql.catalyst.checker

import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import scala.collection.mutable.Set
import scala.collection.JavaConverters._
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import edu.thu.ss.lang.pojo.DataCategory
import edu.thu.ss.lang.pojo.DesensitizeOperation
import edu.thu.ss.lang.pojo.Policy
import edu.thu.ss.lang.parser.PolicyParser
import org.apache.spark.Logging
import edu.thu.ss.lang.pojo.ExpandedRule
import edu.thu.ss.lang.pojo.UserCategory
import edu.thu.ss.lang.pojo.Action
import edu.thu.ss.lang.pojo.DataActionPair
import edu.thu.ss.lang.pojo.Restriction
import edu.thu.ss.lang.pojo.Desensitization

object SparkChecker extends Logging {

	val checker = new SparkChecker();

	def init: Unit = {
		init("res/spark-policy.xml");
	}

	def init(path: String): Unit = {
		checker.init(path);
	}

	def apply(plan: LogicalPlan): Unit = {
		if (!checker.inited) {
			logWarning("Fail to initialize privacy checker. Privacy checker Disabled.");
			return ;
		}
		val begin = System.currentTimeMillis();

		LabelPropagator(plan);
		val projections = new HashSet[Label];
		plan.projections.foreach(t => projections.add(t._2))
		checker.check(projections, plan.tests);

		val end = System.currentTimeMillis();
		//TODO print
		val time = end - begin;
		println(s"privacy checking finished in $time ms");

	}
}

class SparkChecker extends PrivacyChecker {

	case class Path(val ops: Seq[DesensitizeOperation]);

	var projectionPaths: Map[DataCategory, Set[Path]] = null;
	var testPaths: Map[DataCategory, Set[Path]] = null;

	def check(projections: Set[Label], tests: Set[Label]): Unit = {
		projectionPaths = new HashMap[DataCategory, Set[Path]];
		testPaths = new HashMap[DataCategory, Set[Path]];
		projections.foreach(buildPath(_, projectionPaths));
		tests.foreach(buildPath(_, testPaths));

		printPaths(projectionPaths, testPaths);
		val user = MetaRegistry.get.currentUser();
		rules.foreach(checkRule(_, user, projectionPaths, testPaths));
	}

	private def printPaths(projectionPaths: Map[DataCategory, Set[Path]], testPaths: Map[DataCategory, Set[Path]]) {
		println("\nprojection paths:");
		projectionPaths.foreach(t => t._2.foreach(path => println(s"${t._1}\t$path")));

		println("\ntest paths:");
		testPaths.foreach(t => t._2.foreach(path => println(s"${t._1}\t$path")));
	}

	private def checkRule(rule: ExpandedRule, user: UserCategory, projectionPaths: Map[DataCategory, Set[Path]], testPaths: Map[DataCategory, Set[Path]]): Unit = {
		if (!rule.getUsers().contains(user)) {
			return
		}

		val pairs = rule.getDatas();
		val access = Array.fill(pairs.length)(new HashSet[DataCategory]);
		for (i <- 0 to pairs.length - 1) {
			val pair = pairs(i);
			pair.getAction().getId() match {
				case Action.Action_All => {
					collectLabels(pair, projectionPaths, access(i));
					collectLabels(pair, testPaths, access(i));
				}
				case Action.Action_Project => collectLabels(pair, projectionPaths, access(i));
				case Action.Action_Test => collectLabels(pair, testPaths, access(i));
			}
		}
		if (access.exists(_.size == 0)) {
			return
		}

		if (!checkRestrictions(rule, access)) {
			throw new PrivacyException(s"The SQL query violates the rule: #${rule.getRuleId()}.");
		}
	}

	private def collectLabels(pair: DataActionPair, paths: Map[DataCategory, Set[Path]], access: Set[DataCategory]): Unit = {
		pair.getDatas().asScala.foreach(data => if (paths.contains(data)) {
			access.add(data);
		});
	}

	private def checkRestrictions(rule: ExpandedRule, accesses: Array[HashSet[DataCategory]]): Boolean = {
		if (rule.getRestriction().isForbid()) {
			return false;
		}
		val array = new Array[DataCategory](accesses.length);

		return checkRestrictions(array, 0, rule, accesses);
	}

	private def checkRestrictions(array: Array[DataCategory], i: Int, rule: ExpandedRule, accesses: Array[HashSet[DataCategory]]): Boolean = {
		if (i == accesses.length) {
			val restrictions = rule.getRestrictions();
			val pairs = rule.getDatas();
			return restrictions.exists(res => {
				if (res.isForbid()) {
					return false;
				}
				res.getDesensitizations().asScala.forall(de => {
					for (index <- de.getDataIndex()) {
						val pair = pairs(index);
						val data = array(index);
						pair.getAction().getId() match {
							case Action.Action_All => if (!checkOperations(de, data, projectionPaths) || !checkOperations(de, data, testPaths)) {
								return false
							}
							case Action.Action_Project => if (!checkOperations(de, data, projectionPaths)) {
								return false
							}
							case Action.Action_Test => if (!checkOperations(de, data, testPaths)) {
								return false
							}
						}
					}
					return true;
				});
			});
		}
		val access = accesses(i);
		for (data <- access) {
			array(i) = data;
			if (!checkRestrictions(array, i + 1, rule, accesses)) {
				return false;
			}
		}
		return true;
	}

	private def checkOperations(de: Desensitization, data: DataCategory, paths: Map[DataCategory, Set[Path]]): Boolean = {
		val set = paths.getOrElse(data, null);
		if (set == null) {
			return true;
		}
		var ops = de.getOperations();
		if (ops == null) {
			//fall back to default desensitize operations
			ops = data.getOperations();
		}
		set.forall(path => path.ops.exists(ops.contains(_)));

	}

	private def buildPath(label: Label, paths: Map[DataCategory, Set[Path]], list: ListBuffer[String] = new ListBuffer): Unit = {
		label match {
			case data: DataLabel => {
				addPath(data, list, paths);
			}
			case func: Function => {
				list.prepend(func.udf);
				func.children.foreach(buildPath(_, paths, list));
				list.remove(0);
			}
			case pred: Predicate => {
				pred.children.foreach(buildPath(_, paths, list));
			}
			case _ =>
		}
	}

	private def addPath(label: DataLabel, udfs: ListBuffer[String], paths: Map[DataCategory, Set[Path]]): Unit = {
		var set = paths.getOrElse(label.data, null);
		if (set == null) {
			set = new HashSet[Path];
			paths.put(label.data, set);
		}

		val ops = udfs.map(MetaRegistry.get.lookup(_, label)).filter(_ != null);
		set.add(Path(ops));
	}
}