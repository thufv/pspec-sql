package org.apache.spark.sql.catalyst.checker

import org.apache.spark.sql.catalyst.expressions.Attribute
import edu.thu.ss.spec.lang.pojo.DataCategory
import edu.thu.ss.spec.meta.JoinCondition
import scala.collection.mutable.HashSet
import scala.collection.mutable.Map
import scala.collection.mutable.Set
import scala.collection.JavaConverters._
import edu.thu.ss.spec.meta.JoinCondition.ColumnEntry
import scala.collection.mutable.HashMap
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import edu.thu.ss.spec.meta.BaseType
import org.apache.spark.sql.catalyst.expressions.Expression

/**
 * base class for lineage tree
 */
sealed abstract class Label {
  def sensitive(): Boolean;
}

/**
 * base class for column node in lineage tree
 */
abstract class ColumnLabel extends Label {
  val database: String;
  val table: String;
  val attr: AttributeReference;
}

/**
 * class for data category node (leaf) in lineage tree
 */
case class DataLabel(labelType: BaseType, database: String, table: String, attr: AttributeReference)
  extends ColumnLabel {
  def sensitive(): Boolean = true;
}

/**
 * class for insensitive attribute (leaf) in lineage tree
 */
case class Insensitive(database: String, table: String, attr: AttributeReference)
  extends ColumnLabel {
  def sensitive(): Boolean = false;
}

/**
 * class for conditional category attribute (leaf) in lineage tree
 * @see {@link ConditionalColumn}
 */
case class ConditionalLabel(conds: Map[JoinCondition, BaseType], database: String, table: String, attr: AttributeReference)
  extends ColumnLabel with Equals {

  var fulfilled: Set[BaseType] = null;

  def sensitive(): Boolean = true;

  def canEqual(other: Any) = {
    other.isInstanceOf[org.apache.spark.sql.catalyst.checker.ConditionalLabel]
  }

  override def equals(other: Any) = {
    other match {
      case that: ConditionalLabel => that.canEqual(ConditionalLabel.this) && database == that.database && table == that.table && attr == that.attr
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime * (prime * (prime + database.hashCode) + table.hashCode) + attr.hashCode
  }
}

/**
 * class for function node (non-leaf) in lineage tree
 */
case class Function(val children: Seq[Label], val udf: String, val expression: Expression) extends Label {
  def sensitive(): Boolean = children.exists(_.sensitive);
}

/**
 * class for constant node (leaf) in lineage tree
 */
case class Constant(val value: Any) extends Label {
  def sensitive(): Boolean = false;
}

/**
 * class for predicate node (non-leaf, root) in lineage tree
 * predicate node only appears in condition lineage trees
 */
case class Predicate(val children: Seq[Label], val operation: String) extends Label {
  def sensitive(): Boolean = children.exists(_.sensitive);
}