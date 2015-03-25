package org.apache.spark.sql.hive;
/**
 * This class provides the UDF creation and also the UDF instance serialization and
 * de-serialization cross process boundary.
 * <p>
 * Detail discussion can be found at https://github.com/apache/spark/pull/3640
 * <p>
 * @param functionClassName UDF class name
 */
public  class HiveFunctionWrapper implements java.io.Externalizable, scala.Product, scala.Serializable {
  public  java.lang.String functionClassName () { throw new RuntimeException(); }
  // not preceding
  public   HiveFunctionWrapper (java.lang.String functionClassName) { throw new RuntimeException(); }
  public   HiveFunctionWrapper () { throw new RuntimeException(); }
  private  java.lang.reflect.Method methodDeSerialize () { throw new RuntimeException(); }
  private  java.lang.reflect.Method methodSerialize () { throw new RuntimeException(); }
  public <UDFType extends java.lang.Object> UDFType deserializePlan (java.io.InputStream is, java.lang.Class<?> clazz) { throw new RuntimeException(); }
  public  void serializePlan (java.lang.Object function, java.io.OutputStream out) { throw new RuntimeException(); }
  private  java.lang.Object instance () { throw new RuntimeException(); }
  public  void writeExternal (java.io.ObjectOutput out) { throw new RuntimeException(); }
  public  void readExternal (java.io.ObjectInput in) { throw new RuntimeException(); }
  public <UDFType extends java.lang.Object> UDFType createFunction () { throw new RuntimeException(); }
}
