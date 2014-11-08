package org.apache.spark.ui.jobs;
/** Web UI showing progress status of all jobs in the given SparkContext. */
private  class JobProgressTab extends org.apache.spark.ui.SparkUITab {
  public   JobProgressTab (org.apache.spark.ui.SparkUI parent) { throw new RuntimeException(); }
  public  boolean live () { throw new RuntimeException(); }
  public  org.apache.spark.SparkContext sc () { throw new RuntimeException(); }
  public  org.apache.spark.SparkConf conf () { throw new RuntimeException(); }
  public  boolean killEnabled () { throw new RuntimeException(); }
  public  org.apache.spark.ui.jobs.JobProgressListener listener () { throw new RuntimeException(); }
  public  boolean isFairScheduler () { throw new RuntimeException(); }
  public  void handleKillRequest (javax.servlet.http.HttpServletRequest request) { throw new RuntimeException(); }
}
