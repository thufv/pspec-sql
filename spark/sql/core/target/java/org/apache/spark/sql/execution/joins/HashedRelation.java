package org.apache.spark.sql.execution.joins;
/**
 * Interface for a hashed relation by some key. Use {@link HashedRelation.apply} to create a concrete
 * object.
 */
public  interface HashedRelation {
  public  org.apache.spark.util.collection.CompactBuffer<org.apache.spark.sql.Row> get (org.apache.spark.sql.Row key) ;
}
