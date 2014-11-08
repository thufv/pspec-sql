package org.apache.spark.scheduler;
/**
 * A simple listener for application events.
 * <p>
 * This listener expects to hear events from a single application only. If events
 * from multiple applications are seen, the behavior is unspecified.
 */
private  class ApplicationEventListener implements org.apache.spark.scheduler.SparkListener {
  public   ApplicationEventListener () { throw new RuntimeException(); }
  public  java.lang.String appName () { throw new RuntimeException(); }
  public  java.lang.String sparkUser () { throw new RuntimeException(); }
  public  long startTime () { throw new RuntimeException(); }
  public  long endTime () { throw new RuntimeException(); }
  public  java.lang.String viewAcls () { throw new RuntimeException(); }
  public  java.lang.String adminAcls () { throw new RuntimeException(); }
  public  boolean applicationStarted () { throw new RuntimeException(); }
  public  boolean applicationCompleted () { throw new RuntimeException(); }
  public  long applicationDuration () { throw new RuntimeException(); }
  public  void onApplicationStart (org.apache.spark.scheduler.SparkListenerApplicationStart applicationStart) { throw new RuntimeException(); }
  public  void onApplicationEnd (org.apache.spark.scheduler.SparkListenerApplicationEnd applicationEnd) { throw new RuntimeException(); }
  public  void onEnvironmentUpdate (org.apache.spark.scheduler.SparkListenerEnvironmentUpdate environmentUpdate) { throw new RuntimeException(); }
}
