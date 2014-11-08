package org.apache.spark.shuffle;
/**
 * Failed to get shuffle metadata from {@link org.apache.spark.MapOutputTracker}.
 */
private  class MetadataFetchFailedException extends org.apache.spark.shuffle.FetchFailedException {
  public   MetadataFetchFailedException (int shuffleId, int reduceId, java.lang.String message) { throw new RuntimeException(); }
  public  java.lang.String getMessage () { throw new RuntimeException(); }
}
