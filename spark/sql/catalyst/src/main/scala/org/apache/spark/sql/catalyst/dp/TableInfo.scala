package org.apache.spark.sql.catalyst.dp

import org.apache.spark.sql.types.NumericType

//added by luochen
//for each numerical attributes, record the min/max range.
case class ColumnInfo(low: Any, up: Any, multiplicity: Option[Int], dataType: NumericType);

/**
 * provides statistics information for tables
 */
trait TableInfo {
  def get(dbName: String, tableName: String, columnName: String): ColumnInfo;

  def get(tableName: String, columnName: String): ColumnInfo;
}








