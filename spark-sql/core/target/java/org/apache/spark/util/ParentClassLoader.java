package org.apache.spark.util;
/**
 * A class loader which makes findClass accesible to the child
 */
private  class ParentClassLoader extends java.lang.ClassLoader {
  public   ParentClassLoader (java.lang.ClassLoader parent) { throw new RuntimeException(); }
  public  java.lang.Class<?> findClass (java.lang.String name) { throw new RuntimeException(); }
  public  java.lang.Class<?> loadClass (java.lang.String name) { throw new RuntimeException(); }
}
