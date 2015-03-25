package org.apache.spark.sql.sources;
/**
 * ::DeveloperApi::
 * A BaseRelation that can produce all of its tuples as an RDD of Row objects.
 */
public  interface TableScan {
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> buildScan () ;
}
