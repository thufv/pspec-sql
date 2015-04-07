package org.apache.spark.sql.catalyst.checker

import edu.thu.ss.spec.lang.pojo.DataCategory
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation
import edu.thu.ss.spec.lang.pojo.Policy
import edu.thu.ss.spec.global.MetaManager
import scala.collection.mutable.ListBuffer
import edu.thu.ss.spec.meta.BaseType
import edu.thu.ss.spec.meta.ArrayType
import edu.thu.ss.spec.meta.StructType
import edu.thu.ss.spec.meta.PrimitiveType
import edu.thu.ss.spec.meta.CompositeType
import edu.thu.ss.spec.meta.MapType
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import edu.thu.ss.spec.lang.pojo.Action
import org.apache.spark.sql.catalyst.checker.LabelConstants._
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._

case class Path(func: FunctionLabel, op: DesensitizeOperation, transforms: Seq[FunctionLabel]) extends Equals {

  override def toString(): String = {
    if (op != null) {
      op.toString();
    } else {
      "NULL";
    }
  }

  def canEqual(other: Any) = {
    other.isInstanceOf[org.apache.spark.sql.catalyst.checker.Path]
  }

  override def equals(other: Any) = {
    other match {
      case that: org.apache.spark.sql.catalyst.checker.Path => that.canEqual(Path.this) && func == that.func && op == that.op
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    var result = 1;
    val hfunc = if (func != null) func.hashCode else 0;
    result = prime * result + hfunc;
    val hop = if (op != null) op.hashCode() else 0;
    result = prime * result + hop;
    result;
  }
}

case class Flow(action: Action, data: DataCategory, path: Path) {

}

/**
 * used to build paths from a set of lineage trees
 */
class PathBuilder {

  private val flows = new HashMap[Policy, Set[Flow]];

  /**
   * path is essential a list of desensitize operations
   */

  def apply(projections: Set[Label], conditions: Set[Label]): Map[Policy, Set[Flow]] = {
    //first calculate all paths for data categories
    projections.foreach(label => buildPath(label, Action.Projection));
    conditions.foreach(label => buildPath(label, Action.Condition));

    printPaths(projections, conditions, flows);

    return flows;
  }

  /**
   * should be turn off in production
   */
  private def printPaths(projections: Set[Label], conditions: Set[Label], flow: Map[Policy, Set[Flow]]) {

    println("projections:");
    projections.foreach(t => {
      println(s"$t");
    });
    println();
    println("conditions:")
    conditions.foreach(t => {
      println(t);
    });
    println();
    println("data category paths:");
    flows.foreach(p => { p._2.foreach(println(_)) });
  }

  /**
   * build paths for each data category recursively.
   */
  private def buildPath(label: Label, action: Action, list: ListBuffer[FunctionLabel] = new ListBuffer): Unit = {
    label match {
      case data: DataLabel => {
        resolvePaths(data.labelType, data, action, list);
      }
      case cond: ConditionalLabel => {
        cond.fulfilled.foreach(labelType => {
          resolvePaths(labelType, cond, action, list);
        });
      }
      case func: FunctionLabel => {
        list.prepend(func);
        func.children.foreach(buildPath(_, action, list));
        list.remove(0);
      }
      case pred: PredicateLabel => {
        pred.children.foreach(buildPath(_, action, list));
      }
      case _ =>
    }
  }

  /**
   * first calculate real data categories for data types
   * then map transformations to real data categories.
   */
  private def resolvePaths(labelType: BaseType, label: ColumnLabel, action: Action, transforms: ListBuffer[FunctionLabel]): Unit = {

    val meta = MetaManager.get(label.database, label.table);
    val policy = meta.getPolicy();

    val index = transforms.indexWhere(func => !skippable(func.transform));
    val (skipped, left) = transforms.splitAt(index);

    var types = Seq(labelType);
    skipped.foreach(func => {
      types = types.flatMap(resolveType(_, func));
    });
    val primitives = types.flatMap(_.toPrimitives());
    primitives.foreach(addPath(_, left, action, policy));

    /*
    var data: DataCategory = null;

    var it = transforms.iterator;
    var i = 0;
    var curType = labelType;
    var matched = true;

    while (curType != null && matched) {
      matched = false;
      curType match {
        case array: ArrayType => {
          if (it.hasNext) {
            val transform = it.next.transform;
            if (isGetItem(transform)) {
              curType = array.getItemType();
              matched = true;
            }
          }
        }
        case struct: StructType => {
          if (it.hasNext) {
            val transform = it.next.transform;
            if (isGetField(transform)) {
              val field = getSubType(transform);
              curType = struct.getFieldType(field);
              matched = true;
            }
          }
        }
        case map: MapType => {
          if (it.hasNext) {
            val transform = it.next.transform;
            if (isGetEntry(transform)) {
              val key = getSubType(transform);
              curType = map.getEntryType(key);
              matched = true;
            }
          }
        }
        case composite: CompositeType => {
          if (it.hasNext) {
            val transform = it.next.transform;
            curType = composite.getExtractOperation(transform).getType();
            matched = true;
          }
        }
        case _ =>
      }
      if (matched) {
        i += 1;
      }
    }
    if (curType == null) {
      return ;
    }
    val dropped = if (i > 0) {
      transforms.drop(i);
    } else {
      transforms;
    }
    val primitives = curType.toPrimitives();
    primitives.foreach(addPath(_, dropped, action, policy));
  	*/
  }

  /**
   * first calculate real data categories for data types
   * then map transformations to real data categories.
   */
  private def addPath(primitive: PrimitiveType, transforms: ListBuffer[FunctionLabel], action: Action, policy: Policy): Unit = {

    val set = flows.getOrElseUpdate(policy, new HashSet[Flow]);

    transforms.foreach(tran => {
      val op = getOperation(primitive, tran.transform);
      if (op != null) {
        set.add(Flow(action, primitive.getDataCategory, Path(tran, op, transforms.toList)));
      } else {
        set.add(Flow(action, primitive.getDataCategory, Path(null, null, transforms.toList)));
      }
    });
  }

  private def getOperation(primitive: PrimitiveType, transform: String): DesensitizeOperation = {
    val operation: DesensitizeOperation = primitive.getDesensitizeOperation(transform);
    if (operation != null) {
      return operation;
    } else {
      return primitive.getDataCategory().getOperation(transform);
    }
  }

}