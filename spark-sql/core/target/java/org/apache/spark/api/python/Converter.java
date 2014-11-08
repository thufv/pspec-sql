package org.apache.spark.api.python;
/**
 * :: Experimental ::
 * A trait for use with reading custom classes in PySpark. Implement this trait and add custom
 * transformation code by overriding the convert method.
 */
public  interface Converter<T extends java.lang.Object, U extends java.lang.Object> extends scala.Serializable {
  static public  org.apache.spark.api.python.Converter<java.lang.Object, java.lang.Object> getInstance (scala.Option<java.lang.String> converterClass, org.apache.spark.api.python.Converter<java.lang.Object, java.lang.Object> defaultConverter) { throw new RuntimeException(); }
  public abstract  U convert (T obj) ;
}
