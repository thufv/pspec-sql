package org.apache.spark.scheduler;
// no position
private  class StageInfo$ {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final StageInfo$ MODULE$ = null;
  public   StageInfo$ () { throw new RuntimeException(); }
  /**
   * Construct a StageInfo from a Stage.
   * <p>
   * Each Stage is associated with one or many RDDs, with the boundary of a Stage marked by
   * shuffle dependencies. Therefore, all ancestor RDDs related to this Stage's RDD through a
   * sequence of narrow dependencies should also be associated with this Stage.
   */
  public  org.apache.spark.scheduler.StageInfo fromStage (org.apache.spark.scheduler.Stage stage, scala.Option<java.lang.Object> numTasks) { throw new RuntimeException(); }
}
