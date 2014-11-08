package org.apache.spark.sql.catalyst.expressions;
/**
 * Represents one row of output from a relational operator.  Allows both generic access by ordinal,
 * which will incur boxing overhead for primitives, as well as native primitive access.
 * <p>
 * It is invalid to use the native primitive interface to retrieve a value that is null, instead a
 * user must check {@link isNullAt} before attempting to retrieve a value that might be null.
 */
public abstract interface Row extends scala.collection.Seq<java.lang.Object>, scala.Serializable {
  /**
   * This method can be used to extract fields from a {@link Row} object in a pattern match. Example:
   * <pre><code>
   * import org.apache.spark.sql._
   *
   * val pairs = sql("SELECT key, value FROM src").rdd.map {
   *   case Row(key: Int, value: String) =&gt;
   *     key -&gt; value
   * }
   * </code></pre>
   */
  static public  scala.Some<scala.collection.Seq<java.lang.Object>> unapplySeq (org.apache.spark.sql.catalyst.expressions.Row row) { throw new RuntimeException(); }
  /**
   * This method can be used to construct a {@link Row} from a {@link Seq} of values.
   */
  static public  org.apache.spark.sql.catalyst.expressions.Row fromSeq (scala.collection.Seq<java.lang.Object> values) { throw new RuntimeException(); }
  public abstract  Object apply (int i) ;
  public abstract  boolean isNullAt (int i) ;
  public abstract  int getInt (int i) ;
  public abstract  long getLong (int i) ;
  public abstract  double getDouble (int i) ;
  public abstract  float getFloat (int i) ;
  public abstract  boolean getBoolean (int i) ;
  public abstract  short getShort (int i) ;
  public abstract  byte getByte (int i) ;
  public abstract  java.lang.String getString (int i) ;
  public  java.lang.String toString () ;
  public abstract  org.apache.spark.sql.catalyst.expressions.Row copy () ;
  /** Returns true if there are any NULL values in this row. */
  public  boolean anyNull () ;
}
