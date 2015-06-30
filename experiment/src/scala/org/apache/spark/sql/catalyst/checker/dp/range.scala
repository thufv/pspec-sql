package org.apache.spark.sql.catalyst.checker.dp

import scala.collection.mutable.ArrayBuffer
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil._
import org.apache.spark.sql.catalyst.checker.util.CheckerUtil
import scala.collection.immutable

trait Range {
  def disjoint(that: Range): Boolean;

  def joint(that: Range) = !disjoint(that);

  def union(that: Range): Range;

  def intersect(that: Range): Range;

  def not(): Range;

}

case class CategoricalRange(values: immutable.Set[String], include: Boolean) extends Range {

  def disjoint(that: Range): Boolean = {
    val cthat = asCategoricalRange(that);
    if (include && cthat.include) {
      return !CheckerUtil.intersect(values, cthat.values);
    } else if (include && !cthat.include) {
      return containsAll(cthat.values, values);
    } else if (!include && cthat.include) {
      return containsAll(values, cthat.values);
    } else {
      return false;
    }
  }

  def union(that: Range): Range = {
    if (that == null) {
      return this;
    }
    val cthat = asCategoricalRange(that);
    var range: CategoricalRange = null;
    if (include && cthat.include) {
      //also include
      range = new CategoricalRange(values ++ cthat.values, true);
    } else if (include && !cthat.include) {
      range = new CategoricalRange(cthat.values -- values, false);
    } else if (!include && cthat.include) {
      range = new CategoricalRange(values -- cthat.values, false);
    } else {
      //both exclude
      range = new CategoricalRange(values.intersect(cthat.values), false);
    }
    return range;
  }

  def intersect(that: Range): Range = {
    if (that == null) {
      return this;
    }
    val cthat = asCategoricalRange(that);
    var range: CategoricalRange = null;
    if (include && cthat.include) {
      range = new CategoricalRange(values.intersect(cthat.values), true);
    } else if (include && !cthat.include) {
      range = new CategoricalRange(values -- cthat.values, true);
    } else if (!include && cthat.include) {
      range = new CategoricalRange(cthat.values -- values, true);
    } else {
      range = new CategoricalRange(values ++ cthat.values, false);
    }
    return range;
  }

  def not(): Range = {
    return new CategoricalRange(values, !include);
  }

  private def asCategoricalRange(that: Range): CategoricalRange = {
    if (!that.isInstanceOf[CategoricalRange]) {
      throw new IllegalArgumentException(s"$that is not a valid CategoricalRange");
    }
    return that.asInstanceOf[CategoricalRange];
  }

}

case class NumericalRange(intervals: Seq[Interval], continuous: Boolean) extends Range {

  def disjoint(that: Range): Boolean = {
    val nthat = asNumericalRange(that);
    val seq1 = intervals;
    val seq2 = nthat.intervals;
    var i, j = 0;
    while (i < seq1.length && j < seq2.length) {
      val int1 = seq1(i);
      val int2 = seq2(j);
      if (seq1(i).joint(seq2(j))) {
        return false;
      } else if (seq1(i).start < seq2(j).start) {
        i += 1;
      } else {
        j += 1;
      }
    }
    return true;
  }

  def union(that: Range): Range = {
    if (that == null) {
      return this;
    }
    val nthat = asNumericalRange(that);
    val list = merge(intervals, nthat.intervals);
    var i = 0;
    val result = new ArrayBuffer[Interval];
    while (i < list.length) {
      val start = list(i).start;
      var end = list(i).end;
      i += 1;
      var continue = true;
      while (i < list.length && continue) {
        if (continuous && end >= list(i).start) {
          end = Math.max(end, list(i).end);
          i += 1;
        } else if (!continuous && end + 1 >= list(i).start) {
          end = Math.max(end, list(i).end);
          i += 1;
        } else {
          continue = false;
        }
      }
      result.append(Interval.newInstance(start, true, end, true, continuous));
    }
    return NumericalRange(result, continuous);
  }

  private def merge[T <: Comparable[T]](seq1: Seq[T], seq2: Seq[T]): Seq[T] = {
    val result = new ArrayBuffer[T](seq1.length + seq2.length);
    var i, j = 0;
    while (i < seq1.length && j < seq2.length) {
      val t1 = seq1(i);
      val t2 = seq2(j);
      val c = t1.compareTo(t2);
      if (c < 0) {
        result.append(t1);
        i += 1;
      } else if (c > 0) {
        result.append(t2);
        j += 1;
      } else {
        result.append(t1, t2);
        i += 1;
        j += 1;
      }
    }
    if (i < seq1.length) {
      for (k <- i to seq1.length - 1) {
        result.append(seq1(k));
      }
    }
    if (j < seq2.length) {
      for (k <- j to seq2.length - 1) {
        result.append(seq2(k));
      }
    }
    return result;
  }

  def intersect(that: Range): Range = {
    if (that == null) {
      return this;
    }
    val nthat = asNumericalRange(that);
    val result = new ArrayBuffer[Interval];
    intervals.foreach(int1 => {
      nthat.intervals.foreach(int2 => {
        val int = int1.intersect(int2);
        if (int != null) {
          result.append(int);
        }
      });
    });
    return new NumericalRange(result, continuous);
  }

  def not(): Range = {
    val result = new ArrayBuffer[Interval];
    if (intervals.head.start > Double.MinValue) {
      val interval = Interval.newEndBounded(intervals.head.start, false, continuous);
      result.append(interval);
    }

    for (i <- 0 to intervals.length - 2) {
      val int1 = intervals(i);
      val int2 = intervals(i + 1);
      val interval = Interval.newInstance(int1.end, false, int2.start, false, continuous);
      result.append(interval);
    }

    if (intervals.last.end < Double.MaxValue) {
      val interval = Interval.newStartBounded(intervals.last.end, false, continuous);
      result.append(interval);
    }
    return NumericalRange(result, continuous);
  }

  private def asNumericalRange(that: Range): NumericalRange = {
    if (!that.isInstanceOf[NumericalRange]) {
      throw new IllegalArgumentException(s"$that is not a valid NumericalRange");
    }
    val result = that.asInstanceOf[NumericalRange];
    if (result.continuous != continuous) {
      throw new IllegalArgumentException(s"incompatible discrete and continuous numerical range for comparision.");
    }
    return result;

  }
}

case class Interval private (start: Double, end: Double) extends Comparable[Interval] {

  def compareTo(that: Interval): Int = {
    return java.lang.Double.compare(start, that.start);
  }

  def joint(other: Interval): Boolean = {
    return !disjoint(other);
  }

  def disjoint(other: Interval): Boolean = {
    return this.end < other.start || other.end < this.start;
  }

  def overlap(other: Interval): Boolean = {
    return !this.disjoint(other) && !(this.includes(other) || other.includes(this));
  }

  def includes(other: Interval): Boolean = {
    return start <= other.start && end >= other.end;
  }

  def intersect(that: Interval): Interval = {
    val nstart = Math.max(start, that.start);
    val nend = Math.min(end, that.end);
    if (nstart > nend) {
      return null;
    } else {
      return Interval(nstart, nend);
    }

  }

}
object Interval {
  private val Continous_Rounding = 0.0000000000001;
  private val Discrete_Rounding = 1;

  def newStartBounded(start: Double, includeStart: Boolean, continuous: Boolean): Interval = {
    val end = Double.MaxValue;
    return newInstance(start, includeStart, end, true, continuous);
  }

  def newEndBounded(end: Double, includeEnd: Boolean, continuous: Boolean): Interval = {
    val start = Double.MinValue;
    return newInstance(start, true, end, includeEnd, continuous);
  }

  def newInstance(start: Double, includeStart: Boolean, end: Double, includeEnd: Boolean, continuous: Boolean): Interval = {
    assert(start <= end);
    val rounding = if (continuous) {
      Continous_Rounding;
    } else {
      Discrete_Rounding;
    }

    val nstart = if (includeStart) start else start + rounding;
    val nend = if (includeEnd) end else end - rounding;
    return Interval(nstart, nend);
  }
}