package org.apache.spark.sql.json;
// no position
private  class JsonRDD$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final JsonRDD$ MODULE$ = null;
  public   JsonRDD$ () { throw new RuntimeException(); }
  private  org.apache.spark.rdd.RDD<org.apache.spark.sql.catalyst.expressions.Row> jsonStringToRow (org.apache.spark.rdd.RDD<java.lang.String> json, org.apache.spark.sql.catalyst.types.StructType schema) { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.types.StructType inferSchema (org.apache.spark.rdd.RDD<java.lang.String> json, double samplingRatio) { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.types.StructType createSchema (scala.collection.immutable.Set<scala.Tuple2<java.lang.String, org.apache.spark.sql.catalyst.types.DataType>> allKeys) { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.types.StructType nullTypeToStringType (org.apache.spark.sql.catalyst.types.StructType struct) { throw new RuntimeException(); }
  /**
   * Returns the most general data type for two given data types.
   */
  private  org.apache.spark.sql.catalyst.types.DataType compatibleType (org.apache.spark.sql.catalyst.types.DataType t1, org.apache.spark.sql.catalyst.types.DataType t2) { throw new RuntimeException(); }
  private  scala.PartialFunction<java.lang.Object, org.apache.spark.sql.catalyst.types.DataType> typeOfPrimitiveValue () { throw new RuntimeException(); }
  /**
   * Returns the element type of an JSON array. We go through all elements of this array
   * to detect any possible type conflict. We use {@link compatibleType} to resolve
   * type conflicts. Right now, when the element of an array is another array, we
   * treat the element as String.
   */
  private  org.apache.spark.sql.catalyst.types.ArrayType typeOfArray (scala.collection.Seq<java.lang.Object> l) { throw new RuntimeException(); }
  /**
   * Figures out all key names and data types of values from a parsed JSON object
   * (in the format of Map[Stirng, Any]). When the value of a key is an JSON object, we
   * only use a placeholder (StructType(Nil)) to mark that it should be a struct
   * instead of getting all fields of this struct because a field does not appear
   * in this JSON object can appear in other JSON objects.
   */
  private  scala.collection.immutable.Set<scala.Tuple2<java.lang.String, org.apache.spark.sql.catalyst.types.DataType>> allKeysWithValueTypes (scala.collection.Map<java.lang.String, java.lang.Object> m) { throw new RuntimeException(); }
  /**
   * Converts a Java Map/List to a Scala Map/Seq.
   * We do not use Jackson's scala module at here because
   * DefaultScalaModule in jackson-module-scala will make
   * the parsing very slow.
   */
  private  Object scalafy (Object obj) { throw new RuntimeException(); }
  private  org.apache.spark.rdd.RDD<scala.collection.Map<java.lang.String, java.lang.Object>> parseJson (org.apache.spark.rdd.RDD<java.lang.String> json) { throw new RuntimeException(); }
  private  long toLong (Object value) { throw new RuntimeException(); }
  private  double toDouble (Object value) { throw new RuntimeException(); }
  private  scala.math.BigDecimal toDecimal (Object value) { throw new RuntimeException(); }
  private  java.lang.String toJsonArrayString (scala.collection.Seq<java.lang.Object> seq) { throw new RuntimeException(); }
  private  java.lang.String toJsonObjectString (scala.collection.Map<java.lang.String, java.lang.Object> map) { throw new RuntimeException(); }
  private  java.lang.String toString (Object value) { throw new RuntimeException(); }
  private  Object enforceCorrectType (Object value, org.apache.spark.sql.catalyst.types.DataType desiredType) { throw new RuntimeException(); }
  private  org.apache.spark.sql.catalyst.expressions.Row asRow (scala.collection.Map<java.lang.String, java.lang.Object> json, org.apache.spark.sql.catalyst.types.StructType schema) { throw new RuntimeException(); }
}
