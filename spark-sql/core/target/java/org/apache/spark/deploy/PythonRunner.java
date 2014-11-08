package org.apache.spark.deploy;
// no position
/**
 * A main class used by spark-submit to launch Python applications. It executes python as a
 * subprocess and then has it connect back to the JVM to access system properties, etc.
 */
public  class PythonRunner {
  static public  void main (java.lang.String[] args) { throw new RuntimeException(); }
  /**
   * Format the python file path so that it can be added to the PYTHONPATH correctly.
   * <p>
   * Python does not understand URI schemes in paths. Before adding python files to the
   * PYTHONPATH, we need to extract the path from the URI. This is safe to do because we
   * currently only support local python files.
   */
  static public  java.lang.String formatPath (java.lang.String path, boolean testWindows) { throw new RuntimeException(); }
  /**
   * Format each python file path in the comma-delimited list of paths, so it can be
   * added to the PYTHONPATH correctly.
   */
  static public  java.lang.String[] formatPaths (java.lang.String paths, boolean testWindows) { throw new RuntimeException(); }
}
