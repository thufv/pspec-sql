package org.apache.spark;
/**
 * A simpler version of {@link org.apache.spark.AccumulableParam} where the only data type you can add
 * in is the same type as the accumulated value. An implicit AccumulatorParam object needs to be
 * available when you create Accumulators of a specific type.
 * <p>
 * @tparam T type of value to accumulate
 */
public abstract interface AccumulatorParam<T extends java.lang.Object> extends org.apache.spark.AccumulableParam<T, T> {
  public  T addAccumulator (T t1, T t2) ;
}
