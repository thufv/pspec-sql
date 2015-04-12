package org.apache.spark.sql.catalyst.checker.dp;

import scala.collection.mutable.ListBuffer

case class Interval private (start: Double, end: Double) {
  def disjoint(other: Interval): Boolean = {
    return this.end < other.start || other.end < this.start;
  }

  def overlap(other: Interval): Boolean = {
    return !this.disjoint(other) && !(this.includes(other) || other.includes(this));
  }

  def includes(other: Interval): Boolean = {
    return start <= other.start && end >= other.end;
  }

}

object Interval {
  private val Rounding = 0.0000000000001;

  def apply(min: Double, minInclusive: Boolean, max: Double, maxInclusive: Boolean): Interval = {
    assert(min <= max);
    val _min = if (minInclusive) min else min + Rounding;
    val _max = if (maxInclusive) max else max - Rounding;
    return Interval(_min, _max);
  }

  def sort(left: Interval, right: Interval): (Interval, Interval) = {
    val _left = if (left.start < right.start) left else right;
    val _right = if (left.start < right.start) right else left;
    (_left, _right);
  }

  def intersect(left: Interval, right: Interval): Interval = {
    val start = Math.max(left.start, right.start);
    val end = Math.min(left.end, right.end);
    if (start < end) {
      Interval(start, end);
    } else {
      null;
    }
  }

  def except(left: Interval, right: Interval): Seq[Interval] = {
    if (left.disjoint(right)) {
      Seq(left);
    } else if (right.includes(left)) {
      Nil;
    } else if (left.includes(right)) {
      //split
      Seq(Interval(left.start, true, right.start, false), Interval(right.end, false, left.end, true));
    } else {
      //overlap
      Seq(Interval(left.start, true, right.start, false));
    }
  }
}

//intervals must be disjointed, and are sorted in an ascending order
case class Domain private (intervals: Seq[Interval]) {

  def disjoint(other: Domain): Boolean = {
    return !joint(other);

  }

  def joint(other: Domain): Boolean = {
    val seq1 = intervals;
    val seq2 = other.intervals;
    var i, j = 0;
    while (i < seq1.length && j < seq2.length) {
      val int1 = seq1(i);
      val int2 = seq2(j);
      if (!seq1(i).disjoint(seq2(j))) {
        return true;
      } else if (seq1(i).start < seq2(j).start) {
        i += 1;
      } else {
        j += 1;
      }
    }
    return false;
  }
}

object Domain {

  def apply(interval: Interval) = new Domain(Seq(interval));

  def union(range1: Domain, range2: Domain): Domain = {
    val list = (range1.intervals ++ range2.intervals).sortBy(_.start).distinct;
    var i = 0;
    val result = new ListBuffer[Interval];
    while (i < list.length) {
      val begin = list(i).start;
      var end = list(i).end;
      i += 1;
      while (i < list.length && list(i).start <= end) {
        end = list(i).end;
        i += 1;
      }
      result.append(Interval(begin, true, end, true));
    }
    return Domain(result);
  }

  def except(domain1: Domain, domain2: Domain): Domain = {
    val result = new ListBuffer[Interval];
    domain1.intervals.foreach(int1 => {
      domain2.intervals.foreach(int2 => {
        val seq = Interval.except(int1, int2);
        result ++= seq;
      });
    });

    return Domain(result);
  }

  def intersect(domain1: Domain, domain2: Domain): Domain = {
    val result = new ListBuffer[Interval];
    domain1.intervals.foreach(int1 => {
      domain2.intervals.foreach(int2 => {
        val int = Interval.intersect(int1, int2);
        if (int != null) {
          result += int;
        }
      });
    });
    return Domain(result);
  }
}