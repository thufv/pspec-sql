package org.apache.spark.sql.jdbc;
/**
 * Given a partitioning schematic (a column of integral type, a number of
 * partitions, and upper and lower bounds on the column's value), generate
 * WHERE clauses for each partition so that each row in the table appears
 * exactly once.  The parameters minValue and maxValue are advisory in that
 * incorrect values may cause the partitioning to be poor, but no data
 * will fail to be represented.
 */
public  class DefaultSource implements org.apache.spark.sql.sources.RelationProvider {
  public   DefaultSource () { throw new RuntimeException(); }
  /** Returns a new base relation with the given parameters. */
  public  org.apache.spark.sql.sources.BaseRelation createRelation (org.apache.spark.sql.SQLContext sqlContext, scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters) { throw new RuntimeException(); }
}
