package org.apache.spark.sql.hive;
// no position
public  class HadoopTableReader$ implements org.apache.spark.sql.hive.HiveInspectors {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final HadoopTableReader$ MODULE$ = null;
  public   HadoopTableReader$ () { throw new RuntimeException(); }
  /**
   * Curried. After given an argument for 'path', the resulting JobConf => Unit closure is used to
   * instantiate a HadoopRDD.
   */
  public  void initializeLocalJobConfFunc (java.lang.String path, org.apache.hadoop.hive.ql.plan.TableDesc tableDesc, org.apache.hadoop.mapred.JobConf jobConf) { throw new RuntimeException(); }
  /**
   * Transform all given raw <code>Writable</code>s into <code>Row</code>s.
   * <p>
   * @param iterator Iterator of all <code>Writable</code>s to be transformed
   * @param deserializer The <code>Deserializer</code> associated with the input <code>Writable</code>
   * @param nonPartitionKeyAttrs Attributes that should be filled together with their corresponding
   *                             positions in the output schema
   * @param mutableRow A reusable <code>MutableRow</code> that should be filled
   * @return An <code>Iterator[Row]</code> transformed from <code>iterator</code>
   */
  public  scala.collection.Iterator<org.apache.spark.sql.Row> fillObject (scala.collection.Iterator<org.apache.hadoop.io.Writable> iterator, org.apache.hadoop.hive.serde2.Deserializer deserializer, scala.collection.Seq<scala.Tuple2<org.apache.spark.sql.catalyst.expressions.Attribute, java.lang.Object>> nonPartitionKeyAttrs, org.apache.spark.sql.catalyst.expressions.MutableRow mutableRow) { throw new RuntimeException(); }
}
