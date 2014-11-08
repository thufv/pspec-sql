package org.apache.spark.sql.columnar.compression;
private  interface CompressionScheme {
  static public  scala.collection.Seq<org.apache.spark.sql.columnar.compression.CompressionScheme> all () { throw new RuntimeException(); }
  static private  scala.collection.immutable.Map<java.lang.Object, org.apache.spark.sql.columnar.compression.CompressionScheme> typeIdToScheme () { throw new RuntimeException(); }
  static public  org.apache.spark.sql.columnar.compression.CompressionScheme apply (int typeId) { throw new RuntimeException(); }
  static public  int columnHeaderSize (java.nio.ByteBuffer columnBuffer) { throw new RuntimeException(); }
  public abstract  int typeId () ;
  public abstract  boolean supports (org.apache.spark.sql.columnar.ColumnType<?, ?> columnType) ;
  public abstract <T extends org.apache.spark.sql.catalyst.types.NativeType> org.apache.spark.sql.columnar.compression.Encoder<T> encoder () ;
  public abstract <T extends org.apache.spark.sql.catalyst.types.NativeType> org.apache.spark.sql.columnar.compression.Decoder<T> decoder (java.nio.ByteBuffer buffer, org.apache.spark.sql.columnar.NativeColumnType<T> columnType) ;
}
