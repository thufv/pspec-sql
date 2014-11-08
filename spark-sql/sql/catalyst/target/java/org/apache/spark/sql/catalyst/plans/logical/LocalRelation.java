package org.apache.spark.sql.catalyst.plans.logical;
public  class LocalRelation extends org.apache.spark.sql.catalyst.plans.logical.LeafNode implements org.apache.spark.sql.catalyst.analysis.MultiInstanceRelation, scala.Product, scala.Serializable {
  static public  org.apache.spark.sql.catalyst.plans.logical.LocalRelation apply (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Product> data () { throw new RuntimeException(); }
  // not preceding
  public   LocalRelation (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> output, scala.collection.Seq<scala.Product> data) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.plans.logical.LocalRelation loadData (scala.collection.Seq<scala.Product> newData) { throw new RuntimeException(); }
  /**
   * Returns an identical copy of this relation with new exprIds for all attributes.  Different
   * attributes are required when a relation is going to be included multiple times in the same
   * query.
   */
  public final  org.apache.spark.sql.catalyst.plans.logical.LocalRelation newInstance () { throw new RuntimeException(); }
  protected  scala.collection.Iterator<scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute>> stringArgs () { throw new RuntimeException(); }
}
