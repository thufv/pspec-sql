package org.apache.spark;
/**
 * :: DeveloperApi ::
 * A set of functions used to aggregate data.
 * <p>
 * @param createCombiner function to create the initial value of the aggregation.
 * @param mergeValue function to merge a new value into the aggregation result.
 * @param mergeCombiners function to merge outputs from multiple mergeValue function.
 */
public  class Aggregator<K extends java.lang.Object, V extends java.lang.Object, C extends java.lang.Object> implements scala.Product, scala.Serializable {
  public  scala.Function1<V, C> createCombiner () { throw new RuntimeException(); }
  public  scala.Function2<C, V, C> mergeValue () { throw new RuntimeException(); }
  public  scala.Function2<C, C, C> mergeCombiners () { throw new RuntimeException(); }
  // not preceding
  public   Aggregator (scala.Function1<V, C> createCombiner, scala.Function2<C, V, C> mergeValue, scala.Function2<C, C, C> mergeCombiners) { throw new RuntimeException(); }
  private  boolean externalSorting () { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<K, C>> combineValuesByKey (scala.collection.Iterator<scala.Product2<K, V>> iter) { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<K, C>> combineValuesByKey (scala.collection.Iterator<scala.Product2<K, V>> iter, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<K, C>> combineCombinersByKey (scala.collection.Iterator<scala.Product2<K, C>> iter) { throw new RuntimeException(); }
  public  scala.collection.Iterator<scala.Tuple2<K, C>> combineCombinersByKey (scala.collection.Iterator<scala.Product2<K, C>> iter, org.apache.spark.TaskContext context) { throw new RuntimeException(); }
}
