package org.apache.spark.sql.parquet;
// no position
/**
 * Collection of converters of Parquet types (group and primitive types) that
 * model arrays and maps. The conversions are partly based on the AvroParquet
 * converters that are part of Parquet in order to be able to process these
 * types.
 * <p>
 * There are several types of converters:
 * <ul>
 *   <li>{@link org.apache.spark.sql.parquet.CatalystPrimitiveConverter} for primitive
 *   (numeric, boolean and String) types</li>
 *   <li>{@link org.apache.spark.sql.parquet.CatalystNativeArrayConverter} for arrays
 *   of native JVM element types; note: currently null values are not supported!</li>
 *   <li>{@link org.apache.spark.sql.parquet.CatalystArrayConverter} for arrays of
 *   arbitrary element types (including nested element types); note: currently
 *   null values are not supported!</li>
 *   <li>{@link org.apache.spark.sql.parquet.CatalystStructConverter} for structs</li>
 *   <li>{@link org.apache.spark.sql.parquet.CatalystMapConverter} for maps; note:
 *   currently null values are not supported!</li>
 *   <li>{@link org.apache.spark.sql.parquet.CatalystPrimitiveRowConverter} for rows
 *   of only primitive element types</li>
 *   <li>{@link org.apache.spark.sql.parquet.CatalystGroupConverter} for other nested
 *   records, including the top-level row record</li>
 * </ul>
 */
private  class CatalystConverter$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final CatalystConverter$ MODULE$ = null;
  public   CatalystConverter$ () { throw new RuntimeException(); }
  public  java.lang.String ARRAY_CONTAINS_NULL_BAG_SCHEMA_NAME () { throw new RuntimeException(); }
  public  java.lang.String ARRAY_ELEMENTS_SCHEMA_NAME () { throw new RuntimeException(); }
  public  java.lang.String MAP_KEY_SCHEMA_NAME () { throw new RuntimeException(); }
  public  java.lang.String MAP_VALUE_SCHEMA_NAME () { throw new RuntimeException(); }
  public  java.lang.String MAP_SCHEMA_NAME () { throw new RuntimeException(); }
  protected  parquet.io.api.Converter createConverter (org.apache.spark.sql.catalyst.types.StructField field, int fieldIndex, org.apache.spark.sql.parquet.CatalystConverter parent) { throw new RuntimeException(); }
  protected  org.apache.spark.sql.parquet.CatalystConverter createRootConverter (parquet.schema.MessageType parquetSchema, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes) { throw new RuntimeException(); }
}
