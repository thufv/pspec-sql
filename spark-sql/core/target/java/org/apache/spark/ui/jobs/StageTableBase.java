package org.apache.spark.ui.jobs;
/** Page showing list of all ongoing and recently finished stages */
private  class StageTableBase {
  public   StageTableBase (scala.collection.Seq<org.apache.spark.scheduler.StageInfo> stages, org.apache.spark.ui.jobs.JobProgressTab parent, boolean killEnabled) { throw new RuntimeException(); }
  private  org.apache.spark.ui.jobs.JobProgressListener listener () { throw new RuntimeException(); }
  protected  boolean isFairScheduler () { throw new RuntimeException(); }
  protected  scala.collection.Seq<scala.xml.Node> columns () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.xml.Node> toNodeSeq () { throw new RuntimeException(); }
  /** Special table that merges two header cells. */
  protected <T extends java.lang.Object> scala.collection.Seq<scala.xml.Node> stageTable (scala.Function1<T, scala.collection.Seq<scala.xml.Node>> makeRow, scala.collection.Seq<T> rows) { throw new RuntimeException(); }
  private  scala.collection.Seq<scala.xml.Node> makeProgressBar (int started, int completed, int failed, int total) { throw new RuntimeException(); }
  private  scala.collection.Seq<scala.xml.Node> makeDescription (org.apache.spark.scheduler.StageInfo s) { throw new RuntimeException(); }
  protected  scala.collection.Seq<scala.xml.Node> stageRow (org.apache.spark.scheduler.StageInfo s) { throw new RuntimeException(); }
  /** Render an HTML row that represents a stage */
  private  scala.collection.Seq<scala.xml.Node> renderStageRow (org.apache.spark.scheduler.StageInfo s) { throw new RuntimeException(); }
}
