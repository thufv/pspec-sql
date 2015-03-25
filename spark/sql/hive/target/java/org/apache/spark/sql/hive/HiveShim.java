package org.apache.spark.sql.hive;
// no position
/**
 * A compatibility layer for interacting with Hive version 0.13.1.
 */
public  class HiveShim {
  static public  java.lang.String version () { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.ql.plan.TableDesc getTableDesc (java.lang.Class<? extends org.apache.hadoop.hive.serde2.Deserializer> serdeClass, java.lang.Class<? extends org.apache.hadoop.mapred.InputFormat<?, ?>> inputFormatClass, java.lang.Class<?> outputFormatClass, java.util.Properties properties) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getStringWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getIntWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getDoubleWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getBooleanWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getLongWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getFloatWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getShortWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getByteWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getBinaryWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getDateWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getTimestampWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getDecimalWritableConstantObjectInspector (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getPrimitiveNullWritableConstantObjectInspector () { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.Text getStringWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.IntWritable getIntWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.io.DoubleWritable getDoubleWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.BooleanWritable getBooleanWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.LongWritable getLongWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.FloatWritable getFloatWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.io.ShortWritable getShortWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.io.ByteWritable getByteWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.BytesWritable getBinaryWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.io.DateWritable getDateWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.io.TimestampWritable getTimestampWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.io.HiveDecimalWritable getDecimalWritable (Object value) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.NullWritable getPrimitiveNullWritable () { throw new RuntimeException(); }
  static public  java.util.ArrayList<java.lang.Object> createDriverResultsArray () { throw new RuntimeException(); }
  static public  scala.collection.mutable.Buffer<java.lang.String> processResults (java.util.ArrayList<java.lang.Object> results) { throw new RuntimeException(); }
  static public  java.lang.String getStatsSetupConstTotalSize () { throw new RuntimeException(); }
  static public  java.lang.String getStatsSetupConstRawDataSize () { throw new RuntimeException(); }
  static public  scala.collection.Seq<java.lang.String> createDefaultDBIfNeeded (org.apache.spark.sql.hive.HiveContext context) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.ql.processors.CommandProcessor getCommandProcessor (java.lang.String[] cmd, org.apache.hadoop.hive.conf.HiveConf conf) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.common.type.HiveDecimal createDecimal (java.math.BigDecimal bd) { throw new RuntimeException(); }
  static private  void appendReadColumnNames (org.apache.hadoop.conf.Configuration conf, scala.collection.Seq<java.lang.String> cols) { throw new RuntimeException(); }
  static public  void appendReadColumns (org.apache.hadoop.conf.Configuration conf, scala.collection.Seq<java.lang.Integer> ids, scala.collection.Seq<java.lang.String> names) { throw new RuntimeException(); }
  static public  org.apache.hadoop.fs.Path getExternalTmpPath (org.apache.hadoop.hive.ql.Context context, org.apache.hadoop.fs.Path path) { throw new RuntimeException(); }
  static public  org.apache.hadoop.fs.Path getDataLocationPath (org.apache.hadoop.hive.ql.metadata.Partition p) { throw new RuntimeException(); }
  static public  java.util.Set<org.apache.hadoop.hive.ql.metadata.Partition> getAllPartitionsOf (org.apache.hadoop.hive.ql.metadata.Hive client, org.apache.hadoop.hive.ql.metadata.Table tbl) { throw new RuntimeException(); }
  static public  scala.collection.Seq<scala.runtime.Nothing$> compatibilityBlackList () { throw new RuntimeException(); }
  static public  void setLocation (org.apache.hadoop.hive.ql.metadata.Table tbl, org.apache.hadoop.hive.ql.plan.CreateTableDesc crtTbl) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.ql.plan.FileSinkDesc wrapperToFileSinkDesc (org.apache.spark.sql.hive.ShimFileSinkDesc w) { throw new RuntimeException(); }
  static private  int UNLIMITED_DECIMAL_PRECISION () { throw new RuntimeException(); }
  static private  int UNLIMITED_DECIMAL_SCALE () { throw new RuntimeException(); }
  static public  java.lang.String decimalMetastoreString (org.apache.spark.sql.types.DecimalType decimalType) { throw new RuntimeException(); }
  static public  org.apache.hadoop.hive.serde2.typeinfo.TypeInfo decimalTypeInfo (org.apache.spark.sql.types.DecimalType decimalType) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.types.DecimalType decimalTypeInfoToCatalyst (org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector inspector) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.types.Decimal toCatalystDecimal (org.apache.hadoop.hive.serde2.objectinspector.primitive.HiveDecimalObjectInspector hdoi, Object data) { throw new RuntimeException(); }
  static public  org.apache.hadoop.io.Writable prepareWritable (org.apache.hadoop.io.Writable w) { throw new RuntimeException(); }
  static public  Object setTblNullFormat (org.apache.hadoop.hive.ql.plan.CreateTableDesc crtTbl, org.apache.hadoop.hive.ql.metadata.Table tbl) { throw new RuntimeException(); }
}
