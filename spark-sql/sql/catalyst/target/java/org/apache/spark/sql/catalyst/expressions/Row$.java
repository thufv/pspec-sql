package org.apache.spark.sql.catalyst.expressions;
// no position
public  class Row$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final Row$ MODULE$ = null;
  public   Row$ () { throw new RuntimeException(); }
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
  public  scala.Some<scala.collection.Seq<java.lang.Object>> unapplySeq (org.apache.spark.sql.catalyst.expressions.Row row) { throw new RuntimeException(); }
  /**
   * This method can be used to construct a {@link Row} with the given values.
   */
  public  org.apache.spark.sql.catalyst.expressions.Row apply (scala.collection.Seq<java.lang.Object> values) { throw new RuntimeException(); }
  /**
   * This method can be used to construct a {@link Row} from a {@link Seq} of values.
   */
  public  org.apache.spark.sql.catalyst.expressions.Row fromSeq (scala.collection.Seq<java.lang.Object> values) { throw new RuntimeException(); }
}
