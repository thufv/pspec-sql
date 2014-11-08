package org.apache.spark.sql.catalyst.types;
/**
 * A field inside a StructType.
 * @param name The name of this field.
 * @param dataType The data type of this field.
 * @param nullable Indicates if values of this field can be <code>null</code> values.
 */
public  class StructField implements scala.Product, scala.Serializable {
  public  java.lang.String name () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  // not preceding
  public   StructField (java.lang.String name, org.apache.spark.sql.catalyst.types.DataType dataType, boolean nullable) { throw new RuntimeException(); }
  private  void buildFormattedString (java.lang.String prefix, scala.collection.mutable.StringBuilder builder) { throw new RuntimeException(); }
}
