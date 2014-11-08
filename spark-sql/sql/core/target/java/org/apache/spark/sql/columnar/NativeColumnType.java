package org.apache.spark.sql.columnar;
private abstract class NativeColumnType<T extends org.apache.spark.sql.catalyst.types.NativeType> extends org.apache.spark.sql.columnar.ColumnType<T, java.lang.Object> {
  public  T dataType () { throw new RuntimeException(); }
  // not preceding
  public   NativeColumnType (T dataType, int typeId, int defaultSize) { throw new RuntimeException(); }
  /**
   * Scala TypeTag. Can be used to create primitive arrays and hash tables.
   */
  public  scala.reflect.api.TypeTags.TypeTag<java.lang.Object> scalaTag () { throw new RuntimeException(); }
}
