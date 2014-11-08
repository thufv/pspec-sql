package org.apache.spark.sql.hive;
// no position
/**
 * :: DeveloperApi ::
 * Provides conversions between Spark SQL data types and Hive Metastore types.
 */
public  class HiveMetastoreTypes implements scala.util.parsing.combinator.RegexParsers {
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> primitiveType () { throw new RuntimeException(); }
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> arrayType () { throw new RuntimeException(); }
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> mapType () { throw new RuntimeException(); }
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.StructField> structField () { throw new RuntimeException(); }
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> structType () { throw new RuntimeException(); }
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> dataType () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.types.DataType toDataType (java.lang.String metastoreType) { throw new RuntimeException(); }
  static public  java.lang.String toMetastoreType (org.apache.spark.sql.catalyst.types.DataType dt) { throw new RuntimeException(); }
}
