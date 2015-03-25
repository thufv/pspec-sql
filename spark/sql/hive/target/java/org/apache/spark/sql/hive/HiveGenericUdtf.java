package org.apache.spark.sql.hive;
/**
 * Converts a Hive Generic User Defined Table Generating Function (UDTF) to a
 * {@link catalyst.expressions.Generator Generator}.  Note that the semantics of Generators do not allow
 * Generators to maintain state in between input rows.  Thus UDTFs that rely on partitioning
 * dependent operations like calls to <code>close()</code> before producing output will not operate the same as
 * in Hive.  However, in practice this should not affect compatibility for most sane UDTFs
 * (e.g. explode or GenericUDTFParseUrlTuple).
 * <p>
 * Operators that require maintaining state in between input rows should instead be implemented as
 * user defined aggregations, which have clean semantics even in a partitioned execution.
 */
public  class HiveGenericUdtf extends org.apache.spark.sql.catalyst.expressions.Generator implements org.apache.spark.sql.hive.HiveInspectors, scala.Product, scala.Serializable {
  protected  class UDTFCollector implements org.apache.hadoop.hive.ql.udf.generic.Collector {
    public   UDTFCollector () { throw new RuntimeException(); }
    public  scala.collection.mutable.ArrayBuffer<org.apache.spark.sql.Row> collected () { throw new RuntimeException(); }
    public  void collect (java.lang.Object input) { throw new RuntimeException(); }
    public  scala.collection.mutable.ArrayBuffer<org.apache.spark.sql.Row> collectRows () { throw new RuntimeException(); }
  }
  public  org.apache.spark.sql.hive.HiveFunctionWrapper funcWrapper () { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> aliasNames () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  // not preceding
  public   HiveGenericUdtf (org.apache.spark.sql.hive.HiveFunctionWrapper funcWrapper, scala.collection.Seq<java.lang.String> aliasNames, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children) { throw new RuntimeException(); }
  protected  org.apache.hadoop.hive.ql.udf.generic.GenericUDTF function () { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector> inputInspectors () { throw new RuntimeException(); }
  protected  org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector outputInspector () { throw new RuntimeException(); }
  protected  java.lang.Object[] udtInput () { throw new RuntimeException(); }
  protected  scala.collection.mutable.Buffer<org.apache.spark.sql.types.DataType> outputDataTypes () { throw new RuntimeException(); }
  protected  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> makeOutput () { throw new RuntimeException(); }
  public  scala.collection.TraversableOnce<org.apache.spark.sql.Row> eval (org.apache.spark.sql.Row input) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
