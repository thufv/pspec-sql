package org.apache.spark.sql.catalyst.checker.dp

import org.apache.spark.sql.types.NumericType
import org.apache.spark.sql.catalyst.checker.util.TypeUtil._

case class ColumnInfo(low: Any, up: Any, multiplicity: Option[Int]);

/**
 * provides statistics information for tables
 */
trait TableInfo {
  def get(dbName: String, tableName: String, columnName: String): ColumnInfo;

  def getByAttribute(dbName: String, tableName: String, attrName: String): ColumnInfo = {
    val columnName = getColumnString(attrName);
    get(dbName, tableName, columnName);
  }

  def get(tableName: String, columnName: String): ColumnInfo;

}
