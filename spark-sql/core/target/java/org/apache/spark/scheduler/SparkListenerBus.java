package org.apache.spark.scheduler;
/**
 * A SparkListenerEvent bus that relays events to its listeners
 */
private abstract interface SparkListenerBus extends org.apache.spark.Logging {
  protected  scala.collection.mutable.ArrayBuffer<org.apache.spark.scheduler.SparkListener> sparkListeners () ;
  public  void addListener (org.apache.spark.scheduler.SparkListener listener) ;
  /**
   * Post an event to all attached listeners.
   * This does nothing if the event is SparkListenerShutdown.
   */
  public  void postToAll (org.apache.spark.scheduler.SparkListenerEvent event) ;
  /**
   * Apply the given function to all attached listeners, catching and logging any exception.
   */
  private  void foreachListener (scala.Function1<org.apache.spark.scheduler.SparkListener, scala.runtime.BoxedUnit> f) ;
}
