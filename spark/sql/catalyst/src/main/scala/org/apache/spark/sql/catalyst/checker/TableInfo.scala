package org.apache.spark.sql.catalyst.checker

import org.apache.spark.sql.types._
import org.apache.spark.sql.catalyst.checker.DPHelper._

//added by luochen
//for each numerical attributes, record the min/max range.
case class ColumnInfo(low: Any, up: Any, multiplicity: Option[Int], dataType: NumericType) {

  def union(c: ColumnInfo): ColumnInfo = {
    if (c == null) {
      ColumnInfo.this;
    } else {
      new ColumnInfo(min(low, c.low), max(up, c.up), combine(multiplicity, c.multiplicity), dataType);
    }
  }

  def intersect(c: ColumnInfo): ColumnInfo = {
    if (c == null) {
      ColumnInfo.this;
    } else {
      val nlow = max(low, c.low);
      val nup = min(up, c.up);
      if (lessThan(nlow, nup)) {
        new ColumnInfo(nlow, nup, combine(multiplicity, c.multiplicity), dataType); ; ; ;
      } else {
        null;
      }
    }
  }

  private def combine(m1: Option[Int], m2: Option[Int]): Option[Int] = {
    if (!m1.isDefined || !m2.isDefined) {
      None;
    }
    Some(Math.max(m1.get, m2.get));
  }

  def except(r: ColumnInfo): ColumnInfo = {
    this;
  }

}

/**
 * provides statistics information for tables
 */
trait TableInfo {
  def get(dbName: String, tableName: String, columnName: String): ColumnInfo;

  def get(tableName: String, columnName: String): ColumnInfo;
}








