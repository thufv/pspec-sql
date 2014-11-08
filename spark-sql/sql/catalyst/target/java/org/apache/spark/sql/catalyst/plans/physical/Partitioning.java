package org.apache.spark.sql.catalyst.plans.physical;
public  interface Partitioning {
  /** Returns the number of partitions that the data is split across */
  public abstract  int numPartitions () ;
  /**
   * Returns true iff the guarantees made by this {@link Partitioning} are sufficient
   * to satisfy the partitioning scheme mandated by the <code>required</code> {@link Distribution},
   * i.e. the current dataset does not need to be re-partitioned for the <code>required</code>
   * Distribution (it is possible that tuples within a partition need to be reorganized).
   */
  public abstract  boolean satisfies (org.apache.spark.sql.catalyst.plans.physical.Distribution required) ;
  /**
   * Returns true iff all distribution guarantees made by this partitioning can also be made
   * for the <code>other</code> specified partitioning.
   * For example, two {@link HashPartitioning HashPartitioning}s are
   * only compatible if the <code>numPartitions</code> of them is the same.
   */
  public abstract  boolean compatibleWith (org.apache.spark.sql.catalyst.plans.physical.Partitioning other) ;
}
