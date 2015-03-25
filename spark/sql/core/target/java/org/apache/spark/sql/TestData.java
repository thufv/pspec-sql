package org.apache.spark.sql;
public  class TestData implements scala.Product, scala.Serializable {
  static public  class LargeAndSmallInts implements scala.Product, scala.Serializable {
    public  int a () { throw new RuntimeException(); }
    public  int b () { throw new RuntimeException(); }
    // not preceding
    public   LargeAndSmallInts (int a, int b) { throw new RuntimeException(); }
  }
  // no position
  static public  class LargeAndSmallInts$ extends scala.runtime.AbstractFunction2<java.lang.Object, java.lang.Object, org.apache.spark.sql.TestData.LargeAndSmallInts> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final LargeAndSmallInts$ MODULE$ = null;
    public   LargeAndSmallInts$ () { throw new RuntimeException(); }
  }
  static public  class TestData2 implements scala.Product, scala.Serializable {
    public  int a () { throw new RuntimeException(); }
    public  int b () { throw new RuntimeException(); }
    // not preceding
    public   TestData2 (int a, int b) { throw new RuntimeException(); }
  }
  // no position
  static public  class TestData2$ extends scala.runtime.AbstractFunction2<java.lang.Object, java.lang.Object, org.apache.spark.sql.TestData.TestData2> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final TestData2$ MODULE$ = null;
    public   TestData2$ () { throw new RuntimeException(); }
  }
  static public  class DecimalData implements scala.Product, scala.Serializable {
    public  scala.math.BigDecimal a () { throw new RuntimeException(); }
    public  scala.math.BigDecimal b () { throw new RuntimeException(); }
    // not preceding
    public   DecimalData (scala.math.BigDecimal a, scala.math.BigDecimal b) { throw new RuntimeException(); }
  }
  // no position
  static public  class DecimalData$ extends scala.runtime.AbstractFunction2<scala.math.BigDecimal, scala.math.BigDecimal, org.apache.spark.sql.TestData.DecimalData> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final DecimalData$ MODULE$ = null;
    public   DecimalData$ () { throw new RuntimeException(); }
  }
  static public  class BinaryData implements scala.Product, scala.Serializable {
    public  byte[] a () { throw new RuntimeException(); }
    public  int b () { throw new RuntimeException(); }
    // not preceding
    public   BinaryData (byte[] a, int b) { throw new RuntimeException(); }
  }
  // no position
  static public  class BinaryData$ extends scala.runtime.AbstractFunction2<byte[], java.lang.Object, org.apache.spark.sql.TestData.BinaryData> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final BinaryData$ MODULE$ = null;
    public   BinaryData$ () { throw new RuntimeException(); }
  }
  static public  class TestData3 implements scala.Product, scala.Serializable {
    public  int a () { throw new RuntimeException(); }
    public  scala.Option<java.lang.Object> b () { throw new RuntimeException(); }
    // not preceding
    public   TestData3 (int a, scala.Option<java.lang.Object> b) { throw new RuntimeException(); }
  }
  // no position
  static public  class TestData3$ extends scala.runtime.AbstractFunction2<java.lang.Object, scala.Option<java.lang.Object>, org.apache.spark.sql.TestData.TestData3> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final TestData3$ MODULE$ = null;
    public   TestData3$ () { throw new RuntimeException(); }
  }
  static public  class UpperCaseData implements scala.Product, scala.Serializable {
    public  int N () { throw new RuntimeException(); }
    public  java.lang.String L () { throw new RuntimeException(); }
    // not preceding
    public   UpperCaseData (int N, java.lang.String L) { throw new RuntimeException(); }
  }
  // no position
  static public  class UpperCaseData$ extends scala.runtime.AbstractFunction2<java.lang.Object, java.lang.String, org.apache.spark.sql.TestData.UpperCaseData> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final UpperCaseData$ MODULE$ = null;
    public   UpperCaseData$ () { throw new RuntimeException(); }
  }
  static public  class LowerCaseData implements scala.Product, scala.Serializable {
    public  int n () { throw new RuntimeException(); }
    public  java.lang.String l () { throw new RuntimeException(); }
    // not preceding
    public   LowerCaseData (int n, java.lang.String l) { throw new RuntimeException(); }
  }
  // no position
  static public  class LowerCaseData$ extends scala.runtime.AbstractFunction2<java.lang.Object, java.lang.String, org.apache.spark.sql.TestData.LowerCaseData> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final LowerCaseData$ MODULE$ = null;
    public   LowerCaseData$ () { throw new RuntimeException(); }
  }
  static public  class ArrayData implements scala.Product, scala.Serializable {
    public  scala.collection.Seq<java.lang.Object> data () { throw new RuntimeException(); }
    public  scala.collection.Seq<scala.collection.Seq<java.lang.Object>> nestedData () { throw new RuntimeException(); }
    // not preceding
    public   ArrayData (scala.collection.Seq<java.lang.Object> data, scala.collection.Seq<scala.collection.Seq<java.lang.Object>> nestedData) { throw new RuntimeException(); }
  }
  // no position
  static public  class ArrayData$ extends scala.runtime.AbstractFunction2<scala.collection.Seq<java.lang.Object>, scala.collection.Seq<scala.collection.Seq<java.lang.Object>>, org.apache.spark.sql.TestData.ArrayData> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final ArrayData$ MODULE$ = null;
    public   ArrayData$ () { throw new RuntimeException(); }
  }
  static public  class MapData implements scala.Product, scala.Serializable {
    public  scala.collection.Map<java.lang.Object, java.lang.String> data () { throw new RuntimeException(); }
    // not preceding
    public   MapData (scala.collection.Map<java.lang.Object, java.lang.String> data) { throw new RuntimeException(); }
  }
  // no position
  static public  class MapData$ extends scala.runtime.AbstractFunction1<scala.collection.Map<java.lang.Object, java.lang.String>, org.apache.spark.sql.TestData.MapData> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final MapData$ MODULE$ = null;
    public   MapData$ () { throw new RuntimeException(); }
  }
  static public  class StringData implements scala.Product, scala.Serializable {
    public  java.lang.String s () { throw new RuntimeException(); }
    // not preceding
    public   StringData (java.lang.String s) { throw new RuntimeException(); }
  }
  // no position
  static public  class StringData$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.sql.TestData.StringData> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final StringData$ MODULE$ = null;
    public   StringData$ () { throw new RuntimeException(); }
  }
  static public  class NullInts implements scala.Product, scala.Serializable {
    public  java.lang.Integer a () { throw new RuntimeException(); }
    // not preceding
    public   NullInts (java.lang.Integer a) { throw new RuntimeException(); }
  }
  // no position
  static public  class NullInts$ extends scala.runtime.AbstractFunction1<java.lang.Integer, org.apache.spark.sql.TestData.NullInts> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final NullInts$ MODULE$ = null;
    public   NullInts$ () { throw new RuntimeException(); }
  }
  static public  class NullStrings implements scala.Product, scala.Serializable {
    public  int n () { throw new RuntimeException(); }
    public  java.lang.String s () { throw new RuntimeException(); }
    // not preceding
    public   NullStrings (int n, java.lang.String s) { throw new RuntimeException(); }
  }
  // no position
  static public  class NullStrings$ extends scala.runtime.AbstractFunction2<java.lang.Object, java.lang.String, org.apache.spark.sql.TestData.NullStrings> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final NullStrings$ MODULE$ = null;
    public   NullStrings$ () { throw new RuntimeException(); }
  }
  static public  class TableName implements scala.Product, scala.Serializable {
    public  java.lang.String tableName () { throw new RuntimeException(); }
    // not preceding
    public   TableName (java.lang.String tableName) { throw new RuntimeException(); }
  }
  // no position
  static public  class TableName$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.sql.TestData.TableName> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final TableName$ MODULE$ = null;
    public   TableName$ () { throw new RuntimeException(); }
  }
  static public  class TimestampField implements scala.Product, scala.Serializable {
    public  java.sql.Timestamp time () { throw new RuntimeException(); }
    // not preceding
    public   TimestampField (java.sql.Timestamp time) { throw new RuntimeException(); }
  }
  // no position
  static public  class TimestampField$ extends scala.runtime.AbstractFunction1<java.sql.Timestamp, org.apache.spark.sql.TestData.TimestampField> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final TimestampField$ MODULE$ = null;
    public   TimestampField$ () { throw new RuntimeException(); }
  }
  static public  class IntField implements scala.Product, scala.Serializable {
    public  int i () { throw new RuntimeException(); }
    // not preceding
    public   IntField (int i) { throw new RuntimeException(); }
  }
  // no position
  static public  class IntField$ extends scala.runtime.AbstractFunction1<java.lang.Object, org.apache.spark.sql.TestData.IntField> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final IntField$ MODULE$ = null;
    public   IntField$ () { throw new RuntimeException(); }
  }
  static public  class Person implements scala.Product, scala.Serializable {
    public  int id () { throw new RuntimeException(); }
    public  java.lang.String name () { throw new RuntimeException(); }
    public  int age () { throw new RuntimeException(); }
    // not preceding
    public   Person (int id, java.lang.String name, int age) { throw new RuntimeException(); }
  }
  // no position
  static public  class Person$ extends scala.runtime.AbstractFunction3<java.lang.Object, java.lang.String, java.lang.Object, org.apache.spark.sql.TestData.Person> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final Person$ MODULE$ = null;
    public   Person$ () { throw new RuntimeException(); }
  }
  static public  class Salary implements scala.Product, scala.Serializable {
    public  int personId () { throw new RuntimeException(); }
    public  double salary () { throw new RuntimeException(); }
    // not preceding
    public   Salary (int personId, double salary) { throw new RuntimeException(); }
  }
  // no position
  static public  class Salary$ extends scala.runtime.AbstractFunction2<java.lang.Object, java.lang.Object, org.apache.spark.sql.TestData.Salary> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final Salary$ MODULE$ = null;
    public   Salary$ () { throw new RuntimeException(); }
  }
  static public  class ComplexData implements scala.Product, scala.Serializable {
    public  scala.collection.immutable.Map<java.lang.Object, java.lang.String> m () { throw new RuntimeException(); }
    public  org.apache.spark.sql.TestData s () { throw new RuntimeException(); }
    public  scala.collection.Seq<java.lang.Object> a () { throw new RuntimeException(); }
    public  boolean b () { throw new RuntimeException(); }
    // not preceding
    public   ComplexData (scala.collection.immutable.Map<java.lang.Object, java.lang.String> m, org.apache.spark.sql.TestData s, scala.collection.Seq<java.lang.Object> a, boolean b) { throw new RuntimeException(); }
  }
  // no position
  static public  class ComplexData$ extends scala.runtime.AbstractFunction4<scala.collection.immutable.Map<java.lang.Object, java.lang.String>, org.apache.spark.sql.TestData, scala.collection.Seq<java.lang.Object>, java.lang.Object, org.apache.spark.sql.TestData.ComplexData> implements scala.Serializable {
    /**
     * Static reference to the singleton instance of this Scala object.
     */
    public static final ComplexData$ MODULE$ = null;
    public   ComplexData$ () { throw new RuntimeException(); }
  }
  static public  org.apache.spark.sql.DataFrame testData () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame negativeData () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame largeAndSmallInts () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame testData2 () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame decimalData () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame binaryData () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame testData3 () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.plans.logical.LocalRelation emptyTableData () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame upperCaseData () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame lowerCaseData () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.ArrayData> arrayData () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.MapData> mapData () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.StringData> repeatedData () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.StringData> nullableRepeatedData () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame nullInts () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame allNulls () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame nullStrings () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<java.lang.String> unparsedStrings () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.TimestampField> timestamps () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.IntField> withEmptyParts () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame person () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame salary () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.DataFrame complexData () { throw new RuntimeException(); }
  public  int key () { throw new RuntimeException(); }
  public  java.lang.String value () { throw new RuntimeException(); }
  // not preceding
  public   TestData (int key, java.lang.String value) { throw new RuntimeException(); }
}
