package org.apache.spark.sql.hive.execution;
public  class PairSerDe extends org.apache.hadoop.hive.serde2.AbstractSerDe {
  public   PairSerDe () { throw new RuntimeException(); }
  public  void initialize (org.apache.hadoop.conf.Configuration p1, java.util.Properties p2) { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector getObjectInspector () { throw new RuntimeException(); }
  public  java.lang.Class<? extends org.apache.hadoop.io.Writable> getSerializedClass () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.serde2.SerDeStats getSerDeStats () { throw new RuntimeException(); }
  public  org.apache.hadoop.io.Writable serialize (Object p1, org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector p2) { throw new RuntimeException(); }
  public  java.lang.Object deserialize (org.apache.hadoop.io.Writable value) { throw new RuntimeException(); }
}
