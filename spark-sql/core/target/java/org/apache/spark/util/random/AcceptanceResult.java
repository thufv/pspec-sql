package org.apache.spark.util.random;
/**
 * Object used by seqOp to keep track of the number of items accepted and items waitlisted per
 * stratum, as well as the bounds for accepting and waitlisting items.
 * <p>
 * <code>[random]</code> here is necessary since it's in the return type signature of seqOp defined above
 */
private  class AcceptanceResult implements scala.Serializable {
  public  long numItems () { throw new RuntimeException(); }
  public  long numAccepted () { throw new RuntimeException(); }
  // not preceding
  public   AcceptanceResult (long numItems, long numAccepted) { throw new RuntimeException(); }
  public  scala.collection.mutable.ArrayBuffer<java.lang.Object> waitList () { throw new RuntimeException(); }
  public  double acceptBound () { throw new RuntimeException(); }
  public  double waitListBound () { throw new RuntimeException(); }
  public  boolean areBoundsEmpty () { throw new RuntimeException(); }
  public  void merge (scala.Option<org.apache.spark.util.random.AcceptanceResult> other) { throw new RuntimeException(); }
}
