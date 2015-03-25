package org.apache.spark.sql.sources;
/**
 * ::DeveloperApi::
 * Represents a collection of tuples with a known schema. Classes that extend BaseRelation must
 * be able to produce the schema of their data in the form of a {@link StructType}. Concrete
 * implementation should inherit from one of the descendant <code>Scan</code> classes, which define various
 * abstract methods for execution.
 * <p>
 * BaseRelations must also define a equality function that only returns true when the two
 * instances will return the same data. This equality function is used when determining when
 * it is safe to substitute cached results for a given relation.
 */
public abstract class BaseRelation {
  public   BaseRelation () { throw new RuntimeException(); }
  public abstract  org.apache.spark.sql.SQLContext sqlContext () ;
  public abstract  org.apache.spark.sql.types.StructType schema () ;
  /**
   * Returns an estimated size of this relation in bytes. This information is used by the planner
   * to decided when it is safe to broadcast a relation and can be overridden by sources that
   * know the size ahead of time. By default, the system will assume that tables are too
   * large to broadcast. This method will be called multiple times during query planning
   * and thus should not perform expensive operations for each invocation.
   * <p>
   * Note that it is always better to overestimate size than underestimate, because underestimation
   * could lead to execution plans that are suboptimal (i.e. broadcasting a very large table).
   */
  public  long sizeInBytes () { throw new RuntimeException(); }
}
