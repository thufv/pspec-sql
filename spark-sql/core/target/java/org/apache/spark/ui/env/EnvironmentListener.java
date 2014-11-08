package org.apache.spark.ui.env;
/**
 * :: DeveloperApi ::
 * A SparkListener that prepares information to be displayed on the EnvironmentTab
 */
public  class EnvironmentListener implements org.apache.spark.scheduler.SparkListener {
  public   EnvironmentListener () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> jvmInformation () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> sparkProperties () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> systemProperties () { throw new RuntimeException(); }
  public  scala.collection.Seq<scala.Tuple2<java.lang.String, java.lang.String>> classpathEntries () { throw new RuntimeException(); }
  public  void onEnvironmentUpdate (org.apache.spark.scheduler.SparkListenerEnvironmentUpdate environmentUpdate) { throw new RuntimeException(); }
}
