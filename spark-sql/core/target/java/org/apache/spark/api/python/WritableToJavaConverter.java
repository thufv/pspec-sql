package org.apache.spark.api.python;
/**
 * A converter that handles conversion of common {@link org.apache.hadoop.io.Writable} objects.
 * Other objects are passed through without conversion.
 */
private  class WritableToJavaConverter implements org.apache.spark.api.python.Converter<java.lang.Object, java.lang.Object> {
  public   WritableToJavaConverter (org.apache.spark.broadcast.Broadcast<org.apache.spark.SerializableWritable<org.apache.hadoop.conf.Configuration>> conf, int batchSize) { throw new RuntimeException(); }
  /**
   * Converts a {@link org.apache.hadoop.io.Writable} to the underlying primitive, String or
   * object representation
   */
  private  Object convertWritable (org.apache.hadoop.io.Writable writable) { throw new RuntimeException(); }
  public  Object convert (Object obj) { throw new RuntimeException(); }
}
