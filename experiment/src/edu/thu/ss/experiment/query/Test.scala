package edu.thu.ss.experiment.query

object Test extends App {
  val runtime = Runtime.getRuntime();
  val process = runtime.exec("java", Array("edu.thu.ss.experiment.query"));
  process.waitFor();
  println(process.exitValue());
}