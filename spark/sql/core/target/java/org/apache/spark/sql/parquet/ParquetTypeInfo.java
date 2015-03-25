package org.apache.spark.sql.parquet;
/** A class representing Parquet info fields we care about, for passing back to Parquet */
public  class ParquetTypeInfo implements scala.Product, scala.Serializable {
  public  parquet.schema.PrimitiveType.PrimitiveTypeName primitiveType () { throw new RuntimeException(); }
  public  scala.Option<parquet.schema.OriginalType> originalType () { throw new RuntimeException(); }
  public  scala.Option<parquet.schema.DecimalMetadata> decimalMetadata () { throw new RuntimeException(); }
  public  scala.Option<java.lang.Object> length () { throw new RuntimeException(); }
  // not preceding
  public   ParquetTypeInfo (parquet.schema.PrimitiveType.PrimitiveTypeName primitiveType, scala.Option<parquet.schema.OriginalType> originalType, scala.Option<parquet.schema.DecimalMetadata> decimalMetadata, scala.Option<java.lang.Object> length) { throw new RuntimeException(); }
}
