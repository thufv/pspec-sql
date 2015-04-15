package org.apache.spark.sql.catalyst.checker.dp;

import scala.collection.mutable.ListBuffer

case class Interval(start: Int, end: Int) {

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
      result.append(Interval(begin, end));
    }
    return Domain(result);
  }

}