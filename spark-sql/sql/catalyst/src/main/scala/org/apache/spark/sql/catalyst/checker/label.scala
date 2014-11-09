package org.apache.spark.sql.catalyst.checker

import edu.thu.ss.lang.pojo.DataCategory
import org.apache.spark.sql.catalyst.expressions.Attribute

sealed abstract class Label;

case class DataLabel(val data: DataCategory, val table:String, val column:String) extends Label;

case class Function(val children: Seq[Label], val udf: String) extends Label;

case class Insensitive(val ref: Attribute) extends Label;

case class Constant(val value: Any) extends Label;

case class Predicate(val children: Seq[Label], val operation: String) extends Label;