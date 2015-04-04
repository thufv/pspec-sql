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

case class Path(func: Function, path: Seq[Function], op: DesensitizeOperation) {

  override def toString(): String = {
    op.toString();
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
  private def buildPath(label: Label, action: Action, list: ListBuffer[Function] = new ListBuffer): Unit = {
    label match {
      case data: DataLabel => {
        resolvePaths(data.labelType, data, action, list);
      }
      case cond: ConditionalLabel => {
        cond.fulfilled.foreach(labelType => {
          resolvePaths(labelType, cond, action, list);
        });
      }
      case func: Function => {
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
  private def resolvePaths(labelType: BaseType, label: ColumnLabel, action: Action, transforms: ListBuffer[Function]): Unit = {

    val meta = MetaManager.get(label.database, label.table);
    val policy = meta.getPolicy();

    var data: DataCategory = null;

    var it = transforms.iterator;
    var i = 0;
    var curType = labelType;
    var matched = true;
    while (curType != null && matched) {
      matched = false;
      curType match {
        case array: ArrayType => {
          if (it.hasNext && it.next.udf == LabelConstants.Func_GetItem) {
            curType = array.getItemType();
            matched = true;
          }
        }
        case struct: StructType => {
          if (it.hasNext) {
            val transform = it.next.udf;
            if (transform.startsWith(LabelConstants.Func_GetField)) {
              val field = transform.split("\\.")(1);
              curType = struct.getFieldType(field);
              matched = true;
            }
          }
        }
        case map: MapType => {
          if (it.hasNext) {
            val transform = it.next.udf;
            if (transform.startsWith(LabelConstants.Func_GetEntry)) {
              val key = transform.split("\\.")(1);
              curType = map.getEntryType(key);
              matched = true;
            }
          }
        }
        case composite: CompositeType => {
          if (it.hasNext) {
            val transform = it.next.udf;
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
  }

  /**
   * first calculate real data categories for data types
   * then map transformations to real data categories.
   */
  private def addPath(primitive: PrimitiveType, transforms: ListBuffer[Function], action: Action, policy: Policy): Unit = {

    val set = flows.getOrElseUpdate(policy, new HashSet[Flow]);

    transforms.foreach(tran => {
      if (!Func_SetOperations.contains(tran.udf)) {
        val op = getOperation(primitive, tran.udf);
        val flow = Flow(action, primitive.getDataCategory, Path(tran, transforms.toList, op));
        set.add(flow);
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