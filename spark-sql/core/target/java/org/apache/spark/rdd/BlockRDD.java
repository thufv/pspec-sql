package org.apache.spark.rdd;
private  class BlockRDD<T extends java.lang.Object> extends org.apache.spark.rdd.RDD<T> {
  public  org.apache.spark.storage.BlockId[] blockIds () { throw new RuntimeException(); }
  // not preceding
  public   BlockRDD (org.apache.spark.SparkContext sc, org.apache.spark.storage.BlockId[] blockIds, scala.reflect.ClassTag<T> evidence$1) { throw new RuntimeException(); }
  public  scala.collection.immutable.Map<org.apache.spark.storage.BlockId, scala.collection.Seq<java.lang.String>> locations_ () { throw new RuntimeException(); }
  private  boolean _isValid () { throw new RuntimeException(); }
  public  org.apache.spark.Partition[] getPartitions () { throw new RuntimeException(); }
  public  scala.collection.Iterator<T> compute (org.apache.spark.Partition split, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  scala.collection.Seq<java.lang.String> getPreferredLocations (org.apache.spark.Partition split) { throw new RuntimeException(); }
  /**
   * Remove the data blocks that this BlockRDD is made from. NOTE: This is an
   * irreversible operation, as the data in the blocks cannot be recovered back
   * once removed. Use it with caution.
   */
  private  void removeBlocks () { throw new RuntimeException(); }
  /**
   * Whether this BlockRDD is actually usable. This will be false if the data blocks have been
   * removed using <code>this.removeBlocks</code>.
   */
  private  boolean isValid () { throw new RuntimeException(); }
  /** Check if this BlockRDD is valid. If not valid, exception is thrown. */
  private  void assertValid () { throw new RuntimeException(); }
}
