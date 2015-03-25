package org.apache.spark.sql.hive.execution;
/**
 * The wrapper class of Hive input and output schema properties
 */
public  class HiveScriptIOSchema implements org.apache.spark.sql.catalyst.plans.logical.ScriptInputOutputSchema, org.apache.spark.sql.hive.HiveInspectors, scala.Product, scala.Serializable {
  public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> inputRowFormat () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> outputRowFormat () { throw new RuntimeException(); }
  public  java.lang.String inputSerdeClass () { throw new RuntimeException(); }
  public  java.lang.String outputSerdeClass () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> inputSerdeProps () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> outputSerdeProps () { throw new RuntimeException(); }
  public  boolean schemaLess () { throw new RuntimeException(); }
  // not preceding
  public   HiveScriptIOSchema (scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> inputRowFormat, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> outputRowFormat, java.lang.String inputSerdeClass, java.lang.String outputSerdeClass, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> inputSerdeProps, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> outputSerdeProps, boolean schemaLess) { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> defaultFormat () { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> inputRowFormatMap () { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<java.lang.String, java.lang.String> outputRowFormatMap () { throw new RuntimeException(); }
  public  scala.Tuple2<org.apache.hadoop.hive.serde2.AbstractSerDe, org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector> initInputSerDe (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> input) { throw new RuntimeException(); }
  public  scala.Tuple2<org.apache.hadoop.hive.serde2.AbstractSerDe, org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector> initOutputSerDe (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output) { throw new RuntimeException(); }
  public  scala.Tuple2<scala.collection.Seq<java.lang.String>, scala.collection.Seq<org.apache.spark.sql.types.DataType>> parseAttrs (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> attrs) { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.serde2.AbstractSerDe initSerDe (java.lang.String serdeClassName, scala.collection.Seq<java.lang.String> columns, scala.collection.Seq<org.apache.spark.sql.types.DataType> columnTypes, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> serdeProps) { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector initInputSoi (org.apache.hadoop.hive.serde2.AbstractSerDe inputSerde, scala.collection.Seq<java.lang.String> columns, scala.collection.Seq<org.apache.spark.sql.types.DataType> columnTypes) { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector initOutputputSoi (org.apache.hadoop.hive.serde2.AbstractSerDe outputSerde) { throw new RuntimeException(); }
}
