package org.apache.spark.serializer;
// no position
public  class KryoDistributedTest {
  static public  class AppJarRegistrator implements org.apache.spark.serializer.KryoRegistrator {
    public   AppJarRegistrator () { throw new RuntimeException(); }
    public  void registerClasses (com.esotericsoftware.kryo.Kryo k) { throw new RuntimeException(); }
  }
  // no position
  static public  class AppJarRegistrator$ {
    public   AppJarRegistrator$ () { throw new RuntimeException(); }
    public  java.lang.String customClassName () { throw new RuntimeException(); }
  }
  static public  class MyCustomClass {
    public   MyCustomClass () { throw new RuntimeException(); }
  }
}
