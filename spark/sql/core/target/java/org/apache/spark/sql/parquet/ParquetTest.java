package org.apache.spark.sql.parquet;
/**
 * A helper trait that provides convenient facilities for Parquet testing.
 * <p>
 * NOTE: Considering classes <code>Tuple1</code> ... <code>Tuple22</code> all extend <code>Product</code>, it would be more
 * convenient to use tuples rather than special case classes when writing test cases/suites.
 * Especially, <code>Tuple1.apply</code> can be used to easily wrap a single type/value.
 */
public  interface ParquetTest {
  public  org.apache.spark.sql.SQLContext sqlContext () ;
  public  org.apache.hadoop.conf.Configuration configuration () ;
  /**
   * Sets all SQL configurations specified in <code>pairs</code>, calls <code>f</code>, and then restore all SQL
   * configurations.
   * <p>
   * @todo Probably this method should be moved to a more general place
   */
  public  void withSQLConf (scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> pairs, scala.Function0<scala.runtime.BoxedUnit> f) ;
  /**
   * Generates a temporary path without creating the actual file/directory, then pass it to <code>f</code>. If
   * a file/directory is created there by <code>f</code>, it will be delete after <code>f</code> returns.
   * <p>
   * @todo Probably this method should be moved to a more general place
   */
  public  void withTempPath (scala.Function1<java.io.File, scala.runtime.BoxedUnit> f) ;
  /**
   * Creates a temporary directory, which is then passed to <code>f</code> and will be deleted after <code>f</code>
   * returns.
   * <p>
   * @todo Probably this method should be moved to a more general place
   */
  public  void withTempDir (scala.Function1<java.io.File, scala.runtime.BoxedUnit> f) ;
  /**
   * Writes <code>data</code> to a Parquet file, which is then passed to <code>f</code> and will be deleted after <code>f</code>
   * returns.
   */
  public <T extends scala.Product> void withParquetFile (scala.collection.Seq<T> data, scala.Function1<java.lang.String, scala.runtime.BoxedUnit> f, scala.reflect.ClassTag<T> evidence$1, scala.reflect.api.TypeTags.TypeTag<T> evidence$2) ;
  /**
   * Writes <code>data</code> to a Parquet file and reads it back as a {@link DataFrame},
   * which is then passed to <code>f</code>. The Parquet file will be deleted after <code>f</code> returns.
   */
  public <T extends scala.Product> void withParquetDataFrame (scala.collection.Seq<T> data, scala.Function1<org.apache.spark.sql.DataFrame, scala.runtime.BoxedUnit> f, scala.reflect.ClassTag<T> evidence$3, scala.reflect.api.TypeTags.TypeTag<T> evidence$4) ;
  /**
   * Drops temporary table <code>tableName</code> after calling <code>f</code>.
   */
  public  void withTempTable (java.lang.String tableName, scala.Function0<scala.runtime.BoxedUnit> f) ;
  /**
   * Writes <code>data</code> to a Parquet file, reads it back as a {@link DataFrame} and registers it as a
   * temporary table named <code>tableName</code>, then call <code>f</code>. The temporary table together with the
   * Parquet file will be dropped/deleted after <code>f</code> returns.
   */
  public <T extends scala.Product> void withParquetTable (scala.collection.Seq<T> data, java.lang.String tableName, scala.Function0<scala.runtime.BoxedUnit> f, scala.reflect.ClassTag<T> evidence$5, scala.reflect.api.TypeTags.TypeTag<T> evidence$6) ;
  public <T extends scala.Product> void makeParquetFile (scala.collection.Seq<T> data, java.io.File path, scala.reflect.ClassTag<T> evidence$7, scala.reflect.api.TypeTags.TypeTag<T> evidence$8) ;
  public <T extends scala.Product> void makeParquetFile (org.apache.spark.sql.DataFrame df, java.io.File path, scala.reflect.ClassTag<T> evidence$9, scala.reflect.api.TypeTags.TypeTag<T> evidence$10) ;
  public  java.io.File makePartitionDir (java.io.File basePath, java.lang.String defaultPartitionName, scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.Object>> partitionCols) ;
}
