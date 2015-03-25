package org.apache.spark.sql.hive;
public  class ShimFileSinkDesc implements scala.Serializable, org.apache.spark.Logging {
  public  java.lang.String dir () { throw new RuntimeException(); }
  public  org.apache.hadoop.hive.ql.plan.TableDesc tableInfo () { throw new RuntimeException(); }
  public  boolean compressed () { throw new RuntimeException(); }
  // not preceding
  public   ShimFileSinkDesc (java.lang.String dir, org.apache.hadoop.hive.ql.plan.TableDesc tableInfo, boolean compressed) { throw new RuntimeException(); }
  public  java.lang.String compressCodec () { throw new RuntimeException(); }
  public  java.lang.String compressType () { throw new RuntimeException(); }
  public  int destTableId () { throw new RuntimeException(); }
  public  void setCompressed (boolean compressed) { throw new RuntimeException(); }
  public  java.lang.String getDirName () { throw new RuntimeException(); }
  public  void setDestTableId (int destTableId) { throw new RuntimeException(); }
  public  void setTableInfo (org.apache.hadoop.hive.ql.plan.TableDesc tableInfo) { throw new RuntimeException(); }
  public  void setCompressCodec (java.lang.String intermediateCompressorCodec) { throw new RuntimeException(); }
  public  void setCompressType (java.lang.String intermediateCompressType) { throw new RuntimeException(); }
}
