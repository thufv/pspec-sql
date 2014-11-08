package org.apache.spark.sql.api.java;
/**
 * A collection of functions that allow Java users to register UDFs.  In order to handle functions
 * of varying airities with minimal boilerplate for our users, we generate classes and functions
 * for each airity up to 22.  The code for this generation can be found in comments in this trait.
 */
private abstract interface UDFRegistration {
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF1<?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF2<?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF3<?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF4<?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF5<?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF6<?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF7<?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF8<?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF9<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF11<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF12<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF13<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF14<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF15<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF16<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF17<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF18<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF19<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF20<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF21<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
  public  void registerFunction (java.lang.String name, org.apache.spark.sql.api.java.UDF22<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?> f, org.apache.spark.sql.api.java.DataType dataType) ;
}
