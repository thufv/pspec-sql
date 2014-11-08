package org.apache.spark.rdd;
/**
 * An RDD that executes an SQL query on a JDBC connection and reads results.
 * For usage example, see test case JdbcRDDSuite.
 * <p>
 * @param getConnection a function that returns an open Connection.
 *   The RDD takes care of closing the connection.
 * @param sql the text of the query.
 *   The query must contain two ? placeholders for parameters used to partition the results.
 *   E.g. "select title, author from books where ? <= id and id <= ?"
 * @param lowerBound the minimum value of the first placeholder
 * @param upperBound the maximum value of the second placeholder
 *   The lower and upper bounds are inclusive.
 * @param numPartitions the number of partitions.
 *   Given a lowerBound of 1, an upperBound of 20, and a numPartitions of 2,
 *   the query would be executed twice, once with (1, 10) and once with (11, 20)
 * @param mapRow a function from a ResultSet to a single row of the desired result type(s).
 *   This should only call getInt, getString, etc; the RDD takes care of calling next.
 *   The default maps a ResultSet to an array of Object.
 */
public  class JdbcRDD<T extends java.lang.Object> extends org.apache.spark.rdd.RDD<T> implements org.apache.spark.Logging {
  static public  java.lang.Object[] resultSetToObjectArray (java.sql.ResultSet rs) { throw new RuntimeException(); }
  // not preceding
  // TypeTree().setOriginal(TypeBoundsTree(TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Nothing)), TypeTree().setOriginal(Select(Select(Ident(_root_), scala), scala.Any))))
  public   JdbcRDD (org.apache.spark.SparkContext sc, scala.Function0<java.sql.Connection> getConnection, java.lang.String sql, long lowerBound, long upperBound, int numPartitions, scala.Function1<java.sql.ResultSet, T> mapRow, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  org.apache.spark.util.NextIterator<T> compute (org.apache.spark.Partition thePart, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
}
