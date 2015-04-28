package org.apache.spark.sql.catalyst.checker.dp

import org.scalatest.FunSuite
import org.apache.spark.sql.catalyst.plans.logical.Filter
import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.dsl.plans._
import org.apache.spark.sql.catalyst.dsl.expressions._
import scala.collection.mutable.HashMap
import org.apache.spark.sql.catalyst.checker.Insensitive
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.types._
import scala.collection.immutable

class RangeResolverSuite extends FunSuite {

  private val resolver = new RangeResolver;

  private val leaf = Filter(true, null);
  private val plan = Filter(true, leaf);

  private val a = new AttributeReference("a", IntegerType, true)();
  private val b = new AttributeReference("b", DoubleType, true)();
  private val c = new AttributeReference("c", StringType, true)();

  {
    leaf.projectLabels.put(a, Insensitive("", "test", a));
    leaf.projectLabels.put(b, Insensitive("", "test", b));
    leaf.projectLabels.put(c, Insensitive("", "test", c));
  }

  test("Test Resolve Condition") {
    val cond1 = (a >= 1 && a <= 6 && (c like "a*"));

    val map1 = resolver.resolveCondition(cond1, plan);
    assert(map1.size == 1);
    val range1 = map1.getOrElse(a.toString, null);
    assert(range1 == new NumericalRange(Seq(Interval.newInstance(1, true, 6, true, false)), false));

    val cond2 = (a >= 6 || a <= 1) && (b <= 5 || InSet(c, immutable.Set("a")));
    val map2 = resolver.resolveCondition(cond2, plan);
    assert(map2.size == 1);
    val range2 = map2.getOrElse(a.toString, null).asInstanceOf[NumericalRange];
    assert(range2.intervals(0) == Interval.newEndBounded(1, true, false));
    assert(range2.intervals(1) == Interval.newStartBounded(6, true, false));

    val cond3 = (a >= 6 || b >= 1) && (b <= 5 || InSet(c, immutable.Set("a")));
    val map3 = resolver.resolveCondition(cond3, plan);
    assert(map3.size == 0);

    val cond4 = !(a >= 6 || a <= 1) && b <= 5 && !(InSet(c, immutable.Set("a")) || InSet(c, immutable.Set("b")));
    val map4 = resolver.resolveCondition(cond4, plan);

    assert(map4.size == 3);
    val arange = map4.getOrElse(a.toString, null).asInstanceOf[NumericalRange];
    assert(arange.intervals(0) == Interval.newInstance(1, false, 6, false, false));
    val brange = map4.getOrElse(b.toString, null).asInstanceOf[NumericalRange];
    assert(brange.intervals(0) == Interval.newEndBounded(5, true, true));
    val crange = map4.getOrElse(c.toString, null).asInstanceOf[CategoricalRange];
    assert(crange == CategoricalRange(immutable.Set("a", "b"), false));

  }

  test("Test Categorical") {
    val cond1 = InSet(c, immutable.Set("a", "b", "c"));
    val map1 = resolver.resolveCondition(cond1, plan);
    assert(map1.size == 1);
    val range1 = map1.getOrElse(c.toString, null);
    assert(range1 == new CategoricalRange(immutable.Set("a", "b", "c"), true));

  }
}