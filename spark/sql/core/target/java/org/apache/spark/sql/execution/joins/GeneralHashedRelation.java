package org.apache.spark.sql.execution.joins;
/**
 * A general {@link HashedRelation} backed by a hash map that maps the key into a sequence of values.
 */
public final class GeneralHashedRelation implements org.apache.spark.sql.execution.joins.HashedRelation, scala.Serializable {
  public   GeneralHashedRelation (java.util.HashMap<org.apache.spark.sql.Row, org.apache.spark.util.collection.CompactBuffer<org.apache.spark.sql.Row>> hashTable) { throw new RuntimeException(); }
  public  org.apache.spark.util.collection.CompactBuffer<org.apache.spark.sql.Row> get (org.apache.spark.sql.Row key) { throw new RuntimeException(); }
}
