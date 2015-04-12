package org.apache.spark.sql.catalyst.checker.dp

import org.scalatest.FunSuite

class DPQueryTrackerSuite extends FunSuite {

  test("intervals")({
    val int1 = Interval(1, true, 10, false);
    val int2 = Interval(5, true, 10, true);

    assert(Interval.intersect(int1, int2) == Interval(5, true, 10, false));
    assert(Interval.except(int1, int2)(0) == Interval(1, true, 5, false));

    val int3 = Interval(6, true, 10, false);
    val seq = Interval.except(int2, int3);
    assert(seq(0) == Interval(5, true, 6, false));
    assert(seq(1) == Interval(10, true, 10, true));

  });

  test("domains")({
    val d1 = Domain(Interval(0, true, 10, true));
    val d2 = Domain(Interval(2, true, 4, true));
    val d3 = Domain.except(d1, d2);
    println(d3);

    val d4 = Domain(Interval(8, true, 15, true));
    val d5 = Domain.intersect(d3, d4);
    println(d5);

    val d6 = Domain.union(d3, d4);
    println(d6);
  });

}