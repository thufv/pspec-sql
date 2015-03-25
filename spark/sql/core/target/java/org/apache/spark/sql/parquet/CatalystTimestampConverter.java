package org.apache.spark.sql.parquet;
// no position
public  class CatalystTimestampConverter {
  static public  java.lang.ThreadLocal<java.util.Calendar> parquetTsCalendar () { throw new RuntimeException(); }
  static public  java.util.Calendar getCalendar () { throw new RuntimeException(); }
  static public  long NANOS_PER_SECOND () { throw new RuntimeException(); }
  static public  long SECONDS_PER_MINUTE () { throw new RuntimeException(); }
  static public  long MINUTES_PER_HOUR () { throw new RuntimeException(); }
  static public  long NANOS_PER_MILLI () { throw new RuntimeException(); }
  static public  java.sql.Timestamp convertToTimestamp (parquet.io.api.Binary value) { throw new RuntimeException(); }
  static public  parquet.io.api.Binary convertFromTimestamp (java.sql.Timestamp ts) { throw new RuntimeException(); }
}
