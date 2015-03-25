package org.apache.spark.sql.execution.joins;
/**
 * A specialized {@link HashedRelation} that maps key into a single value. This implementation
 * assumes the key is unique.
 */
public final class UniqueKeyHashedRelation implements org.apache.spark.sql.execution.joins.HashedRelation, scala.Serializable {
  public   UniqueKeyHashedRelation (java.util.HashMap<org.apache.spark.sql.Row, org.apache.spark.sql.Row> hashTable) { throw new RuntimeException(); }
  public  org.apache.spark.util.collection.CompactBuffer<org.apache.spark.sql.Row> get (org.apache.spark.sql.Row key) { throw new RuntimeException(); }
  public  org.apache.spark.sql.Row getValue (org.apache.spark.sql.Row key) { throw new RuntimeException(); }
}
