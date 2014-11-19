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

sealed abstract class Label;

abstract class ColumnLabel(val database: String, val table: String, val attr: AttributeReference) extends Label;

case class DataLabel(data: DataCategory, override val database: String, override val table: String, override val attr: AttributeReference)
  extends ColumnLabel(database, table, attr);

case class Insensitive(override val database: String, override val table: String, override val attr: AttributeReference)
  extends ColumnLabel(database, table, attr);

case class ConditionalLabel(conds: Map[JoinCondition, DataCategory], override val database: String, override val table: String, override val attr: AttributeReference)
  extends ColumnLabel(database, table, attr) with Equals {

  var fulfilled: Set[DataCategory] = null;

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

case class Function(val children: Seq[Label], val udf: String) extends Label;

case class Constant(val value: Any) extends Label;

case class Predicate(val children: Seq[Label], val operation: String) extends Label;