package org.apache.spark.sql.columnar;
/**
 * Statistical information for <code>Int</code> columns. More information is collected since <code>Int</code> is
 * frequently used. Extra information include:
 * <p>
 * - Ordering state (ascending/descending/unordered), may be used to decide whether binary search
 *   is applicable when searching elements.
 * - Maximum delta between adjacent elements, may be used to guide the <code>IntDelta</code> compression
 *   scheme.
 * <p>
 * (This two kinds of information are not used anywhere yet and might be removed later.)
 */
private  class IntColumnStats extends org.apache.spark.sql.columnar.BasicColumnStats<org.apache.spark.sql.catalyst.types.IntegerType$> {
  static public  int UNINITIALIZED () { throw new RuntimeException(); }
  static public  int INITIALIZED () { throw new RuntimeException(); }
  static public  int ASCENDING () { throw new RuntimeException(); }
  static public  int DESCENDING () { throw new RuntimeException(); }
  static public  int UNORDERED () { throw new RuntimeException(); }
  public   IntColumnStats () { throw new RuntimeException(); }
  private  int orderedState () { throw new RuntimeException(); }
  private  int lastValue () { throw new RuntimeException(); }
  private  int _maxDelta () { throw new RuntimeException(); }
  public  boolean isAscending () { throw new RuntimeException(); }
  public  boolean isDescending () { throw new RuntimeException(); }
  public  boolean isOrdered () { throw new RuntimeException(); }
  public  int maxDelta () { throw new RuntimeException(); }
  public  scala.Tuple2<java.lang.Object, java.lang.Object> initialBounds () { throw new RuntimeException(); }
  public  boolean isBelow (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) { throw new RuntimeException(); }
  public  boolean isAbove (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) { throw new RuntimeException(); }
  public  boolean contains (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) { throw new RuntimeException(); }
  public  void gatherStats (org.apache.spark.sql.catalyst.expressions.Row row, int ordinal) { throw new RuntimeException(); }
}
