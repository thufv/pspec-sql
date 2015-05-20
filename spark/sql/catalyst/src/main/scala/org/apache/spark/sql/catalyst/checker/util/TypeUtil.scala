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
import edu.thu.ss.spec.global.MetaManager
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.checker.ColumnLabel
import edu.thu.ss.spec.meta.PrimitiveType
import org.apache.spark.sql.catalyst.expressions.GetField
import org.apache.spark.sql.types
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.DataType

object TypeUtil {

  private val AttributeClasses = List(classOf[Attribute], classOf[GetField], classOf[GetItem]);

  private val Delimiter_Type = " ";
  private val Delimiter_Attr = "#";

  def isGetItem(trans: String) = trans.startsWith(Func_GetItem);

  def isGetField(trans: String) = trans.startsWith(Func_GetField);

  def isGetEntry(trans: String) = trans.startsWith(Func_GetEntry);

  def getTypeSelector(trans: String): String = {
    val strs = trans.split("\\.");
    if (strs.length > 1) {
      strs(1);
    } else {
      null;
    }
  }

  def isGetOperation(trans: String) = isGetItem(trans) || isGetField(trans) || isGetEntry(trans) || MetaManager.isExtractOperation(trans);

  def isAttribute(attr: Expression) = AttributeClasses.exists(_.isInstance(attr)) || isExtractOperation(attr);

  def isExtractOperation(expr: Expression): Boolean = MetaManager.isExtractOperation(expr.nodeName);

  def isValidExtractOperation(expr: Expression, plan: LogicalPlan): Boolean = {
    val label = plan.childLabel(expr);
    return isValidExtractOperation(label);
  }

  def isValidExtractOperation(label: Label): Boolean = {
    //check whether the extract operation is valid (specified with meta labeling)
    return !label.transitTypes().isEmpty;
  }

  def isStoredAttribute(expr: Expression, plan: LogicalPlan): Boolean = {
    val attribute = resolveSimpleAttribute(expr);
    if (attribute == null) {
      return false;
    }
    val label = plan.childLabel(attribute);
    return label.isStoredAttribute();
  }

  def resolveLiteral(expr: Expression): Literal = {
    expr match {
      case cast: Cast => resolveLiteral(cast.child);
      case literal: Literal => literal;
      case MutableLiteral(value, dataType, _) => Literal(value, dataType);
      case _ => null;
    }
  }

  def resolveSimpleAttribute(expr: Expression): Expression = {
    expr match {
      case attr if (isAttribute(attr)) => attr;
      case alias: Alias => {
        resolveSimpleAttribute(alias.child);
      }
      case cast: Cast => {
        resolveSimpleAttribute(cast.child);
      }
      case _ => null;
    }
  }

  /**
   * whether an attribute string represents a complex attribute
   */
  def isComplexAttribute(attr: String): Boolean = attr.contains(Delimiter_Type);

  /**
   * get the attribute part from an attribute string
   */
  def getComplexAttribute(attr: String): String = {
    val index = attr.indexOf(Delimiter_Type);
    if (index < 0) {
      return attr;
    } else {
      return attr.substring(0, index);
    }
  }
  /**
   * get all subtypes from an attribute string
   */
  def getComplexSubtypes(attr: String): String = {
    val index = attr.indexOf(Delimiter_Type);
    if (index >= 0) {
      return attr.substring(index + 1);
    } else {
      return null;
    }
  }

  /**
   * split an attribute string into pairs of prefix and postfix
   */
  def splitComplexAttribute(attr: String): Array[(String, String)] = {
    val subs = attr.split(Delimiter_Type);
    val result = new Array[(String, String)](subs.length - 1);
    for (i <- 0 to result.length - 1) {
      val (pre, post) = subs.splitAt(i + 1);
      result(i) = (pre.mkString(Delimiter_Type), post.mkString(Delimiter_Type));
    }
    return result;
  }

  /**
   * concatenate an attribute string with sub type operations
   */
  def concatComplexAttribute(attr: String, subs: String): String = {
    if (subs == null || subs.isEmpty) {
      return attr;
    } else {
      return attr + Delimiter_Type + subs;
    }
  }

  /**
   * transform an column string to sql string
   */
  def toSQLString(str: String): String = {
    def toSQLColumn(str: String) = {
      str match {
        case field if (field.startsWith(".")) => s".`${field.substring(1)}`";
        case item if (item.startsWith("[")) => item;
        case attr => s"`$attr`";
      }
    }

    val seq = str.split(Delimiter_Type);
    if (seq.length == 1) {
      return toSQLColumn(str);
    }
    if (MetaManager.isExtractOperation(seq.last)) {
      val extract = seq.last;
      val truncated = seq.dropRight(1).map(toSQLColumn(_)).mkString("");
      return extract + "(" + truncated + ")";
    } else {
      return seq.map(toSQLColumn(_)).mkString("");
    }

  }
  /**
   * a tree of some get expressions and ends in a attribute
   */
  def getAttributeString(expr: Expression, plan: LogicalPlan): String = {
    if (expr == null) {
      return null;
    }
    if (expr.isInstanceOf[Attribute]) {
      return expr.toString;
    }
    val list = getAttributeTypes(expr, plan);
    if (!list.isEmpty) {
      return toAttributeString(list);
    } else {
      return null;
    }
  }

  /**
   * transform an attribute to the column string
   */
  def getColumnString(attr: String): String = {
    def toColumnString(str: String): String = str.split(Delimiter_Attr)(0);

    if (isComplexAttribute(attr)) {
      val pre = getComplexAttribute(attr);
      val types = getComplexSubtypes(attr);
      return concatComplexAttribute(toColumnString(pre), types);
    } else {
      return toColumnString(attr);
    }
  }

  def getAttributeTypes(expr: Expression, plan: LogicalPlan): Seq[Expression] = {
    val list = new ListBuffer[Expression];
    var current = expr;
    //the extract operation must appear at the top
    if (MetaManager.isExtractOperation(current.nodeName)) {
      //check the validity of the extract operation
      if (isValidExtractOperation(current, plan)) {
        list.append(current);
      }
      current = current.children(0);
      if (current.isInstanceOf[Cast]) {
        current = current.asInstanceOf[Cast].child;
      }
    }

    while (current != null) {
      current match {
        case alias: Alias => {
          current = alias.child;
        }
        case attribute: Attribute => {
          list.append(attribute);
          current = null;
        }
        case get: GetItem => {
          val ordinal = get.ordinal;
          ordinal match {
            case literal: Literal => {
              list.append(get);
              current = get.child;
            }
            case _ => {
              //unrecognized ordinal
              return Nil;
            }
          }
        }
        case get: GetField => {
          list.append(get);
          current = get.child;
        }
        case _ => return Nil;
      }
    }
    return list.reverse;
  }

  /**
   * one attribute, followed by several get expressions
   */
  def toAttributeString(exprs: Seq[Expression]): String = {
    var preType: DataType = null
    exprs.map(expr => {
      val string = expr match {
        case attr: Attribute => {
          attr.toString;
        }
        case getItem: GetItem => {
          val result = toItemString(getItem.ordinal);
          if (result == null) {
            return null;
          } else {
            result;
          }
        }
        case getField: GetField => {
          toFieldString(getField.field);
        }
        case _ => {
          if (isExtractOperation(expr)) {
            expr.nodeName;
          } else {
            return null;
          }
        }
      }
      preType = expr.dataType;
      string;
    }).mkString(Delimiter_Type);
  }

  /**
   * get attribute string from a lineage tree
   */
  def getLabelString(label: Label): String = {
    if (label == null) {
      return null;
    }
    if (label.isInstanceOf[ColumnLabel]) {
      return label.asInstanceOf[ColumnLabel].attr.toString;
    }

    val list = new ListBuffer[String];
    var current = label;
    //the extract operation must appear at the top
    if (label.isInstanceOf[FunctionLabel]) {
      val function = label.asInstanceOf[FunctionLabel];
      if (MetaManager.isExtractOperation(function.transform)) {
        //check the validity of the extract operation
        if (isValidExtractOperation(label)) {
          list.append(function.transform);
        }
        current = function.children(0);
      }
    }

    while (current != null) {
      current match {
        case column: ColumnLabel => {
          list.append(column.attr.toString());
          current = null;
        }
        case func: FunctionLabel => {
          val get = func.transform;
          if (!isGetOperation(get)) {
            return null;
          }
          if (getTypeSelector(get) == null) {
            return null;
          }
          get match {
            case getItem if (isGetItem(getItem)) => {
              val expr = func.expression.asInstanceOf[GetItem];
              list.append(toItemString(expr.ordinal));
            }
            case getField if (isGetField(getField)) => {
              val expr = func.expression.asInstanceOf[GetField];
              list.append(toFieldString(expr.field));
            }
            case getEntry if (isGetEntry(getEntry)) => {
              val expr = func.expression.asInstanceOf[GetItem];
              list.append(toItemString(expr.ordinal));
            }
          }
          current = func.children(0);
        }
        case _ => return null;
      }
    }
    return list.reverse.mkString(Delimiter_Type);
  }

  def toFieldString(field: StructField): String = {
    return "." + field.name;
  }

  def toItemString(ordinal: Expression): String = {
    ordinal match {
      case literal: Literal => {
        literal.value match {
          case str: String => s"['$str']";
          case v => s"[$v]";
        }
      }
      case _ => {
        return null;
      }
    }
  }

  /**
   * resolve all subtypes given a base type t and a function func
   */
  def resolveType(t: BaseType, func: FunctionLabel): Seq[BaseType] = {
    val transform = func.transform;
    if (ignorable(transform)) {
      return Seq(t);
    }
    t match {
      case comp: CompositeType => {
        val subType = comp.getSubType(transform);
        if (subType != null) {
          return Seq(subType);
        } else {
          return comp.toPrimitives();
        }
      }
      case struct: StructType => {
        if (isGetField(transform)) {
          val field = getTypeSelector(transform);
          val subType = struct.getSubType(field);
          if (subType != null) {
            return Seq(subType);
          } else {
            return Nil;
          }
        } else {
          return struct.toPrimitives();
        }
      }
      case array: ArrayType => {
        if (isGetItem(transform)) {
          val index = getTypeSelector(transform);
          if (index == null) {
            //unrecognized get item operation, assume all subtypes are accessed 
            return array.toSubTypes();
          } else {
            val subType = array.getSubType(index.toInt);
            if (subType != null) {
              return Seq(subType);
            } else {
              return Nil;
            }
          }
        } else {
          return array.toPrimitives();
        }
      }
      case map: MapType => {
        if (isGetEntry(transform)) {
          val key = getTypeSelector(transform);
          if (key == null) {
            return map.toSubTypes();
          }
          val subType = map.getSubType(key);
          if (subType != null) {
            return Seq(subType);
          } else {
            return Nil;
          }
        } else {
          return map.toPrimitives();
        }
      }
      case prim: PrimitiveType => Seq(prim);
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
        return FunctionLabel(children, set, func.expression);
      }
      case get if (isGetOperation(get)) => {
        val child = transformComplexLabel(func.children(0));
        child match {
          case cfunc: FunctionLabel => {
            if (Func_SetOperations.contains(cfunc.transform)) {
              //transform up
              val left = cfunc.children(0);
              val right = cfunc.children(1);
              val seq = Seq(transformComplexLabel(FunctionLabel(Seq(left), get, func.expression)),
                transformComplexLabel(FunctionLabel(Seq(right), get, func.expression)));
              FunctionLabel(seq, cfunc.transform, cfunc.expression);
            } else {
              FunctionLabel(Seq(child), get, func.expression);
            }
          }
          case _ => {
            FunctionLabel(Seq(child), get, func.expression);
          }
        }
      }
    }
  }

}