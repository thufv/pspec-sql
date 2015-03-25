package org.apache.spark.sql.execution;
/**
 * A logical command that is executed for its side-effects.  <code>RunnableCommand</code>s are
 * wrapped in <code>ExecutedCommand</code> during execution.
 */
public  interface RunnableCommand {
  public  scala.collection.Seq<org.apache.spark.sql.Row> run (org.apache.spark.sql.SQLContext sqlContext) ;
}
