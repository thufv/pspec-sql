package org.apache.spark.sql.sources;
public  interface CreatableRelationProvider {
  /**
   * Creates a relation with the given parameters based on the contents of the given
   * DataFrame. The mode specifies the expected behavior of createRelation when
   * data already exists.
   * Right now, there are three modes, Append, Overwrite, and ErrorIfExists.
   * Append mode means that when saving a DataFrame to a data source, if data already exists,
   * contents of the DataFrame are expected to be appended to existing data.
   * Overwrite mode means that when saving a DataFrame to a data source, if data already exists,
   * existing data is expected to be overwritten by the contents of the DataFrame.
   * ErrorIfExists mode means that when saving a DataFrame to a data source,
   * if data already exists, an exception is expected to be thrown.
   */
  public  org.apache.spark.sql.sources.BaseRelation createRelation (org.apache.spark.sql.SQLContext sqlContext, org.apache.spark.sql.SaveMode mode, scala.collection.immutable.Map<java.lang.String, java.lang.String> parameters, org.apache.spark.sql.DataFrame data) ;
}
