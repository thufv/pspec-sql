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

  def isAttribute(attr: Expression) = AttributeClasses.exists(_.isInstance(attr));

  def isExtractOperation(expr: Expression): Boolean = MetaManager.isExtractOperation(expr.nodeName);

  def checkExtractOperation(expr: Expression, plan: LogicalPlan): Boolean = {
    //check whether the extract operation is valid (specified with meta labeling)
    val label = plan.childLabel(expr);
    return !label.transitTypes().isEmpty;

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
          val result = toItemString(getItem.ordinal, preType.getClass);
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

  def getAttributeTypes(expr: Expression, plan: LogicalPlan): Seq[Expression] = {
    val list = new ListBuffer[Expression];
    var current = expr;
    //the extract operation must appear at the top
    if (MetaManager.isExtractOperation(current.nodeName)) {
      //check the validity of the extract operation
      if (!checkExtractOperation(current, plan)) {
        return null;
      }
      list.append(current);
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
        case get: GetItem => {
          val ordinal = get.ordinal;
          ordinal match {
            case literal: Literal => {
              list.append(get);
              current = get.child;
            }
            case _ => {
              return Nil;
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
        case _ => return Nil;
      }
    }
    return list.reverse;
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

  def isComplexAttribute(attr: String): Boolean = attr.contains(Delimiter_Type);

  def getComplexAttribute(attr: String): String = {
    val index = attr.indexOf(Delimiter_Type);
    if (index < 0) {
      return attr;
    } else {
      return attr.substring(0, index);
    }
  }

  def splitComplexAttribute(attr: String): Array[(String, String)] = {
    val subs = attr.split(Delimiter_Type);
    val result = new Array[(String, String)](subs.length - 1);
    for (i <- 0 to result.length - 1) {
      val (pre, post) = subs.splitAt(i + 1);
      result(i) = (pre.mkString(Delimiter_Type), post.mkString(Delimiter_Type));
    }
    return result;
  }

  def getComplexSubtypes(attr: String): String = {
    val index = attr.indexOf(' ');
    if (index >= 0) {
      return attr.substring(index + 1);
    } else {
      return null;
    }
  }

  def concatComplexAttribute(attr: String, subs: String): String = {
    if (subs == null) {
      return attr;
    } else {
      return attr + " " + subs;
    }
  }

  def toFieldString(field: StructField): String = {
    return "." + field.name;
  }

  def toItemString(ordinal: Expression, clazz: Class[_ <: DataType]): String = {
    ordinal match {
      case literal: Literal => {
        literal.value match {
          case str: String => s"['$str']";
          case v => s"[$v]";
        }
        //        if (clazz == classOf[types.ArrayType]) {
        //          s"[${literal.value}]";
        //        } else {
        //          literal.value match {
        //            case str: String => "['" + str + "']"
        //            case _ => "[" + literal.value + "]";
        //          }
        //        }
      }
      case _ => {
        return null;
      }
    }
  }

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
            Seq(subType);
          } else {
            Nil;
          }
        } else {
          return struct.toPrimitives();
        }
      }
      case array: ArrayType => {
        if (isGetItem(transform)) {
          val index = getTypeSelector(transform);
          if (index == null || index.isEmpty()) {
            //unrecoganized get item operation, assume all subtypes are accessed 
            return array.toSubTypes();
          } else {
            val subType = array.getSubType(index.toInt);
            if (subType != null) {
              Seq(subType);
            } else {
              Nil;
            }
          }
        } else {
          return array.toPrimitives();
        }
      }
      case map: MapType => {
        if (isGetEntry(transform)) {
          val key = getTypeSelector(transform);
          if (key == null || key.isEmpty()) {
            return map.toSubTypes();
          }
          val subType = map.getSubType(key);
          if (subType != null) {
            Seq(subType);
          } else {
            Nil;
          }
        } else {
          return map.toPrimitives();
        }
      }
      case prim: PrimitiveType => Seq(prim);
    }
  }

  def getColumnString(attr: String): String = {
    def toColumnString(str: String): String = str.split("#")(0);

    if (isComplexAttribute(attr)) {
      val pre = getComplexAttribute(attr);
      val types = getComplexSubtypes(attr);
      return concatComplexAttribute(toColumnString(pre), types);
    } else {
      return toColumnString(attr);
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
      case get if (isSubtypeOperation(get)) => {
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

  /**
   * transform an attribute string to sql string
   */
  def toSQLString(attr: String): String = {
    val seq = attr.split(Delimiter_Type);
    if (seq.length == 1) {
      return seq(0);
    }

    if (MetaManager.isExtractOperation(seq.last)) {
      val extract = seq.last;
      val truncated = seq.dropRight(1).mkString("");
      return extract + "(" + truncated + ")";
    } else {
      return seq.mkString("");
    }

  }
}