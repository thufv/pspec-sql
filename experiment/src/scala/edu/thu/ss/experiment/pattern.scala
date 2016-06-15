package edu.thu.ss.experiment

import scala.annotation.elidable
import scala.annotation.elidable.ASSERTION

abstract class ConditionPattern {

  def apply(args: Seq[String]): String;

  def numArgs: Int;
}

case class XLeY() extends ConditionPattern {
  def apply(args: Seq[String]): String = {
    assert(args.length == numArgs);

    s" ${args(0)} <= ${args(1)} ";
  }

  def numArgs = 2;

}

case class XGeY() extends ConditionPattern {
  def apply(args: Seq[String]): String = {
    assert(args.length == numArgs);

    s" ${args(0)} >= ${args(1)} ";
  }

  def numArgs = 2;
}

case class XAddYLeZ() extends ConditionPattern {
  def apply(args: Seq[String]): String = {
    assert(args.length == numArgs);

    s" ${args(0)} + ${args(1)} <= ${args(2)} ";
  }

  def numArgs = 3;

}

case class XAddYGeZ() extends ConditionPattern {

  def apply(args: Seq[String]): String = {
    assert(args.length == numArgs);

    s" ${args(0)} + ${args(1)} >= ${args(2)} ";
  }

  def numArgs = 3;

}

case class XMinusYLeZ() extends ConditionPattern {

  def apply(args: Seq[String]): String = {
    assert(args.length == numArgs);

    s" ${args(0)} - ${args(1)} <= ${args(2)} ";
  }

  def numArgs = 3;

}

case class XMinusYGeZ() extends ConditionPattern {
  def apply(args: Seq[String]): String = {
    assert(args.length == numArgs);

    s" ${args(0)} + ${args(1)} >= ${args(2)} ";
  }

  def numArgs = 3;

}

case class XTimesYLeZ() extends ConditionPattern {
  def apply(args: Seq[String]): String = {
    assert(args.length == numArgs);

    s" ${args(0)} * ${args(1)} <= ${args(2)} ";
  }

  def numArgs = 3;

}

case class XTimesYGeZ() extends ConditionPattern {
  def apply(args: Seq[String]): String = {
    assert(args.length == numArgs);

    s" ${args(0)} * ${args(1)} >= ${args(2)} ";
  }

  def numArgs = 3;

}