package org.apache.spark.sql.execution;
/**
 * This is an example TGF that uses UnresolvedAttributes 'name and 'age to access specific columns
 * from the input data.  These will be replaced during analysis with specific AttributeReferences
 * and then bound to specific ordinals during query planning. While TGFs could also access specific
 * columns using hand-coded ordinals, doing so violates data independence.
 * <p>
 * Note: this is only a rough example of how TGFs can be expressed, the final version will likely
 * involve a lot more sugar for cleaner use in Scala/Java/etc.
 */
public  class ExampleTGF extends org.apache.spark.sql.catalyst.expressions.Generator implements scala.Product, scala.Serializable {
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> input () { throw new RuntimeException(); }
  // not preceding
  public   ExampleTGF (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> input) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Expression> children () { throw new RuntimeException(); }
  protected  scala.collection.immutable.List<org.apache.spark.sql.catalyst.expressions.AttributeReference> makeOutput () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression nameAttr () { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.expressions.Expression ageAttr () { throw new RuntimeException(); }
  public  scala.collection.TraversableOnce<org.apache.spark.sql.catalyst.expressions.Row> eval (org.apache.spark.sql.catalyst.expressions.Row input) { throw new RuntimeException(); }
}
