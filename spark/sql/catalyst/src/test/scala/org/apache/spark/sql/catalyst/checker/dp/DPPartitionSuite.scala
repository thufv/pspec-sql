package org.apache.spark.sql.catalyst.checker.dp

import org.scalatest.FunSuite
import edu.thu.ss.spec.lang.pojo.GlobalBudget
import com.microsoft.z3.Context

class DPPartitionSuite extends FunSuite {

  private val context = new Context();

  test("Test Discrete Numerical Index")({
    val index = new NumericalIndex[GlobalPartition]("a");

    val p1 = new GlobalPartition(context, null);
    val range1 = new NumericalRange(Seq(Interval.newInstance(1, true, 8, true, false)), false);
    //[1,8]
    p1.getRanges.put("a", range1);

    val p2 = new GlobalPartition(context, null);
    val range2 = new NumericalRange(Seq(Interval.newInstance(0, true, 2, true, false)), false);
    val range3 = new NumericalRange(Seq(Interval.newInstance(6, true, 8, true, false)), false);
    //[0,2]
    p2.getRanges.put("a", range2);

    val p3 = new GlobalPartition(context, null);
    //[6,8]
    p3.getRanges.put("a", range3);

    val p4 = new GlobalPartition(context, null);
    //[0, 2], [6,8]
    p4.getRanges.put("a", range2.union(range3));

    val p5 = new GlobalPartition(context, null);
    //[1,2]
    p5.getRanges.put("a", range1.intersect(range2));

    index.addPartition(p1);
    index.addPartition(p2);
    index.addPartition(p3);
    index.addPartition(p4);
    index.addPartition(p5);

    val target1 = new NumericalRange(Seq(Interval.newInstance(2, true, 5, true, false)), false);
    val result1 = index.lookupDisjoint(target1);
    assert(result1 == p3);

    val target2 = new NumericalRange(Seq(Interval.newInstance(-1, true, 0, true, false)), false);
    val result2 = index.lookupDisjoint(target2);
    assert(result2 == p1 || result2 == p5);

    //test update
    index.removePartition(p3);
    val p6 = new GlobalPartition(context, null);
    //(-, 0), (2, 6), (8, +)
    p6.getRanges.put("a", range2.union(range3).not);
    index.addPartition(p6);

    val target3 = new NumericalRange(Seq(Interval.newInstance(0, true, 2, true, false)), false);
    val result3 = index.lookupDisjoint(target3);
    assert(result3 == p6)
  })
}