package org.apache.spark.sql.catalyst.checker.dp

import org.apache.spark.sql.types.NumericType
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._
//added by luochen
//for each numerical attributes, record the min/max range.
sealed abstract class ColumnInfo;

case class AttributeInfo(low: Any, up: Any, multiplicity: Option[Int]) extends ColumnInfo;

/**
 * provides statistics information for tables
 */
trait TableInfo {
  def get(dbName: String, tableName: String, columnName: String): AttributeInfo;

  def getByAttribute(dbName: String, tableName: String, attrName: String): AttributeInfo = {
    val columnName = getColumnString(attrName);
    get(dbName, tableName, columnName);
  }

  def get(tableName: String, columnName: String): AttributeInfo;

}








