package org.apache.spark.scheduler;
/**
 * Asynchronously passes SparkListenerEvents to registered SparkListeners.
 * <p>
 * Until start() is called, all posted events are only buffered. Only after this listener bus
 * has started will events be actually propagated to all attached listeners. This listener bus
 * is stopped when it receives a SparkListenerShutdown event, which is posted using stop().
 */
private  class LiveListenerBus implements org.apache.spark.scheduler.SparkListenerBus, org.apache.spark.Logging {
  public   LiveListenerBus () { throw new RuntimeException(); }
  private  int EVENT_QUEUE_CAPACITY () { throw new RuntimeException(); }
  private  java.util.concurrent.LinkedBlockingQueue<org.apache.spark.scheduler.SparkListenerEvent> eventQueue () { throw new RuntimeException(); }
  private  boolean queueFullErrorMessageLogged () { throw new RuntimeException(); }
  private  boolean started () { throw new RuntimeException(); }
  private  java.util.concurrent.Semaphore eventLock () { throw new RuntimeException(); }
  private  java.lang.Thread listenerThread () { throw new RuntimeException(); }
  /**
   * Start sending events to attached listeners.
   * <p>
   * This first sends out all buffered events posted before this listener bus has started, then
   * listens for any additional events asynchronously while the listener bus is still running.
   * This should only be called once.
   */
  public  void start () { throw new RuntimeException(); }
  public  void post (org.apache.spark.scheduler.SparkListenerEvent event) { throw new RuntimeException(); }
  /**
   * For testing only. Wait until there are no more events in the queue, or until the specified
   * time has elapsed. Return true if the queue has emptied and false is the specified time
   * elapsed before the queue emptied.
   */
  public  boolean waitUntilEmpty (int timeoutMillis) { throw new RuntimeException(); }
  /**
   * For testing only. Return whether the listener daemon thread is still alive.
   */
  public  boolean listenerThreadIsAlive () { throw new RuntimeException(); }
  /**
   * Return whether the event queue is empty.
   * <p>
   * The use of synchronized here guarantees that all events that once belonged to this queue
   * have already been processed by all attached listeners, if this returns true.
   */
  public  boolean queueIsEmpty () { throw new RuntimeException(); }
  /**
   * Log an error message to indicate that the event queue is full. Do this only once.
   */
  private  void logQueueFullErrorMessage () { throw new RuntimeException(); }
  public  void stop () { throw new RuntimeException(); }
}
