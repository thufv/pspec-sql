package org.apache.spark.sql.parquet;
/**
 * A <code>parquet.io.api.PrimitiveConverter</code> that converts Parquet types to Catalyst types.
 * <p>
 * @param parent The parent group converter.
 * @param fieldIndex The index inside the record.
 */
public  class CatalystPrimitiveConverter extends parquet.io.api.PrimitiveConverter {
  public   CatalystPrimitiveConverter (org.apache.spark.sql.parquet.CatalystConverter parent, int fieldIndex) { throw new RuntimeException(); }
  public  void addBinary (parquet.io.api.Binary value) { throw new RuntimeException(); }
  public  void addBoolean (boolean value) { throw new RuntimeException(); }
  public  void addDouble (double value) { throw new RuntimeException(); }
  public  void addFloat (float value) { throw new RuntimeException(); }
  public  void addInt (int value) { throw new RuntimeException(); }
  public  void addLong (long value) { throw new RuntimeException(); }
}
