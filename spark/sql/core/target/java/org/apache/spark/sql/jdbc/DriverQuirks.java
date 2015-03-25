package org.apache.spark.sql.jdbc;
/**
 * Encapsulates workarounds for the extensions, quirks, and bugs in various
 * databases.  Lots of databases define types that aren't explicitly supported
 * by the JDBC spec.  Some JDBC drivers also report inaccurate
 * information---for instance, BIT(n>1) being reported as a BIT type is quite
 * common, even though BIT in JDBC is meant for single-bit values.  Also, there
 * does not appear to be a standard name for an unbounded string or binary
 * type; we use BLOB and CLOB by default but override with database-specific
 * alternatives when these are absent or do not behave correctly.
 * <p>
 * Currently, the only thing DriverQuirks does is handle type mapping.
 * <code>getCatalystType</code> is used when reading from a JDBC table and <code>getJDBCType</code>
 * is used when writing to a JDBC table.  If <code>getCatalystType</code> returns <code>null</code>,
 * the default type handling is used for the given JDBC type.  Similarly,
 * if <code>getJDBCType</code> returns <code>(null, None)</code>, the default type handling is used
 * for the given Catalyst type.
 */
public abstract class DriverQuirks {
  /**
   * Fetch the DriverQuirks class corresponding to a given database url.
   */
  static public  org.apache.spark.sql.jdbc.DriverQuirks get (java.lang.String url) { throw new RuntimeException(); }
  public   DriverQuirks () { throw new RuntimeException(); }
  public abstract  org.apache.spark.sql.types.DataType getCatalystType (int sqlType, java.lang.String typeName, int size, org.apache.spark.sql.types.MetadataBuilder md) ;
  public abstract  scala.Tuple2<java.lang.String, scala.Option<java.lang.Object>> getJDBCType (org.apache.spark.sql.types.DataType dt) ;
}
