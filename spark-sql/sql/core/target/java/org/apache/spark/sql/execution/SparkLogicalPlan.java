package org.apache.spark.sql.execution;
/**
 * :: DeveloperApi ::
 * Allows already planned SparkQueries to be linked into logical query plans.
 * <p>
 * Note that in general it is not valid to use this class to link multiple copies of the same
 * physical operator into the same query plan as this violates the uniqueness of expression ids.
 * Special handling exists for ExistingRdd as these are already leaf operators and thus we can just
 * replace the output attributes with new copies of themselves without breaking any attribute
 * linking.
 */
public  class SparkLogicalPlan extends org.apache.spark.sql.catalyst.plans.logical.LogicalPlan implements org.apache.spark.sql.catalyst.analysis.MultiInstanceRelation, scala.Product, scala.Serializable {
  public  org.apache.spark.sql.execution.SparkPlan alreadyPlanned () { throw new RuntimeException(); }
  // not preceding
  public   SparkLogicalPlan (org.apache.spark.sql.execution.SparkPlan alreadyPlanned, org.apache.spark.sql.SQLContext sqlContext) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  scala.collection.immutable.Nil$ children () { throw new RuntimeException(); }
  public final  org.apache.spark.sql.execution.SparkLogicalPlan newInstance () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LogicalPlan.Statistics statistics () { throw new RuntimeException(); }
}
