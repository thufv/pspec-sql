package org.apache.spark.sql.catalyst.checker.dp

import org.apache.spark.sql.types.NumericType

//added by luochen
//for each numerical attributes, record the min/max range.
sealed abstract class ColumnInfo;

case class AttributeInfo(low: Any, up: Any, multiplicity: Option[Int], dataType: NumericType) extends ColumnInfo;

//TODO: luochen complete the classes
case class StructInfo extends ColumnInfo {

}

case class ArrayInfo extends ColumnInfo {

}

case class MapInfo extends ColumnInfo {

}

/**
 * provides statistics information for tables
 */
trait TableInfo {
  //TODO: adapt the api
  def get(dbName: String, tableName: String, columnName: String): AttributeInfo;

  def get(tableName: String, columnName: String): AttributeInfo;
}








