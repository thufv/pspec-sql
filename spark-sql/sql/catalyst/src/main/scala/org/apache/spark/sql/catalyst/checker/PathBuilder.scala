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
import scala.collection
import scala.collection.mutable.HashMap
import scala.collection.mutable.Map
import scala.collection.mutable.HashSet
import scala.collection.mutable.Set

case class Path(val ops: Seq[DesensitizeOperation]);

/**
 * used to build paths from a set of lineage trees
 */
class PathBuilder {

  /**
   * path is essential a list of desensitize operations
   */

  def apply(projections: collection.Set[Label], conditions: collection.Set[Label]): (Map[Policy, Map[DataCategory, Set[Path]]], Map[Policy, Map[DataCategory, Set[Path]]]) = {
    val projectionPaths: Map[Policy, Map[DataCategory, Set[Path]]] = new HashMap;
    val conditionPaths: Map[Policy, Map[DataCategory, Set[Path]]] = new HashMap;

    //first calculate all paths for data categories
    projections.foreach(buildPath(_, projectionPaths));
    conditions.foreach(buildPath(_, conditionPaths));

    printPaths(projections, conditions, projectionPaths, conditionPaths);

    (projectionPaths, conditionPaths);
  }

  /**
   * should be turn off in production
   */
  private def printPaths(projections: collection.Set[Label], conditions: collection.Set[Label], projectionPaths: Map[Policy, Map[DataCategory, Set[Path]]], conditionPaths: Map[Policy, Map[DataCategory, Set[Path]]]) {

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
    println("\nprojection paths:");
    projectionPaths.foreach(p => {
      p._2.foreach(
        t => t._2.foreach(path => println(s"${t._1}\t$path")))
    })

    println("\ncondition paths:");

    conditionPaths.foreach(p => {
      p._2.foreach(
        t => t._2.foreach(path => println(s"${t._1}\t$path")))
    })

  }

  /**
   * build paths for each data category recursively.
   */
  private def buildPath(label: Label, paths: Map[Policy, Map[DataCategory, Set[Path]]], list: ListBuffer[String] = new ListBuffer): Unit = {
    label match {
      case data: DataLabel => {
        resolvePaths(data.labelType, data, list, paths);
      }
      case cond: ConditionalLabel => {
        cond.fulfilled.foreach(labelType => {
          resolvePaths(labelType, cond, list, paths);
        });
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

  /**
   * first calculate real data categories for data types
   * then map transformations to real data categories.
   */
  private def resolvePaths(labelType: BaseType, label: ColumnLabel, transforms: ListBuffer[String], paths: Map[Policy, Map[DataCategory, Set[Path]]]): Unit = {

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
          if (it.hasNext && it.next == LabelConstants.Func_GetItem) {
            curType = array.getItemType();
            matched = true;
          }
        }
        case struct: StructType => {
          if (it.hasNext) {
            val transform = it.next;
            if (transform.startsWith(LabelConstants.Func_GetField)) {
              val field = transform.split("\\.")(1);
              curType = struct.getFieldType(field);
              matched = true;
            }
          }
        }
        case map: MapType => {
          if (it.hasNext) {
            val transform = it.next;
            if (transform.startsWith(LabelConstants.Func_GetEntry)) {
              val key = transform.split("\\.")(1);
              curType = map.getEntryType(key);
              matched = true;
            }
          }
        }
        case composite: CompositeType => {
          if (it.hasNext) {
            val transform = it.next;
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
    primitives.foreach(addPath(_, dropped, paths, policy));
  }

  /**
   * first calculate real data categories for data types
   * then map transformations to real data categories.
   */
  private def addPath(primitive: PrimitiveType, transforms: ListBuffer[String], paths: Map[Policy, Map[DataCategory, Set[Path]]], policy: Policy): Unit = {
    val ops = transforms.map(lookupOperation(primitive, _)).filter(_ != null);
    val map = paths.getOrElseUpdate(policy, new HashMap[DataCategory, Set[Path]]);
    map.getOrElseUpdate(primitive.getDataCategory(), new HashSet[Path]).add(Path(ops));
  }

  private def lookupOperation(primitive: PrimitiveType, transform: String): DesensitizeOperation = {
    var operation: DesensitizeOperation = primitive.getDesensitizeOperation(transform);
    if (operation != null) {
      return operation;
    } else {
      return primitive.getDataCategory().getOperation(transform);
    }
  }

}