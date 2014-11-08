package org.apache.spark.ui.jobs;
/** Table showing list of pools */
private  class PoolTable {
  public   PoolTable (scala.collection.Seq<org.apache.spark.scheduler.Schedulable> pools, org.apache.spark.ui.jobs.JobProgressTab parent) { throw new RuntimeException(); }
  private  org.apache.spark.ui.jobs.JobProgressListener listener () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.xml.Node> toNodeSeq () { throw new RuntimeException(); }
  private  scala.collection.Seq<scala.xml.Node> poolTable (scala.Function2<org.apache.spark.scheduler.Schedulable, scala.collection.mutable.HashMap<java.lang.String, scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.scheduler.StageInfo>>, scala.collection.Seq<scala.xml.Node>> makeRow, scala.collection.Seq<org.apache.spark.scheduler.Schedulable> rows) { throw new RuntimeException(); }
  private  scala.collection.Seq<scala.xml.Node> poolRow (org.apache.spark.scheduler.Schedulable p, scala.collection.mutable.HashMap<java.lang.String, scala.collection.mutable.HashMap<java.lang.Object, org.apache.spark.scheduler.StageInfo>> poolToActiveStages) { throw new RuntimeException(); }
}
