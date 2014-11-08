package org.apache.spark.sql.catalyst.types;
public  class StructType extends org.apache.spark.sql.catalyst.types.DataType implements scala.Product, scala.Serializable {
  static protected  org.apache.spark.sql.catalyst.types.StructType fromAttributes (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes) { throw new RuntimeException(); }
  static private  boolean validateFields (scala.collection.Seq<org.apache.spark.sql.catalyst.types.StructField> fields) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.types.StructField> fields () { throw new RuntimeException(); }
  // not preceding
  public   StructType (scala.collection.Seq<org.apache.spark.sql.catalyst.types.StructField> fields) { throw new RuntimeException(); }
  /**
   * Returns all field names in a {@link Seq}.
   */
  public  scala.collection.Seq<java.lang.String> fieldNames () { throw new RuntimeException(); }
  private  scala.collection.immutable.Set<java.lang.String> fieldNamesSet () { throw new RuntimeException(); }
  private  scala.collection.immutable.Map<java.lang.String, org.apache.spark.sql.catalyst.types.StructField> nameToField () { throw new RuntimeException(); }
  /**
   * Extracts a {@link StructField} of the given name. If the {@link StructType} object does not
   * have a name matching the given name, <code>null</code> will be returned.
   */
  public  org.apache.spark.sql.catalyst.types.StructField apply (java.lang.String name) { throw new RuntimeException(); }
  /**
   * Returns a {@link StructType} containing {@link StructField}s of the given names.
   * Those names which do not have matching fields will be ignored.
   */
  public  org.apache.spark.sql.catalyst.types.StructType apply (scala.collection.immutable.Set<java.lang.String> names) { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.AttributeReference> toAttributes () { throw new RuntimeException(); }
  public  java.lang.String treeString () { throw new RuntimeException(); }
  public  void printTreeString () { throw new RuntimeException(); }
  private  void buildFormattedString (java.lang.String prefix, scala.collection.mutable.StringBuilder builder) { throw new RuntimeException(); }
  public  java.lang.String simpleString () { throw new RuntimeException(); }
}
