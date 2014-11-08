package org.apache.spark.sql.execution;
/**
 * A serialized version of a Python lambda function.  Suitable for use in a {@link PythonRDD}.
 */
private  class PythonUDF extends org.apache.spark.sql.catalyst.expressions.Expression implements org.apache.spark.Logging, scala.Product, scala.Serializable {
  public  java.lang.String name () { throw new RuntimeException(); }
  public  byte[] command () { throw new RuntimeException(); }
  public  java.util.Map<java.lang.String, java.lang.String> envVars () { throw new RuntimeException(); }
  public  java.util.List<java.lang.String> pythonIncludes () { throw new RuntimeException(); }
  public  java.lang.String pythonExec () { throw new RuntimeException(); }
  public  org.apache.spark.Accumulator<java.util.List<byte[]>> accumulator () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType dataType () { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  // not preceding
  public   PythonUDF (java.lang.String name, byte[] command, java.util.Map<java.lang.String, java.lang.String> envVars, java.util.List<java.lang.String> pythonIncludes, java.lang.String pythonExec, org.apache.spark.Accumulator<java.util.List<byte[]>> accumulator, org.apache.spark.sql.catalyst.types.DataType dataType, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
  public  boolean nullable () { throw new RuntimeException(); }
  public  scala.Nothing eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
