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

/**
 * base class for lineage tree
 */
sealed abstract class Label;

/**
 * base class for column node in lineage tree
 */
abstract class ColumnLabel(val database: String, val table: String, val attr: AttributeReference) extends Label;

/**
 * class for data category node (leaf) in lineage tree
 */
case class DataLabel(labelType: BaseType, override val database: String, override val table: String, override val attr: AttributeReference)
  extends ColumnLabel(database, table, attr);

/**
 * class for insensitive attribute (leaf) in lineage tree
 */
case class Insensitive(override val database: String, override val table: String, override val attr: AttributeReference)
  extends ColumnLabel(database, table, attr);

/**
 * class for conditional category attribute (leaf) in lineage tree
 * @see {@link ConditionalColumn}
 */
case class ConditionalLabel(conds: Map[JoinCondition, BaseType], override val database: String, override val table: String, override val attr: AttributeReference)
  extends ColumnLabel(database, table, attr) with Equals {

  var fulfilled: Set[BaseType] = null;

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
case class Function(val children: Seq[Label], val udf: String) extends Label;

/**
 * class for constant node (leaf) in lineage tree
 */
case class Constant(val value: Any) extends Label;

/**
 * class for predicate node (non-leaf, root) in lineage tree
 * predicate node only appears in condition lineage trees
 */
case class Predicate(val children: Seq[Label], val operation: String) extends Label;