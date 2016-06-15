package edu.thu.ss.experiment

import com.microsoft.z3.Context

object Test extends App {
  val context = new Context;

  println(context.SimplifyHelp());

}