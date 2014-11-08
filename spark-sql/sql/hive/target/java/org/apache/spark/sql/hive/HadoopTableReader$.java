package org.apache.spark.sql.hive;
// no position
private  class HadoopTableReader$ implements org.apache.spark.sql.hive.HiveInspectors {
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
   * Transform the raw data(Writable object) into the Row object for an iterable input
   * @param iter Iterable input which represented as Writable object
   * @param deserializer Deserializer associated with the input writable object
   * @param attrs Represents the row attribute names and its zero-based position in the MutableRow
   * @param row reusable MutableRow object
   * <p>
   * @return Iterable Row object that transformed from the given iterable input.
   */
  public  scala.collection.Iterator<org.apache.spark.sql.catalyst.expressions.Row> fillObject (scala.collection.Iterator<org.apache.hadoop.io.Writable> iter, org.apache.hadoop.hive.serde2.Deserializer deserializer, scala.collection.Seq<scala.Tuple2<org.apache.spark.sql.catalyst.expressions.Attribute, java.lang.Object>> attrs, org.apache.spark.sql.catalyst.expressions.GenericMutableRow row) { throw new RuntimeException(); }
}
