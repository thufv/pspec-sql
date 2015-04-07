package org.apache.spark.sql.catalyst.checker.util

import org.apache.spark.sql.catalyst.checker.FunctionLabel
import org.apache.spark.sql.catalyst.checker.LabelConstants._
import org.apache.spark.sql.catalyst.expressions._
import scala.collection.mutable.ListBuffer
import org.apache.spark.sql.catalyst.checker.ExpressionRegistry
import edu.thu.ss.spec.meta.BaseType
import edu.thu.ss.spec.meta.ArrayType
import org.apache.spark.sql.catalyst.checker.Label
import edu.thu.ss.spec.meta.StructType
import edu.thu.ss.spec.meta.CompositeType
import edu.thu.ss.spec.meta.MapType

object TypeUtil {

  private val AttributeClasses = List(classOf[Attribute], classOf[GetField], classOf[GetItem]);

  def isAttribute(attr: Expression) = AttributeClasses.exists(_.isInstance(attr));

  def resolveSimpleAttribute(expr: Expression): Expression = {
    expr match {
      case attr if (isAttribute(attr)) => attr;
      case alias: Alias => {
        resolveSimpleAttribute(alias.child);
      }
      case cast: Cast => {
        resolveSimpleAttribute(cast.child);
      }
      case _ => expr.asInstanceOf[Attribute];
    }
  }

  /**
   * one attribute, followed by several get expressions
   */
  private def getAttributeString(exprs: Seq[Expression]): String = {
    val sb = new StringBuilder;
    val subTypes = exprs.foreach(expr => {
      expr match {
        case getItem: GetItem => {
          val child = getItem.child;
          val ordinal = getItem.ordinal;
          ordinal match {
            case literal: Literal => {
              sb.append("[");
              sb.append(literal.toString);
              sb.append("]");
            }
            case _ => {
              return null;
            }
          }
        }
        case getField: GetField => {
          val field = getField.field;
          sb.append(".");
          sb.append(field.name);
        }
        case _ => throw new IllegalArgumentException(s"unsupported expression:$expr, only GetItem and GetField are allowed");
      }
    });
    return sb.toString;
  }

  def getAttributeTypes(expr: Expression): Seq[Expression] = {
    val list = new ListBuffer[Expression];
    var current = expr;
    while (current != null) {
      current match {
        case get: GetItem => {
          val child = get.child;
          val ordinal = get.ordinal;
          ordinal match {
            case literal: Literal => {
              list.append(literal);
            }
            case _ => {
              return null;
            }
          }
        }
        case get: GetField => {
          list.append(get);
          current = get.child;
        }
        case attribute: Attribute => {
          list.append(attribute);
          current = null;
        }
        case _ => throw new IllegalArgumentException(s"unsupported expression:$expr, only subtype and attribute expressions are allowed");
      }
    }
    return list.reverse;
  }

  /**
   * a tree of some get expressions and ends in a attribute
   */
  def getAttributeString(expr: Expression): String = {

    if (expr.isInstanceOf[Attribute]) {
      return expr.toString;
    }

    val list = getAttributeTypes(expr);
    if (list != null) {
      return getAttributeString(list);
    } else {
      return null;
    }
  }

  def isComplexAttribute(attr: String): Boolean = attr.contains(" ");

  def getComplexAttribute(attr: String) = attr.split(" ")(0);

  def getComplexSubtypes(attr: String): String = {
    val index = attr.indexOf(' ');
    if (index >= 0) {
      return attr.substring(index + 1);
    } else {
      return "";
    }
  }

  def concatComplexAttribute(attr: String, subs: String) = attr + " " + subs;

  def resolveType(t: BaseType, func: FunctionLabel): Seq[BaseType] = {
    val transform = func.transform;
    if (ignorable(transform)) {
      return Seq(t);
    }
    t match {
      case comp: CompositeType => {
        val subType = comp.getExtractOperation(transform);
        if (subType != null) {
          return Seq(subType.getType());
        } else {
          return comp.toPrimitives();
        }
      }
      case struct: StructType => {
        if (isGetField(transform)) {
          val field = getSubType(transform);
          val subType = struct.getField(field);
          if (subType != null) {
            Seq(subType.getType());
          } else {
            Nil;
          }
        } else {
          return struct.toPrimitives();
        }
      }
      case array: ArrayType => {
        if (isGetItem(transform)) {
          return Seq(array.getItemType());
        } else {
          return array.toPrimitives();
        }
      }
      case map: MapType => {
        if (isGetEntry(transform)) {
          val key = getSubType(transform);
          val subType = map.getEntry(key);
          if (subType != null) {
            Seq(subType.getType);
          } else {
            Nil;
          }
        } else {
          return map.toPrimitives();
        }
      }
      case _ => Seq(t);
    }
  }

  def concatComplexLabel(label: Label, subtypes: Seq[Expression]): Label = {
    var current = label;
    subtypes.foreach(sub => {
      sub match {
        case _: GetItem | _: GetField => {
          val func = ExpressionRegistry.resolveFunction(sub);
          current = FunctionLabel(Seq(current), func, sub);
        }
        case _ => {
          throw new IllegalArgumentException(s"unsupport expression $sub, only GetItem and GetField are allowed.");
        }
      }
    });

    return current;
  }

  /**
   * for aggregated attributes, transform up set operators (union, intersect, and except)
   */
  def transformComplexLabel(label: Label): Label = {
    if (!label.isInstanceOf[FunctionLabel]) {
      return label;
    }

    val func = label.asInstanceOf[FunctionLabel];

    func.transform match {
      case set if (Func_SetOperations.contains(set)) => {
        val children = func.children.map(transformComplexLabel(_));
        return FunctionLabel(children, set, null);
      }
      case get if (isSubtype(get)) => {
        val child = transformComplexLabel(func.children(0));
        child match {
          case cfunc: FunctionLabel => {
            if (Func_SetOperations.contains(cfunc.transform)) {
              //transform up
              val left = cfunc.children(0);
              val right = cfunc.children(1);
              val seq = Seq(transformComplexLabel(FunctionLabel(Seq(left), get, null)),
                transformComplexLabel(FunctionLabel(Seq(right), get, null)));
              FunctionLabel(seq, cfunc.transform, null);
            } else {
              FunctionLabel(Seq(child), get, null);
            }
          }
          case _ => {
            FunctionLabel(Seq(child), get, null);
          }
        }
      }
    }
  }
}