package org.apache.spark.serializer;
// no position
public  class KryoTest {
  static public  class CaseClass implements scala.Product, scala.Serializable {
    public  int i () { throw new RuntimeException(); }
    public  java.lang.String s () { throw new RuntimeException(); }
    // not preceding
    public   CaseClass (int i, java.lang.String s) { throw new RuntimeException(); }
  }
  // no position
  static public  class CaseClass$ extends scala.runtime.AbstractFunction2<java.lang.Object, java.lang.String, org.apache.spark.serializer.KryoTest.CaseClass> implements scala.Serializable {
    public   CaseClass$ () { throw new RuntimeException(); }
  }
  static public  class ClassWithNoArgConstructor {
    public   ClassWithNoArgConstructor () { throw new RuntimeException(); }
    public  int x () { throw new RuntimeException(); }
    public  boolean equals (Object other) { throw new RuntimeException(); }
  }
  static public  class ClassWithoutNoArgConstructor {
    public  int x () { throw new RuntimeException(); }
    // not preceding
    public   ClassWithoutNoArgConstructor (int x) { throw new RuntimeException(); }
    public  boolean equals (Object other) { throw new RuntimeException(); }
  }
  static public  class MyRegistrator implements org.apache.spark.serializer.KryoRegistrator {
    public   MyRegistrator () { throw new RuntimeException(); }
    public  void registerClasses (com.esotericsoftware.kryo.Kryo k) { throw new RuntimeException(); }
  }
}
