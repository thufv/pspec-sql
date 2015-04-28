package org.apache.spark.sql.catalyst.checker.dp

import org.scalatest.FunSuite
import scala.collection.immutable

class RangeSuite extends FunSuite {

  test("Test Discrete Numerical Range") {
    val range1 = new NumericalRange(Seq(Interval.newInstance(5, true, 5, true, false)), false);
    val range2 = new NumericalRange(Seq(Interval.newInstance(6, true, 6, true, false)), false);
    val range3 = new NumericalRange(Seq(Interval.newInstance(7, true, 7, true, false)), false);

    val result1 = range1.union(range2).union(range3).asInstanceOf[NumericalRange];
    assert(result1.continuous == false);
    assert(result1.intervals.length == 1);
    assert(result1.intervals(0).start == 5);
    assert(result1.intervals(0).end == 7);

    val range4 = new NumericalRange(Seq(Interval.newEndBounded(6, false, false)), false);
    val range5 = new NumericalRange(Seq(Interval.newStartBounded(5, true, false)), false);

    val result2 = range4.intersect(range5).asInstanceOf[NumericalRange];
    assert(result2.continuous == false);
    assert(result2.intervals.length == 1);
    assert(result2.intervals(0).start == 5);
    assert(result2.intervals(0).end == 5);

    val result3 = range4.not.asInstanceOf[NumericalRange];
    assert(result3.continuous == false);
    assert(result3.intervals.length == 1);
    assert(result3.intervals(0).start == 6);
    assert(result3.intervals(0).end == Double.MaxValue);

    val range11 = new NumericalRange(Seq(Interval.newInstance(1, true, 2, true, false)), false);
    val range12 = new NumericalRange(Seq(Interval.newInstance(4, true, 5, true, false)), false);
    val range13 = new NumericalRange(Seq(Interval.newInstance(7, true, 8, true, false)), false);
    val result11 = range11.union(range12).union(range13).asInstanceOf[NumericalRange];
    assert(result11.continuous == false);
    assert(result11.intervals.length == 3);

    val range14 = new NumericalRange(Seq(Interval.newInstance(2, true, 7, true, false)), false);
    val result12 = result11.union(range14).asInstanceOf[NumericalRange];
    assert(result12.continuous == false);
    assert(result12.intervals.length == 1);
    assert(result12.intervals(0).start == 1);
    assert(result12.intervals(0).end == 8);

    val range21 = new NumericalRange(Seq(Interval.newInstance(1, true, 9, true, false)), false);
    val range22 = new NumericalRange(Seq(Interval.newInstance(2, true, 4, false, false)), false);
    val range23 = new NumericalRange(Seq(Interval.newInstance(0, true, 5, true, false)), false);

    val result22 = range21.intersect(range22.not).intersect(range23).asInstanceOf[NumericalRange];
    assert(result22.continuous == false);
    assert(result22.intervals.length == 2);
    assert(result22.intervals(0) == Interval.newInstance(1, true, 2, false, false));
    assert(result22.intervals(1) == Interval.newInstance(4, true, 5, true, false));
  }

  test("Test Continuous Numerical Range") {
    val range1 = new NumericalRange(Seq(Interval.newInstance(5, true, 6, true, true)), true);
    val range2 = new NumericalRange(Seq(Interval.newInstance(6, true, 7, true, true)), true);
    val range3 = new NumericalRange(Seq(Interval.newInstance(7, true, 8, true, true)), true);

    val result1 = range1.union(range2).union(range3).asInstanceOf[NumericalRange];
    assert(result1.continuous == true);
    assert(result1.intervals.length == 1);
    assert(result1.intervals(0).start == 5);
    assert(result1.intervals(0).end == 8);

    val range4 = new NumericalRange(Seq(Interval.newEndBounded(6, false, true)), true);
    val range5 = new NumericalRange(Seq(Interval.newStartBounded(5, true, true)), true);

    val result2 = range4.intersect(range5).asInstanceOf[NumericalRange];
    assert(result2.continuous == true);
    assert(result2.intervals.length == 1);
    assert(result2.intervals(0).start == 5);
    near(result2.intervals(0).end, 6);

    val result3 = range4.not.asInstanceOf[NumericalRange];
    assert(result3.continuous == true);
    assert(result3.intervals.length == 1);
    assert(result3.intervals(0).start == 6);
    assert(result3.intervals(0).end == Double.MaxValue);

    val range11 = new NumericalRange(Seq(Interval.newInstance(1, true, 2, true, true)), true);
    val range12 = new NumericalRange(Seq(Interval.newInstance(4, true, 5, true, true)), true);
    val range13 = new NumericalRange(Seq(Interval.newInstance(7, true, 8, true, true)), true);
    val result11 = range11.union(range12).union(range13).asInstanceOf[NumericalRange];
    assert(result11.continuous == true);
    assert(result11.intervals.length == 3);

    val range14 = new NumericalRange(Seq(Interval.newInstance(2, true, 7, true, true)), true);
    val result12 = result11.union(range14).asInstanceOf[NumericalRange];
    assert(result12.continuous == true);
    assert(result12.intervals.length == 1);
    assert(result12.intervals(0).start == 1);
    assert(result12.intervals(0).end == 8);

    val range21 = new NumericalRange(Seq(Interval.newInstance(1, true, 9, true, true)), true);
    val range22 = new NumericalRange(Seq(Interval.newInstance(2, true, 4, false, true)), true);
    val range23 = new NumericalRange(Seq(Interval.newInstance(0, true, 5, true, true)), true);

    val result22 = range21.intersect(range22.not).intersect(range23).asInstanceOf[NumericalRange];
    assert(result22.continuous == true);
    assert(result22.intervals.length == 2);
    assert(result22.intervals(0) == Interval.newInstance(1, true, 2, false, true));
    assert(result22.intervals(1) == Interval.newInstance(4, true, 5, true, true));
  }

  test("Categorical Range") {
    val range11 = new CategoricalRange(immutable.Set("1", "2", "3"), true);
    val range12 = new CategoricalRange(immutable.Set("0", "1"), true);

    val result11 = range11.intersect(range12).asInstanceOf[CategoricalRange];
    val result12 = range11.union(range12).asInstanceOf[CategoricalRange];
    assert(result11.values == Set("1"));
    assert(result12.values == Set("0", "1", "2", "3"));
    assert(range11.disjoint(range12) == false);

    val range21 = new CategoricalRange(immutable.Set("1", "2", "3"), true);
    val range22 = new CategoricalRange(immutable.Set("0", "1"), false);

    val result21 = range21.intersect(range22).asInstanceOf[CategoricalRange];
    val result22 = range21.union(range22).asInstanceOf[CategoricalRange];
    assert(result21 == CategoricalRange(immutable.Set("2", "3"), true));
    assert(result22 == CategoricalRange(immutable.Set("0"), false));
    assert(range21.disjoint(range22) == false);

    val range31 = new CategoricalRange(immutable.Set("1", "2"), true);
    val range32 = new CategoricalRange(immutable.Set("1", "2", "5", "0"), false);
    assert(range31.disjoint(range32) == true);

    val range41 = new CategoricalRange(immutable.Set("1", "2", "3"), false);
    val range42 = new CategoricalRange(immutable.Set("0", "1"), false);
    val result41 = range41.intersect(range42).asInstanceOf[CategoricalRange];
    val result42 = range41.union(range42).asInstanceOf[CategoricalRange];
    assert(result41 == CategoricalRange(immutable.Set("0", "2", "3", "1"), false));
    assert(result42 == CategoricalRange(immutable.Set("1"), false));
    assert(range41.disjoint(range42) == false);
  }

  private def near(value: Double, target: Double) {
    assert(value < target + 0.01);
    assert(value > target - 0.01);

  }

}