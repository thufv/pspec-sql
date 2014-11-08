package org.apache.spark.sql.catalyst.checker;
public  interface MetaRegistry {
  static private  org.apache.spark.sql.catalyst.checker.MetaRegistry instance () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.catalyst.checker.MetaRegistry get () { throw new RuntimeException(); }
  public abstract  org.apache.spark.sql.catalyst.checker.DataLabel lookup (java.lang.String database, java.lang.String table, java.lang.String column) ;
  public abstract  edu.thu.ss.lang.pojo.DesensitizeOperation lookup (java.lang.String udf, java.lang.reflect.Method method) ;
}
