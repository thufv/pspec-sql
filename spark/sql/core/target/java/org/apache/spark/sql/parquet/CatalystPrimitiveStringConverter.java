package org.apache.spark.sql.parquet;
/**
 * A <code>parquet.io.api.PrimitiveConverter</code> that converts Parquet Binary to Catalyst String.
 * Supports dictionaries to reduce Binary to String conversion overhead.
 * <p>
 * Follows pattern in Parquet of using dictionaries, where supported, for String conversion.
 * <p>
 * @param parent The parent group converter.
 * @param fieldIndex The index inside the record.
 */
public  class CatalystPrimitiveStringConverter extends org.apache.spark.sql.parquet.CatalystPrimitiveConverter {
  public   CatalystPrimitiveStringConverter (org.apache.spark.sql.parquet.CatalystConverter parent, int fieldIndex) { throw new RuntimeException(); }
  public  boolean hasDictionarySupport () { throw new RuntimeException(); }
  public  void setDictionary (parquet.column.Dictionary dictionary) { throw new RuntimeException(); }
  public  void addValueFromDictionary (int dictionaryId) { throw new RuntimeException(); }
  public  void addBinary (parquet.io.api.Binary value) { throw new RuntimeException(); }
}
