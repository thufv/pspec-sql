package org.apache.spark.sql.sources;
// no position
public  class ResolvedDataSource$ implements scala.Serializable {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ResolvedDataSource$ MODULE$ = null;
  public   ResolvedDataSource$ () { throw new RuntimeException(); }
  private  scala.collection.immutable.Map<java.lang.String, java.lang.Class<? extends org.apache.spark.sql.sources.RelationProvider>> builtinSources () { throw new RuntimeException(); }
  /** Given a provider name, look up the data source class definition. */
  public  java.lang.Class<?> lookupDataSource (java.lang.String provider) { throw new RuntimeException(); }
  /** Create a {@link ResolvedDataSource} for reading data in. */
  public  org.apache.spark.sql.sources.ResolvedDataSource apply (org.apache.spark.sql.SQLContext sqlContext, scala.Option<org.apache.spark.sql.types.StructType> userSpecifiedSchema, java.lang.String provider, scala.collection.immutable.Map<java.lang.String, java.lang.String> options) { throw new RuntimeException(); }
  /** Create a {@link ResolvedDataSource} for saving the content of the given {@link DataFrame}. */
  public  org.apache.spark.sql.sources.ResolvedDataSource apply (org.apache.spark.sql.SQLContext sqlContext, java.lang.String provider, org.apache.spark.sql.SaveMode mode, scala.collection.immutable.Map<java.lang.String, java.lang.String> options, org.apache.spark.sql.DataFrame data) { throw new RuntimeException(); }
}
