package org.apache.spark.sql.hive;
/**
 * 1. The Underlying data type in catalyst and in Hive
 * In catalyst:
 *  Primitive  =>
 *     java.lang.String
 *     int / scala.Int
 *     boolean / scala.Boolean
 *     float / scala.Float
 *     double / scala.Double
 *     long / scala.Long
 *     short / scala.Short
 *     byte / scala.Byte
 *     org.apache.spark.sql.types.Decimal
 *     Array[Byte]
 *     java.sql.Date
 *     java.sql.Timestamp
 *  Complex Types =>
 *    Map: scala.collection.immutable.Map
 *    List: scala.collection.immutable.Seq
 *    Struct:
 *           org.apache.spark.sql.catalyst.expression.Row
 *    Union: NOT SUPPORTED YET
 *  The Complex types plays as a container, which can hold arbitrary data types.
 * <p>
 * In Hive, the native data types are various, in UDF/UDAF/UDTF, and associated with
 * Object Inspectors, in Hive expression evaluation framework, the underlying data are
 * Primitive Type
 *   Java Boxed Primitives:
 *       org.apache.hadoop.hive.common.type.HiveVarchar
 *       java.lang.String
 *       java.lang.Integer
 *       java.lang.Boolean
 *       java.lang.Float
 *       java.lang.Double
 *       java.lang.Long
 *       java.lang.Short
 *       java.lang.Byte
 *       org.apache.hadoop.hive.common.<code>type</code>.HiveDecimal
 *       byte[]
 *       java.sql.Date
 *       java.sql.Timestamp
 *   Writables:
 *       org.apache.hadoop.hive.serde2.io.HiveVarcharWritable
 *       org.apache.hadoop.io.Text
 *       org.apache.hadoop.io.IntWritable
 *       org.apache.hadoop.hive.serde2.io.DoubleWritable
 *       org.apache.hadoop.io.BooleanWritable
 *       org.apache.hadoop.io.LongWritable
 *       org.apache.hadoop.io.FloatWritable
 *       org.apache.hadoop.hive.serde2.io.ShortWritable
 *       org.apache.hadoop.hive.serde2.io.ByteWritable
 *       org.apache.hadoop.io.BytesWritable
 *       org.apache.hadoop.hive.serde2.io.DateWritable
 *       org.apache.hadoop.hive.serde2.io.TimestampWritable
 *       org.apache.hadoop.hive.serde2.io.HiveDecimalWritable
 * Complex Type
 *   List: Object[] / java.util.List
 *   Map: java.util.Map
 *   Struct: Object[] / java.util.List / java POJO
 *   Union: class StandardUnion { byte tag; Object object }
 * <p>
 * NOTICE: HiveVarchar is not supported by catalyst, it will be simply considered as String type.
 * <p>
 * 2. Hive ObjectInspector is a group of flexible APIs to inspect value in different data
 *  representation, and developers can extend those API as needed, so technically,
 *  object inspector supports arbitrary data type in java.
 * <p>
 * Fortunately, only few built-in Hive Object Inspectors are used in generic udf/udaf/udtf
 * evaluation.
 * 1) Primitive Types (PrimitiveObjectInspector & its sub classes)
 <pre><code>
 public interface PrimitiveObjectInspector {
 // Java Primitives (java.lang.Integer, java.lang.String etc.)
 Object getPrimitiveJavaObject(Object o);
 // Writables (hadoop.io.IntWritable, hadoop.io.Text etc.)
 Object getPrimitiveWritableObject(Object o);
 // ObjectInspector only inspect the `writable` always return true, we need to check it
 // before invoking the methods above.
 boolean preferWritable();
 ...
 }
 </code></pre>
 * <p>
 * 2) Complex Types:
 *   ListObjectInspector: inspects java array or {@link java.util.List}
 *   MapObjectInspector: inspects {@link java.util.Map}
 *   Struct.StructObjectInspector: inspects java array, {@link java.util.List} and
 *                                 even a normal java object (POJO)
 *   UnionObjectInspector: (tag: Int, object data) (TODO: not supported by SparkSQL yet)
 * <p>
 * 3) ConstantObjectInspector: 
 * Constant object inspector can be either primitive type or Complex type, and it bundles a
 * constant value as its property, usually the value is created when the constant object inspector
 * constructed.
 * <pre><code>
 public interface ConstantObjectInspector extends ObjectInspector {
 Object getWritableConstantValue();
 ...
 }
 </code></pre>
 * Hive provides 3 built-in constant object inspectors:
 * Primitive Object Inspectors: 
 *     WritableConstantStringObjectInspector
 *     WritableConstantHiveVarcharObjectInspector
 *     WritableConstantHiveDecimalObjectInspector
 *     WritableConstantTimestampObjectInspector
 *     WritableConstantIntObjectInspector
 *     WritableConstantDoubleObjectInspector
 *     WritableConstantBooleanObjectInspector
 *     WritableConstantLongObjectInspector
 *     WritableConstantFloatObjectInspector
 *     WritableConstantShortObjectInspector
 *     WritableConstantByteObjectInspector
 *     WritableConstantBinaryObjectInspector
 *     WritableConstantDateObjectInspector
 * Map Object Inspector: 
 *     StandardConstantMapObjectInspector
 * List Object Inspector: 
 *     StandardConstantListObjectInspector}
 * Struct Object Inspector: Hive doesn't provide the built-in constant object inspector for Struct
 * Union Object Inspector: Hive doesn't provide the built-in constant object inspector for Union
 * <p>
 * 3. This trait facilitates:
 *    Data Unwrapping: Hive Data => Catalyst Data (unwrap)
 *    Data Wrapping: Catalyst Data => Hive Data (wrap)
 *    Binding the Object Inspector for Catalyst Data (toInspector)
 *    Retrieving the Catalyst Data Type from Object Inspector (inspectorToDataType)
 * <p>
 * 4. Future Improvement (TODO)
 *   This implementation is quite ugly and inefficient:
 *     a. Pattern matching in runtime
 *     b. Small objects creation in catalyst data => writable
 *     c. Unnecessary unwrap / wrap for nested UDF invoking:
 *       e.g. date_add(printf("%s-%s-%s", a,b,c), 3)
 *       We don't need to unwrap the data for printf and wrap it again and passes in data_add
 */
public  interface HiveInspectors {
  public  org.apache.spark.sql.types.DataType javaClassToDataType (java.lang.Class<?> clz) ;
  /**
   * Converts hive types to native catalyst types.
   * @param data the data in Hive type
   * @param oi   the ObjectInspector associated with the Hive Type
   * @return     convert the data into catalyst type
   * TODO return the function of (data => Any) instead for performance consideration
   * <p>
   * Strictly follows the following order in unwrapping (constant OI has the higher priority):
   *  Constant Null object inspector =>
   *    return null
   *  Constant object inspector =>
   *    extract the value from constant object inspector
   *  Check whether the <code>data</code> is null =>
   *    return null if true
   *  If object inspector prefers writable =>
   *    extract writable from <code>data</code> and then get the catalyst type from the writable
   *  Extract the java object directly from the object inspector
   * <p>
   *  NOTICE: the complex data type requires recursive unwrapping.
   */
  public  Object unwrap (Object data, org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector oi) ;
  /**
   * Wraps with Hive types based on object inspector.
   * TODO: Consolidate all hive OI/data interface code.
   */
  public  scala.Function1<java.lang.Object, java.lang.Object> wrapperFor (org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector oi) ;
  /**
   * Converts native catalyst types to the types expected by Hive
   * @param a the value to be wrapped
   * @param oi This ObjectInspector associated with the value returned by this function, and
   *           the ObjectInspector should also be consistent with those returned from
   *           toInspector: DataType => ObjectInspector and
   *           toInspector: Expression => ObjectInspector
   * <p>
   * Strictly follows the following order in wrapping (constant OI has the higher priority):
   *   Constant object inspector => return the bundled value of Constant object inspector
   *   Check whether the <code>a</code> is null => return null if true
   *   If object inspector prefers writable object => return a Writable for the given data <code>a</code>
   *   Map the catalyst data to the boxed java primitive
   * <p>
   *  NOTICE: the complex data type requires recursive wrapping.
   */
  public  java.lang.Object wrap (Object a, org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector oi) ;
  public  java.lang.Object[] wrap (org.apache.spark.sql.Row row, scala.collection.Seq<org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector> inspectors, java.lang.Object[] cache) ;
  public  java.lang.Object[] wrap (scala.collection.Seq<java.lang.Object> row, scala.collection.Seq<org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector> inspectors, java.lang.Object[] cache) ;
  /**
   * @param dataType Catalyst data type
   * @return Hive java object inspector (recursively), not the Writable ObjectInspector
   * We can easily map to the Hive built-in object inspector according to the data type.
   */
  public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector toInspector (org.apache.spark.sql.types.DataType dataType) ;
  /**
   * Map the catalyst expression to ObjectInspector, however,
   * if the expression is {@link Literal} or foldable, a constant writable object inspector returns;
   * Otherwise, we always get the object inspector according to its data type(in catalyst)
   * @param expr Catalyst expression to be mapped
   * @return Hive java objectinspector (recursively).
   */
  public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector toInspector (org.apache.spark.sql.catalyst.expressions.Expression expr) ;
  public  org.apache.spark.sql.types.DataType inspectorToDataType (org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector inspector) ;
  public  class typeInfoConversions {
    public   typeInfoConversions (org.apache.spark.sql.types.DataType dt) { throw new RuntimeException(); }
    public  org.apache.hadoop.hive.serde2.typeinfo.TypeInfo toTypeInfo () { throw new RuntimeException(); }
  }
}
