package org.apache.spark.ui.jobs;
/** Page showing statistics and task list for a given stage */
private  class StagePage extends org.apache.spark.ui.WebUIPage {
  public   StagePage (org.apache.spark.ui.jobs.JobProgressTab parent) { throw new RuntimeException(); }
  private  org.apache.spark.ui.jobs.JobProgressListener listener () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.xml.Node> render (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.xml.Node> taskRow (boolean hasInput, boolean hasShuffleRead, boolean hasShuffleWrite, boolean hasBytesSpilled, org.apache.spark.ui.jobs.UIData.TaskUIData taskData) { throw new RuntimeException(); }
}
