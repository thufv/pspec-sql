package org.apache.spark.sql.parquet;
// no position
public  class CatalystTimestampConverter$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final CatalystTimestampConverter$ MODULE$ = null;
  public   CatalystTimestampConverter$ () { throw new RuntimeException(); }
  public  java.lang.ThreadLocal<java.util.Calendar> parquetTsCalendar () { throw new RuntimeException(); }
  public  java.util.Calendar getCalendar () { throw new RuntimeException(); }
  public  long NANOS_PER_SECOND () { throw new RuntimeException(); }
  public  long SECONDS_PER_MINUTE () { throw new RuntimeException(); }
  public  long MINUTES_PER_HOUR () { throw new RuntimeException(); }
  public  long NANOS_PER_MILLI () { throw new RuntimeException(); }
  public  java.sql.Timestamp convertToTimestamp (parquet.io.api.Binary value) { throw new RuntimeException(); }
  public  parquet.io.api.Binary convertFromTimestamp (java.sql.Timestamp ts) { throw new RuntimeException(); }
}
