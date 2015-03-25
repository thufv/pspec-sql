package org.apache.spark.sql.json;
public  class DefaultSource implements org.apache.spark.sql.sources.RelationProvider, org.apache.spark.sql.sources.SchemaRelationProvider, org.apache.spark.sql.sources.CreatableRelationProvider {
  public   DefaultSource () { throw new RuntimeException(); }
  private  java.lang.String checkPath (scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters) { throw new RuntimeException(); }
  /** Returns a new base relation with the parameters. */
  public  org.apache.spark.sql.sources.BaseRelation createRelation (org.apache.spark.sql.SQLContext sqlContext, scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters) { throw new RuntimeException(); }
  /** Returns a new base relation with the given schema and parameters. */
  public  org.apache.spark.sql.sources.BaseRelation createRelation (org.apache.spark.sql.SQLContext sqlContext, scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters, org.apache.spark.sql.types.StructType schema) { throw new RuntimeException(); }
  public  org.apache.spark.sql.sources.BaseRelation createRelation (org.apache.spark.sql.SQLContext sqlContext, org.apache.spark.sql.SaveMode mode, scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters, org.apache.spark.sql.DataFrame data) { throw new RuntimeException(); }
}
