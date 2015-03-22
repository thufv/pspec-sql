package org.apache.spark.sql.catalyst.checker

import org.apache.spark.sql.catalyst.types.DataType
import org.apache.spark.sql.catalyst.types.NumericType
import org.apache.spark.sql.catalyst.types.NumericType
import org.apache.spark.sql.catalyst.types.DecimalType
import org.apache.spark.sql.catalyst.types.DoubleType
import org.apache.spark.sql.catalyst.types.FloatType
import org.apache.spark.sql.catalyst.types.LongType
import org.apache.spark.sql.catalyst.checker.DPHelper._

//added by luochen
//for each numerical attributes, record the min/max range.
case class Range(low: Any, up: Any, dataType: NumericType) {

  def union(r: Range): Range = {
    if (r == null) {
      this;
    } else {
      Range(min(low, r.low), max(up, r.up), dataType);
    }
  }

  def intersect(r: Range): Range = {
    if (r == null) {
      this;
    } else {
      val nlow = max(low, r.low);
      val nup = min(up, r.up);
      if (lessThan(nlow, nup)) {
        Range(nlow, nup, dataType);
      } else {
        null;
      }
    }
  }

  def except(r: Range): Range = {
    this;
  }

}

/**
 * provides statistics information for tables
 */
trait TableStat {
  def get(dbName: String, tableName: String, columnName: String): Range;

  def get(tableName: String, columnName: String): Range;
}