package org.apache.spark.sql.json;
public  class JSONRelation extends org.apache.spark.sql.sources.BaseRelation implements org.apache.spark.sql.sources.TableScan, org.apache.spark.sql.sources.InsertableRelation, scala.Product, scala.Serializable {
  public  java.lang.String path () { throw new RuntimeException(); }
  public  double samplingRatio () { throw new RuntimeException(); }
  public  scala.Option<org.apache.spark.sql.types.StructType> userSpecifiedSchema () { throw new RuntimeException(); }
  public  org.apache.spark.sql.SQLContext sqlContext () { throw new RuntimeException(); }
  // not preceding
  public   JSONRelation (java.lang.String path, double samplingRatio, scala.Option<org.apache.spark.sql.types.StructType> userSpecifiedSchema, org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  private  org.apache.spark.rdd.RDD<java.lang.String> baseRDD () { throw new RuntimeException(); }
  public  org.apache.spark.sql.types.StructType schema () { throw new RuntimeException(); }
  public  org.apache.spark.rdd.RDD<org.apache.spark.sql.Row> buildScan () { throw new RuntimeException(); }
  public  void insert (org.apache.spark.sql.DataFrame data, boolean overwrite) { throw new RuntimeException(); }
  public  int hashCode () { throw new RuntimeException(); }
  public  boolean equals (Object other) { throw new RuntimeException(); }
}
