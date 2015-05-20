package edu.thu.ss.experiment.query
import scala.reflect.runtime.universe._
import scala.reflect.runtime.{universe => ru}
import scala.tools.reflect.ToolBox

object Quote extends App {
	val toolBox = runtimeMirror(getClass.getClassLoader).mkToolBox()

  val a = 10;
  val b = 20;

  val add = q"$a + $b";
  println(add);
  println(toolBox.eval(add));
}