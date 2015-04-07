package org.apache.spark.sql.hive

import scala.collection.mutable.HashMap
import scala.collection.mutable.Map
import org.apache.spark.sql.Row
import org.apache.spark.sql.SchemaRDD
import org.apache.spark.sql.SchemaRDD
import org.apache.spark.sql.SchemaRDD
import org.apache.spark.sql.catalyst.checker.PrivacyException
import org.apache.spark.sql.catalyst.checker.PrivacyException
import org.apache.spark.sql.catalyst.expressions.AttributeReference
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.NumericType
import edu.thu.ss.spec.global.MetaManager
import org.apache.spark.sql.catalyst.checker.dp.TableInfo
import org.apache.spark.sql.catalyst.checker.dp.AttributeInfo

class HiveTableInfo(val hive: HiveContext) extends TableInfo {
  private class Table {
    private val infos: Map[String, AttributeInfo] = new HashMap;

    def get(column: String): AttributeInfo = {
      val range = infos.get(column);
      range match {
        case Some(r) => r
        case _ => throw new PrivacyException(s"column $column not exist.");
      }
    }

    def put(column: String, range: AttributeInfo) {
      infos.put(column, range);
    }
  }

  private val tableInfos: Map[String, Map[String, Table]] = new HashMap;

  def get(dbName: String, tableName: String, columnName: String): AttributeInfo = {
    val database = tableInfos.get(dbName);
    database match {
      case Some(d) => {
        val table = d.get(tableName);
        table match {
          case Some(t) => t.get(columnName);
          case _ => throw new PrivacyException(s"table $tableName not exist.");
        }
      }
      case _ => throw new PrivacyException(s"database $columnName not exist.");
    }
  }

  def get(tableName: String, columnName: String): AttributeInfo = {
    get("default", tableName, columnName);
  }

  private def put(dbName: String, tableName: String, columnName: String, info: AttributeInfo) {
    val db = tableInfos.getOrElseUpdate(dbName, new HashMap);
    val table = db.getOrElseUpdate(tableName, new Table);
    table.put(columnName, info);
  }

  def initialize() {
    val result = hive.sql("show databases").collect;
    result.foreach(row => {
      val db = row.getString(0);
      hive.sql(s"use $db");
      queryDatabase(db);
    });

    hive.sql("use default");
  }

  //TODO luochen add support for complex types, for map type, only predefined keys are queried
  private def queryDatabase(database: String) {
    val rdd = hive.sql("show tables").collect;
    rdd.foreach(row => {
      val table = row.getString(0);
      val result = hive.catalog.lookupRelation(database :: table :: Nil);
      val relation = result match {
        case r: MetastoreRelation => r;
        //  case l: LowerCaseSchema => l.child.asInstanceOf[MetastoreRelation];
        case _ => null;
      }

      val numerics = relation.attributes.filter(_.dataType.isInstanceOf[NumericType]);
      if (numerics.size > 0) {
        val row = queryRange(table, numerics)
        var i = 0;
        while (i < numerics.length) {
          //add multiplicity information
          val column = numerics(i).name;
          var multiplicity: Option[Int] = None;
          val meta = MetaManager.get(database, table);
          if (meta != null && meta.isJoinable(database, table, column)) {
            val value = meta.getMultiplicity(database, table, column);
            if (value != null) {
              multiplicity = Some(value);
            } else {
              multiplicity = Some(queryMultiplicity(table, column));
            }
          }
          put(database, table, column, new AttributeInfo(row(2 * i), row(2 * i + 1), multiplicity, numerics(i).dataType.asInstanceOf[NumericType]));
          i += 1;
        }
      }
    });

  }

  private def queryRange(table: String, numeric: Seq[AttributeReference]): Row = {
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
    val result = hive.sql(sql.toString).collect;
    result(0);
  }

  private def queryMultiplicity(table: String, column: String): Int = {
    val sql = s"""select max(tmp)
                  from (select count($column) as tmp
                        from $table
                        where $column != 0
                        group by $column) x""";
    val result = hive.sql(sql).collect;
    result(0).getLong(0).toInt;
  }

}