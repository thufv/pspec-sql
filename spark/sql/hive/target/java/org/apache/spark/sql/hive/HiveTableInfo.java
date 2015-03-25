package org.apache.spark.sql.hive;
public  class HiveTableInfo implements org.apache.spark.sql.catalyst.checker.TableInfo {
  public  org.apache.spark.sql.hive.HiveContext hive () { throw new RuntimeException(); }
  // not preceding
  public   HiveTableInfo (org.apache.spark.sql.hive.HiveContext hive) { throw new RuntimeException(); }
  private  class Table {
    public   Table () { throw new RuntimeException(); }
    private  scala.collection.mutable.Map<java.lang.String, org.apache.spark.sql.catalyst.checker.ColumnInfo> infos () { throw new RuntimeException(); }
    public  org.apache.spark.sql.catalyst.checker.ColumnInfo get (java.lang.String column) { throw new RuntimeException(); }
    public  void put (java.lang.String column, org.apache.spark.sql.catalyst.checker.ColumnInfo range) { throw new RuntimeException(); }
  }
  private  scala.collection.mutable.Map<java.lang.String, scala.collection.mutable.Map<java.lang.String, org.apache.spark.sql.hive.HiveTableInfo.Table>> tableInfos () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.checker.ColumnInfo get (java.lang.String dbName, java.lang.String tableName, java.lang.String columnName) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.checker.ColumnInfo get (java.lang.String tableName, java.lang.String columnName) { throw new RuntimeException(); }
  private  void put (java.lang.String dbName, java.lang.String tableName, java.lang.String columnName, org.apache.spark.sql.catalyst.checker.ColumnInfo info) { throw new RuntimeException(); }
  public  void initialize () { throw new RuntimeException(); }
  private  void queryDatabase (java.lang.String database) { throw new RuntimeException(); }
  private  org.apache.spark.sql.Row queryRange (java.lang.String table, scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.AttributeReference> numeric) { throw new RuntimeException(); }
  private  int queryMultiplicity (java.lang.String table, java.lang.String column) { throw new RuntimeException(); }
}
