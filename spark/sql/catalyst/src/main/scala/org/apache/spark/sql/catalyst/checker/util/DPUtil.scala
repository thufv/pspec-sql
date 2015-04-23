package org.apache.spark.sql.catalyst.checker.util

import scala.math.BigDecimal
import scala.util.Random
import org.apache.spark.Logging
import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.checker.SparkChecker
import org.apache.spark.sql.catalyst.checker.PrivacyException

object DPUtil extends Logging {

  val Key_Epsilon = "epsilon";

  val Default_Epsilon = "0.1";

  def laplace(stddev: Double): Double = {
    val uniform = Random.nextDouble() - 0.5;
    return stddev * Math.signum(uniform) * Math.log(1 - 2.0 * Math.abs(uniform));
  }

  def uniform(low: Double, high: Double): Double = {
    return low + (high - low) * Random.nextDouble();
  }

  def lapNoise(epsilon: Double, sensitivity: Double): Double = {
    return laplace(sensitivity / epsilon);
  }

  def supported(agg: AggregateExpression): Boolean = {
    agg match {
      case _: Sum => true;
      case _: SumDistinct => true;
      case _: Count => true;
      case _: CountDistinct => true;
      case _: Min => true;
      case _: Max => true;
      case _: Average => true;
      case _ => false;
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

  def min(v1: Any, v2: Any): Any = {
    val d1 = DPUtil.toDouble(v1);
    val d2 = DPUtil.toDouble(v2);
    if (d1 <= d2) {
      return v1;
    } else {
      return v2;
    }
  }

  def max(v1: Any, v2: Any): Any = {
    val d1 = DPUtil.toDouble(v1);
    val d2 = DPUtil.toDouble(v2);
    if (d1 <= d2) {
      return v2;
    } else {
      return v1;
    }
  }

  def lessThan(v1: Any, v2: Any): Boolean = {
    val d1 = DPUtil.toDouble(v1);
    val d2 = DPUtil.toDouble(v2);
    return d1 < d2;
  }

  def checkUtility(result: Any, epsilon: Double, sensitivity: Double, prob: Double, ratio: Double): Boolean = {

    val value = toDouble(result);
    val stddev = sensitivity / epsilon;
    val noise = -stddev * Math.log(1 - prob);

    logWarning(s"estimated noise range [-$noise, $noise] with probability ${noise}");

    if (result == null || noise / value > ratio) {
      //throw new PrivacyException("accuracy bound violated, please revise your query");
      return false;
    } else {
      return true;
    }
  }

  def calibrateNoise(result: Any, agg: AggregateExpression, checker: SparkChecker, grouping: Boolean): Any = {
    val epsilon = agg.epsilon;
    val sensitivity = agg.sensitivity;
    logWarning(s"calibrating noise, epsilon:$epsilon, sensitivity:$sensitivity");
    if (checker.isCheckAccuracy && !checkUtility(result, epsilon, sensitivity, checker.getAccurcacyProb, checker.getAccurarcyNoise)) {
      logWarning(s"utility accuracy unsatisfied, suppress aggregate result: ${result}");
      if (!grouping) {
        checker.failAggregate(agg.dpId);
      }
      return null;
    }
    val noise = lapNoise(epsilon, sensitivity);
    result match {
      case long: Long => long + noise.toLong;
      case int: Int => int + noise.toInt;
      case double: Double => double + noise.toDouble;
      case float: Float => float + noise.toFloat;
      case short: Short => short + noise.toShort;
      case big: BigDecimal => big + BigDecimal(noise);
      case null => null;
      case _ => throw new PrivacyException(s"invalid argument: $result.");
    }
  }

  def rangeUnion(left: (Int, Int), right: (Int, Int)): (Int, Int) = {
    if (left == null) {
      return right;
    } else if (right == null) {
      return left;
    } else {
      val min = Math.min(left._1, right._1);
      val max = Math.max(left._2, right._2);
      return (min, max);
    }
  }
  def rangeIntersect(left: (Int, Int), right: (Int, Int)): (Int, Int) = {
    if (left == null) {
      return right;
    } else if (right == null) {
      return left;
    } else {
      val min = Math.max(left._1, right._1);
      val max = Math.min(left._2, right._2);
      if (min > max) {
        return null;
      } else {
        return (min, max);
      }
    }
  }
  def rangeExcept(left: (Int, Int), right: (Int, Int)): (Int, Int) = {
    left;
  }

  def rangeAdd(range: (Int, Int)*): (Int, Int) = {
    range.reduce((left, right) => (left._1 + right._1, left._2 + right._2));
  }

  def rangeSubtract(range: (Int, Int)*): (Int, Int) = {
    var i = 0;
    val newRange = range.map(r => {
      i += 1;
      if (i > 1) {
        rangeMinus(r);
      } else {
        r
      }
    });
    rangeAdd(newRange: _*);
  }

  def rangeTimes(left: (Int, Int), right: (Int, Int)): (Int, Int) = {
    val list = List(left._1 * right._1, left._1 * right._2, left._2 * right._1, left._2 * right._2);
    val min = list.reduce(Math.min(_, _));
    val max = list.reduce(Math.max(_, _));
    (min, max);
  }

  def rangeDivide(left: (Int, Int), right: (Int, Int)): (Int, Int) = {
    if (right._2 < 0 || right._1 > 0) {
      val list = List(left._1 / right._1, left._1 / right._2, left._2 / right._1, left._2 / right._2);
      val min = list.reduce(Math.min(_, _));
      val max = list.reduce(Math.max(_, _));
      (min, max);
    } else {
      val max = Math.max(Math.abs(left._1), Math.abs(left._2));
      val min = Math.min(-Math.abs(left._1), -Math.abs(left._2));
    }

    val rounded = rangeRound(right);
    val list = List(left._1 / rounded._1, left._1 / rounded._2, left._2 / rounded._1, left._2 / rounded._2);
    val min = list.reduce(Math.min(_, _));
    val max = list.reduce(Math.max(_, _));
    (min, max);
  }

  def rangeRemainder(left: (Int, Int), right: (Int, Int)): (Int, Int) = {
    if (right._1 > 0) {
      (0, right._2);
    } else if (right._2 < 0) {
      (right._1, 0);
    } else {
      right;
    }
  }

  def rangeMinus(range: (Int, Int)): (Int, Int) = {
    (-range._2, -range._1);
  }

  def multiplicityUnion(left: Option[Int], right: Option[Int]): Option[Int] = {
    if (left.isEmpty) {
      right;
    } else if (right.isEmpty) {
      left;
    } else {
      Some(left.get + right.get);
    }
  }

  def multiplicityIntersect(left: Option[Int], right: Option[Int]): Option[Int] = {
    if (left.isEmpty || right.isEmpty) {
      None;
    } else {
      Some(Math.min(left.get, right.get));
    }
  }

  def multiplicityExcept(left: Option[Int], right: Option[Int]): Option[Int] = {
    left;
  }

  private def rangeRound(range: (Int, Int)): (Int, Int) = {
    val min = if (range._1 == 0) 1 else range._1;
    val max = if (range._2 == 0) 1 else range._2;
    (min, max);
  }

}