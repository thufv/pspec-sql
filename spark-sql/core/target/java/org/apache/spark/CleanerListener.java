package org.apache.spark;
/**
 * Listener class used for testing when any item has been cleaned by the Cleaner class.
 */
private  interface CleanerListener {
  public abstract  void rddCleaned (int rddId) ;
  public abstract  void shuffleCleaned (int shuffleId) ;
  public abstract  void broadcastCleaned (long broadcastId) ;
}
