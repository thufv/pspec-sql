package org.apache.spark.sql.catalyst.checker.util

import org.apache.spark.sql.catalyst.checker.LabelConstants._
import org.apache.spark.sql.catalyst.expressions.Alias
import org.apache.spark.sql.catalyst.expressions.Attribute
import org.apache.spark.sql.catalyst.expressions.Cast
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.catalyst.plans.logical.Aggregate
import org.apache.spark.sql.catalyst.trees.TreeNode
import edu.thu.ss.spec.meta.BaseType
import edu.thu.ss.spec.meta.CompositeType
import edu.thu.ss.spec.meta.StructType
import edu.thu.ss.spec.meta.ArrayType
import edu.thu.ss.spec.meta.MapType
import org.apache.spark.sql.catalyst.expressions.GetItem
import org.apache.spark.sql.catalyst.expressions.GetField
import scala.collection.mutable.ListBuffer
import org.apache.spark.sql.catalyst.expressions.GetItem
import org.apache.spark.sql.catalyst.expressions.BinaryArithmetic
import org.apache.spark.sql.catalyst.expressions.UnaryMinus
import org.apache.spark.sql.catalyst.expressions.Abs
import org.apache.spark.sql.catalyst.expressions.BinaryComparison
import org.apache.spark.sql.catalyst.expressions.In
import org.apache.spark.sql.catalyst.expressions.InSet
import org.apache.spark.sql.catalyst.expressions.GetItem
import org.apache.spark.sql.catalyst.checker.ExpressionRegistry
import org.apache.spark.sql.catalyst.expressions.Abs
import org.apache.spark.sql.catalyst.expressions.BinaryArithmetic
import org.apache.spark.sql.catalyst.expressions.BinaryComparison
import org.apache.spark.sql.catalyst.expressions.In
import org.apache.spark.sql.catalyst.expressions.InSet
import org.apache.spark.sql.catalyst.expressions.UnaryMinus
import org.apache.spark.sql.catalyst.checker.DataLabel
import org.apache.spark.sql.catalyst.checker.Insensitive
import org.apache.spark.sql.catalyst.checker.ConditionalLabel
import org.apache.spark.sql.catalyst.checker.ConstantLabel
import org.apache.spark.sql.catalyst.checker.Label
import org.apache.spark.sql.catalyst.checker.FunctionLabel
import com.microsoft.z3.BoolExpr
import com.microsoft.z3.Context
import com.microsoft.z3.Status
import scala.collection.Set
import scala.collection.mutable.Map
import scala.collection.mutable.HashMap
import org.apache.spark.sql.catalyst.checker.dp.Range
import scala.collection.mutable.HashSet
import com.microsoft.z3.Params
import org.apache.spark.sql.catalyst.checker.dp.TrackerStat
import edu.thu.ss.experiment.ExperimentConf

object AggregateType extends Enumeration {
  type AggregateType = Value
  val Direct_Aggregate, Derived_Aggregate, Invalid_Aggregate, Insensitive_Aggregate = Value
}

object CheckerUtil {

  def format(value: Double, digits: Int): String = {
    value.formatted(s"%.${digits}f")
  }

  def checkLabelType(label: Label): AggregateType.Value = {
    label match {
      case data: DataLabel => AggregateType.Direct_Aggregate;
      case cons: ConstantLabel => AggregateType.Direct_Aggregate;
      case in: Insensitive => AggregateType.Direct_Aggregate;

      case cond: ConditionalLabel => AggregateType.Direct_Aggregate;

      case func: FunctionLabel => {
        val types = func.children.map(checkLabelType(_));
        if (types.exists(_ == AggregateType.Invalid_Aggregate)) {
          return AggregateType.Invalid_Aggregate;
        }
        func.transform match {
          case Func_Cast => {
            return checkLabelType(func.children(0));
          }
          case Func_Union | Func_Except | Func_Intersect => {
            if (types.forall(_ == AggregateType.Direct_Aggregate)) {
              return AggregateType.Direct_Aggregate;
            } else {
              return AggregateType.Derived_Aggregate;
            }
          }
          case Func_Sum | Func_Count | Func_Min | Func_Max | Func_Avg => {
            return AggregateType.Derived_Aggregate;
          }
          case _ => {
            if (types.exists(_ != AggregateType.Derived_Aggregate)) {
              return AggregateType.Invalid_Aggregate;
            } else {
              return AggregateType.Derived_Aggregate;
            }
          }
        }
      }
      case _ => null;
    }
  }

  def exists[T <: TreeNode[T]](node: TreeNode[T], clazz: Class[_ <: T]): Boolean = {
    if (clazz.isInstance(node)) {
      return true;
    }
    return node.children.exists(exists(_, clazz));
  }

  def collect(expr: Expression, clazz: Class[_ <: Expression]): Seq[Expression] = {
    if (clazz.isInstance(expr)) {
      expr.children.flatMap(collect(_, clazz));
    } else {
      List(expr);
    }
  }

  def collect(label: Label, transform: String): Seq[Label] = {
    label match {
      case func: FunctionLabel if (func.transform == transform) => {
        func.children.flatMap(collect(_, transform));
      }
      case _ => List(label);
    }
  }

  var context: Context = null;

  private lazy val params = {
    val params = context.mkParams;
    val timeout = System.getProperty("z3.timeout", ExperimentConf.Max_Solve_Time).toInt;
    params.add("soft_timeout", timeout);
    params.add("solver2_timeout", timeout);
    params;
  }

  private lazy val solver = context.mkSolver();

  def satisfiable(constraint: BoolExpr): Boolean = {
    TrackerStat.get.beginTiming(TrackerStat.Z3_Time);

    solver.setParameters(params);
    solver.add(constraint);
    val result = solver.check();
    if (result == Status.UNKNOWN) {
      println("warning: z3 solver gives unknown result");
    }
    val sat = result == Status.SATISFIABLE;
    solver.reset();

    TrackerStat.get.endTiming(TrackerStat.Z3_Time);
    return sat;
  }

  def intersect[T](set1: Set[T], set2: Set[T]): Boolean = {
    set1.foreach(value => {
      if (set2.contains(value)) {
        return true;
      }
    });
    return false;
  }

  def containsAll[T](set1: Set[T], set2: Set[T]): Boolean = {
    set2.foreach(value => {
      if (!set1.contains(value)) {
        return false;
      }
    });
    return true;
  }

  private val SupportedArithms = List(classOf[BinaryArithmetic], classOf[UnaryMinus], classOf[Abs]);

  private val SupportedPredicates = List(classOf[BinaryComparison], classOf[In], classOf[InSet]);

  def supportArithmetic(expr: Expression) = SupportedArithms.exists(_.isInstance(expr));

  def supportPredicate(expr: Expression) = SupportedPredicates.exists(_.isInstance(expr));

  def asType[T](value: Any, clazz: Class[T]): T = {
    if (value != null && clazz.isInstance(value)) {
      return clazz.cast(value);
    } else {
      return null.asInstanceOf[T];
    }
  }

  def conjuncate(ranges1: Map[String, Range], ranges2: Map[String, Range]): Map[String, Range] = {
    val result = new HashMap[String, Range];
    ranges1.foreach(t => {
      val attr = t._1;
      val range1 = t._2;
      val range2 = ranges2.getOrElse(attr, null);
      result.put(attr, range1.intersect(range2));
    });
    ranges2.foreach(t => {
      val attr = t._1;
      val range2 = t._2;
      val range1 = ranges1.getOrElse(attr, null);
      result.put(attr, range2.intersect(range1));
    });
    return result;
  }

  def disjuncate(ranges1: Map[String, Range], ranges2: Map[String, Range]): Map[String, Range] = {
    val result = new HashMap[String, Range];
    ranges1.foreach(t => {
      val column = t._1;
      val range1 = t._2;
      val range2 = ranges2.getOrElse(column, null);
      if (range2 != null) {
        result.put(column, range1.union(range2));
      }
    });
    return result;
  }

  def toInt(value: Any): Int = {
    value match {
      case long: Long => long.toInt;
      case int: Int => int;
      case double: Double => double.toInt;
      case float: Float => float.toInt;
      case short: Short => short.toInt;
      case big: BigDecimal => big.toInt;
      case null => null.asInstanceOf[Int];
      case _ => throw new RuntimeException(s"invalid argument: $value.");
    }
  }
  def toDouble(value: Any): Double = {
    value match {
      case long: Long => long.toDouble;
      case int: Int => int.toDouble;
      case double: Double => double.toDouble;
      case float: Float => float.toDouble;
      case short: Short => short.toDouble;
      case big: BigDecimal => big.toDouble;
      case str: String => str.toDouble;
      case null => null.asInstanceOf[Double];
      case _ => throw new RuntimeException(s"invalid argument: $value.");
    }
  }
}