package org.apache.spark.ui.jobs;
/** Render an HTML row that represents a stage */
private  class FailedStageTable extends org.apache.spark.ui.jobs.StageTableBase {
  public   FailedStageTable (scala.collection.Seq<org.apache.spark.scheduler.StageInfo> stages, org.apache.spark.ui.jobs.JobProgressTab parent, boolean killEnabled) { throw new RuntimeException(); }
  protected  scala.collection.Seq<scala.xml.Node> columns () { throw new RuntimeException(); }
  protected  scala.collection.Seq<scala.xml.Node> stageRow (org.apache.spark.scheduler.StageInfo s) { throw new RuntimeException(); }
}
