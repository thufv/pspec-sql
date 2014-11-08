package org.apache.spark;
/**
 * Helper object defining how to accumulate values of a particular type. An implicit
 * AccumulableParam needs to be available when you create {@link Accumulable}s of a specific type.
 * <p>
 * @tparam R the full accumulated data (result type)
 * @tparam T partial data that can be added in
 */
public  interface AccumulableParam<R extends java.lang.Object, T extends java.lang.Object> extends java.io.Serializable {
  /**
   * Add additional data to the accumulator value. Is allowed to modify and return <code>r</code>
   * for efficiency (to avoid allocating objects).
   * <p>
   * @param r the current value of the accumulator
   * @param t the data to be added to the accumulator
   * @return the new value of the accumulator
   */
  public abstract  R addAccumulator (R r, T t) ;
  /**
   * Merge two accumulated values together. Is allowed to modify and return the first value
   * for efficiency (to avoid allocating objects).
   * <p>
   * @param r1 one set of accumulated data
   * @param r2 another set of accumulated data
   * @return both data sets merged together
   */
  public abstract  R addInPlace (R r1, R r2) ;
  /**
   * Return the "zero" (identity) value for an accumulator type, given its initial value. For
   * example, if R was a vector of N dimensions, this would return a vector of N zeroes.
   */
  public abstract  R zero (R initialValue) ;
}
