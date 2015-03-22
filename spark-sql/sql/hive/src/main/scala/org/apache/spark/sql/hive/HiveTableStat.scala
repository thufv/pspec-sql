package org.apache.spark.sql.hive

import scala.collection.mutable.Map
import scala.collection.mutable.HashMap
import org.apache.spark.sql.SchemaRDD
import org.apache.spark.sql.SchemaRDD
import org.apache.spark.sql.SchemaRDD
import org.apache.spark.sql.catalyst.types.NumericType
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.catalyst.plans.logical.LowerCaseSchema
import org.apache.spark.sql.catalyst.checker.DPHelper
import org.apache.spark.sql.catalyst.types.DataType
import org.apache.spark.sql.catalyst.checker.Range
import org.apache.spark.sql.catalyst.checker.TableStat

class TableRange {
  private val ranges: Map[String, Range] = new HashMap;

  def get(column: String): Range = {
    val range = ranges.get(column);
    range match {
      case Some(r) => r
      case _ => throw new RuntimeException(s"column $column not exist.");
    }
  }

  def put(column: String, range: Range) {
    ranges.put(column, range);
  }
}

class HiveTableStat extends TableStat {
  private val tableRanges: Map[String, Map[String, TableRange]] = new HashMap;

  def get(dbName: String, tableName: String, columnName: String): Range = {
    val db = tableRanges.get(dbName);
    db match {
      case Some(d) => {
        val table = d.get(tableName);
        table match {
          case Some(t) => t.get(columnName);
          case _ => throw new RuntimeException(s"table $tableName not exist.");
        }
      }
      case _ => throw new RuntimeException(s"database $columnName not exist.");
    }
  }

  def get(tableName: String, columnName: String): Range = {
    get("default", tableName, columnName);
  }

  private def put(dbName: String, tableName: String, columnName: String, range: Range) {
    val db = tableRanges.getOrElseUpdate(dbName, new HashMap);
    val table = db.getOrElseUpdate(tableName, new TableRange);
    table.put(columnName, range);
  }

  def initialize(hive: HiveContext) {
    val rdd = hive.sql("show databases").collect;
    rdd.foreach(row => {
      val db = row.getString(0);
      hive.sql(s"use $db");
      queryTables(db, hive);
    })

    hive.sql("use default");
    DPHelper.initialized = true;
  }

  private def queryTables(db: String, hive: HiveContext) {
    val rdd = hive.sql("show tables").collect;
    rdd.foreach(row => {
      val table = row.getString(0);
      val result = hive.catalog.lookupRelation(Some("default"), table);
      val relation = result match {
        case r: MetastoreRelation => r;
        case l: LowerCaseSchema => l.child.asInstanceOf[MetastoreRelation];
        case _ => null;
      }

      val numerics = relation.attributes.filter(_.dataType.isInstanceOf[NumericType]);
      if (numerics.size > 0) {
        val sql = constructQuery(table, numerics);
        val result = hive.sql(sql).collect;
        val row = result(0);
        var i = 0;
        while (i < numerics.length) {
          put(db, table, numerics(i).name, Range(row(2 * i), row(2 * i + 1), numerics(i).dataType.asInstanceOf[NumericType]));
          i += 1;
        }
      }
    });

  }

  private def constructQuery(table: String, numeric: Seq[AttributeReference]): String = {
    val sql = new StringBuilder;
    sql.append("select ");
    var i = 0;

    numeric.foreach(attr => {
      val name = attr.name;
      sql.append(s"min($name), max($name)");
      if (i != numeric.length - 1) {
        sql.append(",");
      }
      i = i + 1;
    });
    sql.append(s" from $table");
    return sql.toString;
  }

}