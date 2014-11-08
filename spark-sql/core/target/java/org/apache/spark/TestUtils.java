package org.apache.spark;
// no position
/**
 * Utilities for tests. Included in main codebase since it's used by multiple
 * projects.
 * <p>
 * TODO: See if we can move this to the test codebase by specifying
 * test dependencies between projects.
 */
private  class TestUtils {
  static private  class JavaSourceFromString extends javax.tools.SimpleJavaFileObject {
    public  java.lang.String name () { throw new RuntimeException(); }
    public  java.lang.String code () { throw new RuntimeException(); }
    // not preceding
    public   JavaSourceFromString (java.lang.String name, java.lang.String code) { throw new RuntimeException(); }
    public  java.lang.String getCharContent (boolean ignoreEncodingErrors) { throw new RuntimeException(); }
  }
  /**
   * Create a jar that defines classes with the given names.
   * <p>
   * Note: if this is used during class loader tests, class names should be unique
   * in order to avoid interference between tests.
   */
  static public  java.net.URL createJarWithClasses (scala.collection.Seq<java.lang.String> classNames, java.lang.String value) { throw new RuntimeException(); }
  /**
   * Create a jar file that contains this set of files. All files will be located at the root
   * of the jar.
   */
  static public  java.net.URL createJar (scala.collection.Seq<java.io.File> files, java.io.File jarFile) { throw new RuntimeException(); }
  /**
   * Create a jar file that contains this set of files. All files will be located at the root
   * of the jar.
   */
  static private  javax.tools.JavaFileObject.Kind SOURCE () { throw new RuntimeException(); }
  static private  java.net.URI createURI (java.lang.String name) { throw new RuntimeException(); }
  /** Creates a compiled class with the given name. Class file will be placed in destDir. */
  static public  java.io.File createCompiledClass (java.lang.String className, java.io.File destDir, java.lang.String value) { throw new RuntimeException(); }
}
