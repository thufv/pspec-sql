package org.apache.spark.network.netty.client;
public  interface BlockClientListener extends java.util.EventListener {
  public abstract  void onFetchSuccess (java.lang.String blockId, org.apache.spark.network.netty.client.ReferenceCountedBuffer data) ;
  public abstract  void onFetchFailure (java.lang.String blockId, java.lang.String errorMsg) ;
}
