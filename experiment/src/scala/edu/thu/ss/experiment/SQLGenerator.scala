package edu.thu.ss.experiment

import scala.collection.mutable.ListBuffer
import scala.util.Random
import scala.collection.mutable.ArrayBuffer
import java.io.PrintStream
import java.util.Collections
import scala.collection.JavaConverters._

/**
 * number of queries
 * query range width (mean/std)
 */

abstract class ColumnType(name: String);

case class NumericalColumn(name: String, min: Double, max: Double, continuous: Boolean) extends ColumnType(name);

case class CategoricalColumn(name: String, values: Seq[String]) extends ColumnType(name);

case class SQLParams(num: Int, simple: Int, range: (Double, Double), complex: Int, target: String) {
};

class SQLGenerator(val table: String) {

  private val random = new Random(System.currentTimeMillis());

  private val columns = new ArrayBuffer[ColumnType];

  private val numericalColumns = new ArrayBuffer[NumericalColumn];

  private val patterns = new ArrayBuffer[ConditionPattern];

  {
    patterns.append(XLeY());
    patterns.append(XGeY());
    patterns.append(XAddYLeZ());
    patterns.append(XAddYGeZ());
    patterns.append(XMinusYLeZ());
    patterns.append(XMinusYGeZ());

    patterns.append(XLeY());
    patterns.append(XGeY());
    patterns.append(XAddYLeZ());
    patterns.append(XAddYGeZ());
    patterns.append(XMinusYLeZ());
    patterns.append(XMinusYGeZ());

    patterns.append(XLeY());
    patterns.append(XGeY());
    patterns.append(XAddYLeZ());
    patterns.append(XAddYGeZ());
    patterns.append(XMinusYLeZ());
    patterns.append(XMinusYGeZ());

    patterns.append(XLeY());
    patterns.append(XGeY());
    patterns.append(XAddYLeZ());
    patterns.append(XAddYGeZ());
    patterns.append(XMinusYLeZ());
    patterns.append(XMinusYGeZ());

    patterns.append(XTimesYLeZ());
    patterns.append(XTimesYGeZ());
  }

  def addColumn(column: ColumnType) = {
    columns.append(column);
    if (column.isInstanceOf[NumericalColumn]) {
      numericalColumns.append(column.asInstanceOf[NumericalColumn]);
    }
  }

  def generate(param: SQLParams, output: PrintStream) {
    for (i <- 1 to param.num) {
      generateSQL(param, output);
    }
  }

  private def generateSQL(param: SQLParams, output: PrintStream) {

    val conditions = new ListBuffer[String];
    val attributeNum = param.simple;
    val attributes = sample(attributeNum, columns);
    attributes.foreach(column => conditions.append(generateCondition(param, column)));

    val complexNum = param.complex;
    val subpatterns = sampleWithReplace(complexNum, patterns);
    subpatterns.foreach(pattern => conditions.append(generateCondition(param, pattern)));

    val condition = conditions.mkString(" AND ");
    val sql = s"SELECT AVG(${param.target}) FROM $table WHERE $condition ;";
    output.println(sql);
  }

  private def generateCondition(param: SQLParams, column: ColumnType): String = {
    val ratio = gaussian(param.range._1, param.range._2, 0);
    column match {
      case numerical: NumericalColumn => {
        val range = numerical.max - numerical.min;
        val mid = randomDouble(numerical.min, numerical.max);
        val start = rounding(numerical, mid - ratio * range / 2);
        val end = rounding(numerical, mid + ratio * range / 2);
        s" (${numerical.name} >= $start AND ${numerical.name} <= $end) ";
      }
      case category: CategoricalColumn => {
        val count = Math.max(1, category.values.length * ratio);
        val subvalues = sample(count, category.values);
        val in = "(" + subvalues.map(value => s"\'$value\'").mkString(",") + ")";
        s" (${category.name} IN $in) ";
      }
    }
  }

  private def generateCondition(param: SQLParams, pattern: ConditionPattern): String = {
    val seq = sample(pattern.numArgs, numericalColumns);
    return pattern.apply(seq.map(_.name));
  }

  private def sample[T](num: Int, items: Seq[T]): Seq[T] = {
    val shuffled = random.shuffle(items);
    shuffled.slice(0, num);
  }

  private def sampleWithReplace[T](num: Int, items: Seq[T]): Seq[T] = {
    val buffer = new ArrayBuffer[T](num);
    for (i <- 1 to num) {
      val r = random.nextInt(items.length);
      buffer.append(items(r));
    }
    return buffer;
  }

  private def gaussian(mean: Double, std: Double, min: Double): Double = {
    val result = random.nextGaussian * std + mean;
    if (result < min) {
      return min;
    } else {
      return result;
    }
  }

  private def randomDouble(min: Double, max: Double): Double = {
    random.nextDouble * (max - min) + min;
  }

  private def rounding(numerical: NumericalColumn, value: Double): Any = {
    if (numerical.continuous) {
      Math.scalb(value, 2);
    } else {
      double2Int(value);
    }
  }

  private implicit def double2Int(value: Double): Int = {
    Math.round(value).toInt;
  }

}