package org.apache.spark.sql.columnar.compression;
// no position
private  class IntDelta$ extends org.apache.spark.sql.columnar.compression.IntegralDelta<org.apache.spark.sql.catalyst.types.IntegerType$> implements scala.Product, scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final IntDelta$ MODULE$ = null;
  public   IntDelta$ () { throw new RuntimeException(); }
  public  int typeId () { throw new RuntimeException(); }
  public  boolean supports (org.apache.spark.sql.columnar.ColumnType<?, ?> columnType) { throw new RuntimeException(); }
  protected  int addDelta (int x, byte delta) { throw new RuntimeException(); }
  protected  scala.Tuple2<java.lang.Object, java.lang.Object> byteSizedDelta (int x, int y) { throw new RuntimeException(); }
}
