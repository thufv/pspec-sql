package org.apache.spark.sql.catalyst.plans.physical;
// no position
/**
 * Represents a distribution that only has a single partition and all tuples of the dataset
 * are co-located.
 */
public  class AllTuples implements org.apache.spark.sql.catalyst.plans.physical.Distribution, scala.Product, scala.Serializable {
}
