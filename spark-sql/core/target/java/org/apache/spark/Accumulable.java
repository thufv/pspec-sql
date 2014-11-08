package org.apache.spark;
/**
 * A data type that can be accumulated, ie has an commutative and associative "add" operation,
 * but where the result type, <code>R</code>, may be different from the element type being added, <code>T</code>.
 * <p>
 * You must define how to add data, and how to merge two of these together.  For some data types,
 * such as a counter, these might be the same operation. In that case, you can use the simpler
 * {@link org.apache.spark.Accumulator}. They won't always be the same, though -- e.g., imagine you are
 * accumulating a set. You will add items to the set, and you will union two sets together.
 * <p>
 * @param initialValue initial value of accumulator
 * @param param helper object defining how to add elements of type <code>R</code> and <code>T</code>
 * @param name human-readable name for use in Spark's web UI
 * @tparam R the full accumulated data (result type)
 * @tparam T partial data that can be added in
 */
public  class Accumulable<R extends java.lang.Object, T extends java.lang.Object> implements java.io.Serializable {
  public  scala.Option<java.lang.String> name () { throw new RuntimeException(); }
  // not preceding
  public   Accumulable (R initialValue, org.apache.spark.AccumulableParam<R, T> param, scala.Option<java.lang.String> name) { throw new RuntimeException(); }
  public   Accumulable (R initialValue, org.apache.spark.AccumulableParam<R, T> param) { throw new RuntimeException(); }
  public  long id () { throw new RuntimeException(); }
  private  R value_ () { throw new RuntimeException(); }
  public  R zero () { throw new RuntimeException(); }
  private  boolean deserialized () { throw new RuntimeException(); }
  /**
   * Add more data to this accumulator / accumulable
   * @param term the data to add
   */
  public  void add (T term) { throw new RuntimeException(); }
  /**
   * Merge two accumulable objects together
   * <p>
   * Normally, a user will not want to use this version, but will instead call <code>add</code>.
   * @param term the other <code>R</code> that will get merged with this
   */
  public  void merge (R term) { throw new RuntimeException(); }
  /**
   * Access the accumulator's current value; only allowed on master.
   */
  public  R value () { throw new RuntimeException(); }
  /**
   * Get the current value of this accumulator from within a task.
   * <p>
   * This is NOT the global value of the accumulator.  To get the global value after a
   * completed operation on the dataset, call <code>value</code>.
   * <p>
   * The typical use of this method is to directly mutate the local value, eg., to add
   * an element to a Set.
   */
  public  R localValue () { throw new RuntimeException(); }
  /**
   * Set the accumulator's value; only allowed on master
   */
  public  void setValue (R newValue) { throw new RuntimeException(); }
  private  void readObject (java.io.ObjectInputStream in) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
