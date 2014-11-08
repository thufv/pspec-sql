package org.apache.spark.executor;
private  class ChildExecutorURLClassLoader extends java.lang.ClassLoader implements org.apache.spark.executor.MutableURLClassLoader {
  public   ChildExecutorURLClassLoader (java.net.URL[] urls, java.lang.ClassLoader parent) { throw new RuntimeException(); }
  // no position
  private  class userClassLoader extends java.net.URLClassLoader {
    public   userClassLoader () { throw new RuntimeException(); }
    public  void addURL (java.net.URL url) { throw new RuntimeException(); }
    public  java.lang.Class<?> findClass (java.lang.String name) { throw new RuntimeException(); }
  }
  // not preceding
  private  org.apache.spark.executor.ChildExecutorURLClassLoader.userClassLoader$ userClassLoader () { throw new RuntimeException(); }
  private  org.apache.spark.util.ParentClassLoader parentClassLoader () { throw new RuntimeException(); }
  public  java.lang.Class<?> findClass (java.lang.String name) { throw new RuntimeException(); }
  public  void addURL (java.net.URL url) { throw new RuntimeException(); }
  public  java.net.URL[] getURLs () { throw new RuntimeException(); }
}
