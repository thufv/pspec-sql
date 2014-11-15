package org.apache.spark.sql.catalyst.checker

import org.apache.spark.sql.catalyst.expressions.Attribute
import edu.thu.ss.spec.lang.pojo.DataCategory

sealed abstract class Label;

case class DataLabel(val data: DataCategory, val database: String, val table: String, val column: String) extends Label;

case class Function(val children: Seq[Label], val udf: String) extends Label;

case class Insensitive(val attr: Attribute) extends Label;

case class Constant(val value: Any) extends Label;

case class Predicate(val children: Seq[Label], val operation: String) extends Label;