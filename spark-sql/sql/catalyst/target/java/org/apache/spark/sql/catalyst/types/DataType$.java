package org.apache.spark.sql.catalyst.types;
// no position
/**
 * Utility functions for working with DataTypes.
 */
public  class DataType$ implements scala.util.parsing.combinator.RegexParsers {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final DataType$ MODULE$ = null;
  public   DataType$ () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> primitiveType () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> arrayType () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> mapType () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.StructField> structField () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<java.lang.Object> boolVal () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> structType () { throw new RuntimeException(); }
  protected  scala.util.parsing.combinator.Parsers.Parser<org.apache.spark.sql.catalyst.types.DataType> dataType () { throw new RuntimeException(); }
  /**
   * Parses a string representation of a DataType.
   * <p>
   * TODO: Generate parser as pickler...
   */
  public  org.apache.spark.sql.catalyst.types.DataType apply (java.lang.String asString) { throw new RuntimeException(); }
  protected  void buildFormattedString (org.apache.spark.sql.catalyst.types.DataType dataType, java.lang.String prefix, scala.collection.mutable.StringBuilder builder) { throw new RuntimeException(); }
}
