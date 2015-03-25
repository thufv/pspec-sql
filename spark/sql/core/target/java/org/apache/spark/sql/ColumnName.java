package org.apache.spark.sql;
/**
 * :: Experimental ::
 * A convenient class used for constructing schema.
 */
public  class ColumnName extends org.apache.spark.sql.Column {
  public   ColumnName (java.lang.String name) { throw new RuntimeException(); }
  /** Creates a new AttributeReference of type string */
  public  org.apache.spark.sql.types.StructField string () { throw new RuntimeException(); }
  /** Creates a new AttributeReference of type date */
  public  org.apache.spark.sql.types.StructField date () { throw new RuntimeException(); }
  /** Creates a new AttributeReference of type decimal */
  public  org.apache.spark.sql.types.StructField decimal () { throw new RuntimeException(); }
  /** Creates a new AttributeReference of type decimal */
  public  org.apache.spark.sql.types.StructField decimal (int precision, int scale) { throw new RuntimeException(); }
  /** Creates a new AttributeReference of type timestamp */
  public  org.apache.spark.sql.types.StructField timestamp () { throw new RuntimeException(); }
  /** Creates a new AttributeReference of type binary */
  public  org.apache.spark.sql.types.StructField binary () { throw new RuntimeException(); }
  /** Creates a new AttributeReference of type array */
  public  org.apache.spark.sql.types.StructField array (org.apache.spark.sql.types.DataType dataType) { throw new RuntimeException(); }
  /** Creates a new AttributeReference of type map */
  public  org.apache.spark.sql.types.StructField map (org.apache.spark.sql.types.DataType keyType, org.apache.spark.sql.types.DataType valueType) { throw new RuntimeException(); }
  public  org.apache.spark.sql.types.StructField map (org.apache.spark.sql.types.MapType mapType) { throw new RuntimeException(); }
  /** Creates a new AttributeReference of type struct */
  public  org.apache.spark.sql.types.StructField struct (scala.collection.Seq<org.apache.spark.sql.types.StructField> fields) { throw new RuntimeException(); }
  public  org.apache.spark.sql.types.StructField struct (org.apache.spark.sql.types.StructType structType) { throw new RuntimeException(); }
}
