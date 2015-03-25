package org.apache.spark.sql.hive.thriftserver;
public abstract class HiveThriftJdbcTest extends org.apache.spark.sql.hive.thriftserver.HiveThriftServer2Test {
  public   HiveThriftJdbcTest () { throw new RuntimeException(); }
  private  java.lang.String jdbcUri () { throw new RuntimeException(); }
  protected  void withJdbcStatement (scala.Function1<java.sql.Statement, scala.runtime.BoxedUnit> f) { throw new RuntimeException(); }
}
