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
import edu.thu.ss.spec.meta.PrimitiveType
import edu.thu.ss.spec.meta.MapType
import edu.thu.ss.spec.global.MetaManager
import edu.thu.ss.spec.meta.CompositeType

/**
 * base class for lineage tree
 */
sealed abstract class Label {
  def sensitive(): Boolean;

  val attributes: Seq[Attribute];

  def contains(trans: String*): Boolean;

  def getDatas() = getTypes.flatMap(_.toPrimitives().map(_.getDataCategory));

  def getTypes(): Seq[BaseType];

  def getTables(): Seq[String];

  def transitTypes(): Seq[BaseType];
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

  def getTables = Seq(table);
}

/**
 * class for data category node (leaf) in lineage tree
 */
case class DataLabel(labelType: BaseType, database: String, table: String, attr: AttributeReference)
  extends ColumnLabel {
  def sensitive(): Boolean = true;

  def getTypes = Seq(labelType);

  def transitTypes = Seq(labelType);
}

/**
 * class for insensitive attribute (leaf) in lineage tree
 */
case class Insensitive(database: String, table: String, attr: AttributeReference)
  extends ColumnLabel {
  def sensitive(): Boolean = false;

  def getTypes = Nil;

  def transitTypes = Nil;
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

  def transitTypes = getTypes;

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

  def getTables: Seq[String] = {
    children.flatMap(_.getTables);
  }

  def transitTypes: Seq[BaseType] = {
    transform match {
      case binary if (Func_SetOperations.contains(binary)) => {
        val left = children(0).transitTypes;
        val right = children(1).transitTypes;
        if (left.isEmpty || right.isEmpty) {
          return Nil;
        } else {
          return left ++ right;
        }
      }
      case get if (isGetEntry(get)) => {
        val types = children(0).transitTypes;
        types.map(sub => {
          sub match {
            case struct: StructType => {
              struct.getSubType(getTypeSelector(get));
            }
            case prim: PrimitiveType => prim;
          }
        }).filter(_ != null);
      }
      case get if (isGetItem(get)) => {
        val types = children(0).transitTypes;
        types.map(sub => {
          sub match {
            case array: ArrayType => {
              array.getSubType(getTypeSelector(get).toInt);
            }
            case prim: PrimitiveType => prim;
          }
        })
      }
      case get if (isGetEntry(get)) => {
        val types = children(0).transitTypes;
        types.map(sub => {
          sub match {
            case map: MapType => {
              map.getSubType(getTypeSelector(get));
            }
            case prim: PrimitiveType => prim;
          }
        });
      }
      case extract if (MetaManager.isExtractOperation(extract)) => {
        val types = children(0).transitTypes;
        types.map(sub => {
          sub match {
            case comp: CompositeType => {
              val comptype = comp.getSubType(extract);
              if (comptype == null) {
                return Nil;
              } else {
                comptype;
              }
            }
            case _ => return Nil;
          }
        });
      }
      case _ => Nil;
    }
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

  def transitTypes = Nil;

  def getTables = Nil;
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

  def transitTypes = Nil;

  def getTables = children.flatMap(_.getTables);

}