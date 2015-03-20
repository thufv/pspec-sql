package org.apache.spark.sql.catalyst.checker

import scala.util.Random
import scala.math.BigDecimal

object DPHelper {

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

  def checkUtility(result: Any, epsilon: Double, sensitivity: Double) {

  }

  def addNoise(result: Any, epsilon: Double, sensitivity: Double): Any = {
    val noise = lapNoise(epsilon, sensitivity);
    result match {
      case long: Long => long + noise.toLong;
      case int: Int => int + noise.toInt;
      case double: Double => double + noise.toDouble;
      case float: Float => float + noise.toFloat;
      case short: Short => short + noise.toShort;
      case big: BigDecimal => big + BigDecimal(noise);
      case _ => throw new RuntimeException(s"invalid argument: $result.");
    }
  }

}