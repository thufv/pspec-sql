package org.apache.spark;
/**
 * A simpler value of {@link Accumulable} where the result type being accumulated is the same
 * as the types of elements being merged, i.e. variables that are only "added" to through an
 * associative operation and can therefore be efficiently supported in parallel. They can be used
 * to implement counters (as in MapReduce) or sums. Spark natively supports accumulators of numeric
 * value types, and programmers can add support for new types.
 * <p>
 * An accumulator is created from an initial value <code>v</code> by calling {@link SparkContext#accumulator}.
 * Tasks running on the cluster can then add to it using the {@link Accumulable#+=} operator.
 * However, they cannot read its value. Only the driver program can read the accumulator's value,
 * using its value method.
 * <p>
 * The interpreter session below shows an accumulator being used to add up the elements of an array:
 * <p>
 * <pre><code>
 * scala&gt; val accum = sc.accumulator(0)
 * accum: spark.Accumulator[Int] = 0
 *
 * scala&gt; sc.parallelize(Array(1, 2, 3, 4)).foreach(x =&gt; accum += x)
 * ...
 * 10/09/29 18:41:08 INFO SparkContext: Tasks finished in 0.317106 s
 *
 * scala&gt; accum.value
 * res2: Int = 10
 * </code></pre>
 * <p>
 * @param initialValue initial value of accumulator
 * @param param helper object defining how to add elements of type <code>T</code>
 * @tparam T result type
 */
public  class Accumulator<T extends java.lang.Object> extends org.apache.spark.Accumulable<T, T> {
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   Accumulator (T initialValue, org.apache.spark.AccumulatorParam<T> param, scala.Option<java.lang.String> name) { throw new RuntimeException(); }
  public   Accumulator (T initialValue, org.apache.spark.AccumulatorParam<T> param) { throw new RuntimeException(); }
}
