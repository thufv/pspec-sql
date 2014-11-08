package org.apache.spark.sql;
public  class TestData implements scala.Product, scala.Serializable {
  static public  class LargeAndSmallInts implements scala.Product, scala.Serializable {
    public  int a () { throw new RuntimeException(); }
    public  int b () { throw new RuntimeException(); }
    // not preceding
    public   LargeAndSmallInts (int a, int b) { throw new RuntimeException(); }
  }
  // no position
  // not preceding
  static public  class LargeAndSmallInts$ extends scala.runtime.AbstractFunction2<java.lang.Object, java.lang.Object, org.apache.spark.sql.TestData.LargeAndSmallInts> implements scala.Serializable {
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
    public   TestData2$ () { throw new RuntimeException(); }
  }
  static public  class UpperCaseData implements scala.Product, scala.Serializable {
    public  int N () { throw new RuntimeException(); }
    public  java.lang.String L () { throw new RuntimeException(); }
    // not preceding
    public   UpperCaseData (int N, java.lang.String L) { throw new RuntimeException(); }
  }
  // no position
  static public  class UpperCaseData$ extends scala.runtime.AbstractFunction2<java.lang.Object, java.lang.String, org.apache.spark.sql.TestData.UpperCaseData> implements scala.Serializable {
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
    public   ArrayData$ () { throw new RuntimeException(); }
  }
  static public  class MapData implements scala.Product, scala.Serializable {
    public  scala.collection.immutable.Map<java.lang.Object, java.lang.String> data () { throw new RuntimeException(); }
    // not preceding
    public   MapData (scala.collection.immutable.Map<java.lang.Object, java.lang.String> data) { throw new RuntimeException(); }
  }
  // no position
  static public  class MapData$ extends scala.runtime.AbstractFunction1<scala.collection.immutable.Map<java.lang.Object, java.lang.String>, org.apache.spark.sql.TestData.MapData> implements scala.Serializable {
    public   MapData$ () { throw new RuntimeException(); }
  }
  static public  class StringData implements scala.Product, scala.Serializable {
    public  java.lang.String s () { throw new RuntimeException(); }
    // not preceding
    public   StringData (java.lang.String s) { throw new RuntimeException(); }
  }
  // no position
  static public  class StringData$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.sql.TestData.StringData> implements scala.Serializable {
    public   StringData$ () { throw new RuntimeException(); }
  }
  static public  class NullInts implements scala.Product, scala.Serializable {
    public  java.lang.Integer a () { throw new RuntimeException(); }
    // not preceding
    public   NullInts (java.lang.Integer a) { throw new RuntimeException(); }
  }
  // no position
  static public  class NullInts$ extends scala.runtime.AbstractFunction1<java.lang.Integer, org.apache.spark.sql.TestData.NullInts> implements scala.Serializable {
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
    public   NullStrings$ () { throw new RuntimeException(); }
  }
  static public  class TableName implements scala.Product, scala.Serializable {
    public  java.lang.String tableName () { throw new RuntimeException(); }
    // not preceding
    public   TableName (java.lang.String tableName) { throw new RuntimeException(); }
  }
  // no position
  static public  class TableName$ extends scala.runtime.AbstractFunction1<java.lang.String, org.apache.spark.sql.TestData.TableName> implements scala.Serializable {
    public   TableName$ () { throw new RuntimeException(); }
  }
  static public  class TimestampField implements scala.Product, scala.Serializable {
    public  java.sql.Timestamp time () { throw new RuntimeException(); }
    // not preceding
    public   TimestampField (java.sql.Timestamp time) { throw new RuntimeException(); }
  }
  // no position
  static public  class TimestampField$ extends scala.runtime.AbstractFunction1<java.sql.Timestamp, org.apache.spark.sql.TestData.TimestampField> implements scala.Serializable {
    public   TimestampField$ () { throw new RuntimeException(); }
  }
  static public  class IntField implements scala.Product, scala.Serializable {
    public  int i () { throw new RuntimeException(); }
    // not preceding
    public   IntField (int i) { throw new RuntimeException(); }
  }
  // no position
  static public  class IntField$ extends scala.runtime.AbstractFunction1<java.lang.Object, org.apache.spark.sql.TestData.IntField> implements scala.Serializable {
    public   IntField$ () { throw new RuntimeException(); }
  }
  static public  org.apache.spark.sql.SchemaRDD testData () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.SchemaRDD largeAndSmallInts () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.SchemaRDD testData2 () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.plans.logical.LocalRelation testData3 () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.plans.logical.LocalRelation emptyTableData () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.UpperCaseData> upperCaseData () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.LowerCaseData> lowerCaseData () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.ArrayData> arrayData () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.MapData> mapData () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.StringData> repeatedData () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.StringData> nullableRepeatedData () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.NullInts> nullInts () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.NullInts> allNulls () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.NullStrings> nullStrings () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<java.lang.String> unparsedStrings () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.TimestampField> timestamps () { throw new RuntimeException(); }
  static public  org.apache.spark.rdd.RDD<org.apache.spark.sql.TestData.IntField> withEmptyParts () { throw new RuntimeException(); }
  public  int key () { throw new RuntimeException(); }
  public  java.lang.String value () { throw new RuntimeException(); }
  // not preceding
  public   TestData (int key, java.lang.String value) { throw new RuntimeException(); }
}
