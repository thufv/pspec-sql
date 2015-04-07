package org.apache.spark.sql.catalyst.checker

import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.checker.LabelConstants._
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
import edu.thu.ss.spec.meta.BaseType
import edu.thu.ss.spec.meta.JoinCondition
import edu.thu.ss.spec.lang.pojo.DataCategory
import edu.thu.ss.spec.meta.StructType
import edu.thu.ss.spec.meta.ArrayType

/**
 * base class for lineage tree
 */
sealed abstract class Label {
  def sensitive(): Boolean;

  val attributes: Seq[Attribute];

  def contains(trans: String*): Boolean;

  def getDatas() = getTypes.flatMap(_.toPrimitives().map(_.getDataCategory));

  def getTypes(): Seq[BaseType];

}

/**
 * base class for column node in lineage tree
 */
abstract class ColumnLabel extends Label {
  val database: String;
  val table: String;
  val attr: AttributeReference;

  lazy val attributes = List(attr);

  def contains(trans: String*) = false;

}

/**
 * class for data category node (leaf) in lineage tree
 */
case class DataLabel(labelType: BaseType, database: String, table: String, attr: AttributeReference)
  extends ColumnLabel {
  def sensitive(): Boolean = true;

  def getTypes = Seq(labelType);

}

/**
 * class for insensitive attribute (leaf) in lineage tree
 */
case class Insensitive(database: String, table: String, attr: AttributeReference)
  extends ColumnLabel {
  def sensitive(): Boolean = false;

  def getTypes = Nil;
}

/**
 * class for conditional category attribute (leaf) in lineage tree
 * @see {@link ConditionalColumn}
 */
case class ConditionalLabel(conds: Map[JoinCondition, BaseType], database: String, table: String, attr: AttributeReference)
  extends ColumnLabel with Equals {

  var fulfilled: Set[BaseType] = null;

  def sensitive(): Boolean = fulfilled != null && fulfilled.size > 0;
  def canEqual(other: Any) = {
    other.isInstanceOf[org.apache.spark.sql.catalyst.checker.ConditionalLabel]
  }

  def getTypes = if (fulfilled != null) fulfilled.toSeq; else Nil;

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
case class FunctionLabel(children: Seq[Label], transform: String, expression: Expression) extends Label {
  def sensitive(): Boolean = children.exists(_.sensitive);

  lazy val attributes = children.flatMap(_.attributes);

  def contains(trans: String*): Boolean = {
    if (trans.contains(trans)) {
      return true;
    }
    return children.exists(_.contains(trans: _*));
  }

  def getTypes: Seq[BaseType] = {
    children.flatMap(_.getTypes).flatMap(resolveType(_, FunctionLabel.this)).filter(_ != null);
  }

}

/**
 * class for constant node (leaf) in lineage tree
 */
case class ConstantLabel(value: Any) extends Label {
  def sensitive(): Boolean = false;

  lazy val attributes = Nil;

  def contains(trans: String*) = false;

  def getTypes = Nil;
}

/**
 * class for predicate node (non-leaf, root) in lineage tree
 * predicate node only appears in condition lineage trees
 */
case class PredicateLabel(val children: Seq[Label], val operation: String) extends Label {
  def sensitive(): Boolean = children.exists(_.sensitive);

  lazy val attributes = children.flatMap(_.attributes);

  def contains(trans: String*) = false;

  def getTypes = children.flatMap(_.getTypes);

}