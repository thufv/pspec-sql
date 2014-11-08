package org.apache.spark.sql.execution;
private  class KryoResourcePool extends com.twitter.chill.ResourcePool<org.apache.spark.serializer.SerializerInstance> {
  public   KryoResourcePool (int size) { throw new RuntimeException(); }
  public  org.apache.spark.serializer.KryoSerializer ser () { throw new RuntimeException(); }
  public  org.apache.spark.serializer.SerializerInstance newInstance () { throw new RuntimeException(); }
}
