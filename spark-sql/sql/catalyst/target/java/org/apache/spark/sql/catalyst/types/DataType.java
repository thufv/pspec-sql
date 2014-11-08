package org.apache.spark.sql.catalyst.types;
public abstract class DataType {
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> primitiveType () { throw new RuntimeException(); }
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> arrayType () { throw new RuntimeException(); }
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> mapType () { throw new RuntimeException(); }
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.StructField> structField () { throw new RuntimeException(); }
  static protected  scala.util.parsing.combinator.Parsers.Parser<java.lang.Object> boolVal () { throw new RuntimeException(); }
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> structType () { throw new RuntimeException(); }
  static protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> dataType () { throw new RuntimeException(); }
  /**
   * Parses a string representation of a DataType.
   * <p>
   * TODO: Generate parser as pickler...
   */
  static public  org.apache.spark.sql.catalyst.types.DataType apply (java.lang.String asString) { throw new RuntimeException(); }
  static protected  void buildFormattedString (org.apache.spark.sql.catalyst.types.DataType dataType, java.lang.String prefix, scala.collection.mutable.StringBuilder builder) { throw new RuntimeException(); }
  public   DataType () { throw new RuntimeException(); }
  /** Matches any expression that evaluates to this DataType */
  public  boolean unapply (org.apache.spark.sql.catalyst.expressions.Expression a) { throw new RuntimeException(); }
  public  boolean isPrimitive () { throw new RuntimeException(); }
  public abstract  java.lang.String simpleString () ;
}
