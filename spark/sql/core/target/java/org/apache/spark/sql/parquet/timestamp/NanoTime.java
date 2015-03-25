package org.apache.spark.sql.parquet.timestamp;
public  class NanoTime implements scala.Serializable {
  static public  org.apache.spark.sql.parquet.timestamp.NanoTime fromBinary (parquet.io.api.Binary bytes) { throw new RuntimeException(); }
  static public  org.apache.spark.sql.parquet.timestamp.NanoTime apply (int julianDay, long timeOfDayNanos) { throw new RuntimeException(); }
  public   NanoTime () { throw new RuntimeException(); }
  private  int julianDay () { throw new RuntimeException(); }
  private  long timeOfDayNanos () { throw new RuntimeException(); }
  public  org.apache.spark.sql.parquet.timestamp.NanoTime set (int julianDay, long timeOfDayNanos) { throw new RuntimeException(); }
  public  int getJulianDay () { throw new RuntimeException(); }
  public  long getTimeOfDayNanos () { throw new RuntimeException(); }
  public  parquet.io.api.Binary toBinary () { throw new RuntimeException(); }
  public  void writeValue (parquet.io.api.RecordConsumer recordConsumer) { throw new RuntimeException(); }
  public  java.lang.String toString () { throw new RuntimeException(); }
}
